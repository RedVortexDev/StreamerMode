package io.github.redvortexdev.streamermode;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.queue.QueueEntry;
import io.github.redvortexdev.streamermode.util.WebUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class StreamerMode implements ClientModInitializer {

    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final String MOD_ID = "streamermode";
    public static final String MOD_NAME = "Streamer Mode";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final List<UUID> STREAMER_MODE_ALLOWED_PLAYERS = List.of(
            new UUID(0x6c66947530264603L, 0xb3e752c97681ad3aL), // Jeremaster
            new UUID(0x4a60515152604ea9L, 0x92244be2d600dddfL), // Maximization
            new UUID(0xfc513f92b66246e2L, 0xada30a3eb73808a3L), // RedVortx
            new UUID(0x901c4cd098b94d3dL, 0xa8e908d15c6a4472L)  // GeorgeRNG
    );

    private static boolean allowed = false;
    public static Screen setScreen;

    @SuppressWarnings("unused")
    private final String WARNING = "Do not modify the mod to enable Streamer Mode, it includes banned capabilities.";
    @SuppressWarnings("unused")
    private final String WARNING_ = "Only Jeremaster and developers should be able to use this mod.";

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (MC.player == null) return;

            if (setScreen != null) {
                MC.setScreen(setScreen);
                setScreen = null;
            }
        });

        // TODO: Move to separate classes. I don't like this.

        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            if (!isAllowed()) return;
            dispatcher.register(literal("streamermode").executes(ctx -> {
                setScreen = Config.HANDLER.generateGui().generateScreen(null);
                return 0;
                })
            );
            dispatcher.register(literal("queue").executes(ctx -> {
                try {
                    if (MC.player == null) return -1;
                    String content = WebUtil.getString("https://twitch.center/customapi/quote/list?token=18a3878c");

                    String[] splitQueue = content.replace('§', '&').split("\\n");
                    LinkedHashSet<QueueEntry> queue = new LinkedHashSet<>();

                    int i = 0;
                    for (String entry : splitQueue) {
                        if (entry.isEmpty()) continue;
                        QueueEntry queueEntry = new QueueEntry(entry, i);
                        String plotId = queueEntry.getPlotId();
                        if (!QueueEntry.HIDDEN_ENTRIES.contains(plotId == null ? "?" : plotId.toString())) {
                            i++;
                            queue.add(new QueueEntry(entry, i));
                        }
                    }

                    MC.player.playSound(SoundEvents.UI_TOAST_IN, 2F, 1F);

                    MC.player.sendMessage(Text.literal("\n" + " ".repeat(24))
                                    .append(Text.literal("⏪  ")
                                            .styled(style -> style.withColor(TextColor.fromRgb(0x1f9947)))
                                            .append(Text.literal("Twitch Plot Queue")
                                                    .styled(style -> style.withColor(TextColor.fromRgb(0x33ffa7)))
                                                    .append(Text.literal("  ⏩")
                                                            .styled(style -> style.withColor(TextColor.fromRgb(0x1f9947))))

                                            )
                                    )
                            , false);

                    for (QueueEntry entry : queue) {
                        MutableText entrymsg = Text.literal("#" + entry.getPosition())
                                .styled(style -> style.withColor(TextColor.fromRgb(0x00bbff))
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/queue hideandjoin " + entry.getPlotId()))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to join!")
                                                .formatted(Formatting.GRAY)))).append(Text.literal(" - ").formatted(Formatting.DARK_GRAY)
                                        .append(Text.literal(entry.getPlotId() == null ? "?" : entry.getPlotId().toString())
                                                .styled(style -> style.withColor(TextColor.fromRgb(0x66e6ff)))
                                                .append(Text.literal(" - ").formatted(Formatting.DARK_GRAY)
                                                        .append(Text.literal(entry.getStrippedDescription().isEmpty() ? "N/A" : entry.getStrippedDescription())
                                                                .styled(style -> style.withColor(entry.getStrippedDescription().isEmpty() ? TextColor.fromFormatting(Formatting.GRAY) : TextColor.fromRgb(0xbff9ff))))
                                                )
                                        )

                                );
                        if (entry.isBeta()) {
                            entrymsg.append(Text.literal("\n " + " ".repeat(14) + "↑ ")
                                    .styled(style -> style.withColor(TextColor.fromRgb(0x7a2626)))
                                    .append(Text.literal("This plot may be on ")
                                            .styled(style -> style.withColor(TextColor.fromRgb(0xc96363)))
                                            .append(Text.literal("Node Beta")
                                                    .styled(style -> style.withColor(TextColor.fromRgb(0xd95104)))
                                                    .append(Text.literal(" ↑")
                                                            .styled(style -> style.withColor(TextColor.fromRgb(0x7a2626)))
                                                    )
                                            )
                                    )
                            );
                        }
                        MC.player.sendMessage(entrymsg, false);
                    }

                    MC.player.sendMessage(Text.empty());
                } catch (Exception e) {
                    MC.player.sendMessage(Text.literal("Error while requesting").formatted(Formatting.RED), false);
                    LOGGER.error("Error while requesting", e);
                    return 0;
                }

                return 1;
            }).then(argument("type", StringArgumentType.word()).then(argument("id", StringArgumentType.word()).executes(ctx -> {
                if (MC.player == null) return -1;
                String id = ctx.getArgument("id", String.class);
                String type = ctx.getArgument("type", String.class);

                if (id.equals("null")) return 0;

                switch (type) {
                    case "hideandjoin":
                        if (!QueueEntry.HIDDEN_ENTRIES.contains(id)) QueueEntry.HIDDEN_ENTRIES.add(id);

                        MC.player.networkHandler.sendCommand("join " + id);

                        MC.player.sendMessage(Text.literal("⏩ ")
                                .styled(style -> style.withColor(TextColor.fromRgb(0x34961d))
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/queue show " + id))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to unhide!")
                                                .formatted(Formatting.GRAY))))
                                .append(Text.literal("Plot " + id + " hidden from queue. Click here to unhide!")
                                        .styled(style -> style.withColor(TextColor.fromRgb(0xb3ffa1)))
                                        .append(Text.literal(" ⏪").styled(style -> style.withColor(TextColor.fromRgb(0x34961d))))
                                ), false);
                        break;
                    case "show":
                        QueueEntry.HIDDEN_ENTRIES.removeIf(id::equals);

                        MC.player.sendMessage(Text.literal("⏩ ")
                                .styled(style -> style.withColor(TextColor.fromRgb(0x37a61c)))
                                .append(Text.literal("Plot " + id + " will now be shown in queue.")
                                        .styled(style -> style.withColor(TextColor.fromRgb(0xb3ffa1)))
                                        .append(Text.literal(" ⏪").styled(style -> style.withColor(TextColor.fromRgb(0x37a61c))))
                                ), false);
                        break;
                    default:
                        MC.player.sendMessage(Text.literal("⏩ ")
                                .styled(style -> style.withColor(TextColor.fromRgb(0x961d1d)))
                                .append(Text.literal("Invalid plot ID!")
                                        .styled(style -> style.withColor(TextColor.fromRgb(0xffa1a1)))
                                        .append(Text.literal(" ⏪")
                                                .styled(style -> style.withColor(TextColor.fromRgb(0x961d1d)))
                                        )
                                ), false);
                        break;
                }
                return 0;
            }))));

        }));
    }

    public static void enable() {
        allowed = true;
    }

    public static boolean isAllowed() {
        if (!StreamerMode.allowed)
            StreamerMode.MC.getToastManager().add(new SystemToast(
                    SystemToast.Type.PERIODIC_NOTIFICATION,
                    Text.literal("Streamer Mode is disabled"),
                    Text.literal("You are not allowed to use Streamer Mode, please delete the mod.")
            ));
        return StreamerMode.allowed;
    }
}
