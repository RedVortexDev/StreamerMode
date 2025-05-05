package io.github.redvortexdev.streamermode;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.queue.QueueEntry;
import io.github.redvortexdev.streamermode.util.Palette;
import io.github.redvortexdev.streamermode.util.WebUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
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

    public static final int NODE_BETA_PAD = 14;
    public static final int HEADER_PAD = 24;

    private static boolean allowed = false;
    private static Screen queuedScreen;

    @SuppressWarnings("unused")
    private final String WARNING = "Do not modify the mod to enable Streamer Mode, it includes banned capabilities.";
    @SuppressWarnings("unused")
    private final String WARNING_ = "Only Jeremaster and developers should be able to use this mod.";

    public static void enable() {
        allowed = true;
    }

    public static boolean isAllowed() {
        if (!StreamerMode.allowed) {
            StreamerMode.MC.getToastManager().add(new SystemToast(
                    SystemToast.Type.PERIODIC_NOTIFICATION,
                    Text.literal("Streamer Mode is disabled"),
                    Text.literal("You are not allowed to use Streamer Mode, please delete the mod.")));
        }
        return StreamerMode.allowed;
    }

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (queuedScreen != null) {
                MC.setScreen(queuedScreen);
                queuedScreen = null;
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            if (!isAllowed()) {
                return;
            }
            dispatcher.register(literal("streamermode").executes(ctx -> {
                queuedScreen = Config.HANDLER.generateGui().generateScreen(null);
                return 0;
            }));

            dispatcher.register(literal("queue").executes(ctx -> {
                try {
                    if (MC.player == null) {
                        return -1;
                    }
                    String content = WebUtil.getString("https://twitch.center/customapi/quote/list?token=18a3878c");
                    String[] splitQueue = content.replace('§', '&').split("\\n");
                    LinkedHashSet<QueueEntry> queue = new LinkedHashSet<>();

                    int i = 0;
                    for (String entry : splitQueue) {
                        if (entry.isEmpty()) {
                            continue;
                        }
                        QueueEntry queueEntry = new QueueEntry(entry, i);
                        String plotId = queueEntry.getPlotId();
                        String idString;
                        idString = Objects.requireNonNullElse(plotId, "?");

                        if (!QueueEntry.getHiddenEntries().contains(idString)) {
                            i++;
                            queue.add(new QueueEntry(entry, i));
                        }
                    }

                    MC.player.playSound(SoundEvents.UI_TOAST_IN, 2F, 1F);

                    MC.player.sendMessage(Text.literal("\n" + " ".repeat(HEADER_PAD))
                                    .append(Text.literal("⏪  ")
                                            .styled(style -> style.withColor(Palette.MINT_DARK))
                                            .append(Text.literal("Twitch Plot Queue")
                                                    .styled(style -> style.withColor(Palette.MINT))
                                                    .append(Text.literal("  ⏩")
                                                            .styled(style -> style.withColor(Palette.MINT_DARK)))))
                            , false);

                    for (QueueEntry entry : queue) {
                        String plotIdStr;
                        if (entry.getPlotId() == null) {
                            plotIdStr = "?";
                        } else {
                            plotIdStr = entry.getPlotId();
                        }

                        String strippedDesc = entry.getStrippedDescription();
                        MutableText descText;
                        if (strippedDesc.isEmpty()) {
                            descText = Text.literal("N/A").styled(style -> style.withColor(TextColor.fromFormatting(Formatting.GRAY)));
                        } else {
                            descText = Text.literal(strippedDesc).styled(style -> style.withColor(Palette.SKY_LIGHT));
                        }

                        MutableText entrymsg = Text.literal("#" + entry.getPosition())
                                .styled(style -> style.withColor(Palette.SKY_DARK)
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/queue hideandjoin " + entry.getPlotId()))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to join!")
                                                .formatted(Formatting.GRAY))))
                                .append(Text.literal(" - ").formatted(Formatting.DARK_GRAY)
                                        .append(Text.literal(plotIdStr)
                                                .styled(style -> style.withColor(Palette.SKY))
                                                .append(Text.literal(" - ").formatted(Formatting.DARK_GRAY)
                                                        .append(descText))));

                        if (entry.isBeta()) {
                            entrymsg.append(Text.literal("\n " + " ".repeat(NODE_BETA_PAD) + "↑ ")
                                    .styled(style -> style.withColor(Palette.SALMON_DARK))
                                    .append(Text.literal("This plot may be on ")
                                            .styled(style -> style.withColor(Palette.SALMON))
                                            .append(Text.literal("Node Beta")
                                                    .styled(style -> style.withColor(Palette.ORANGE))
                                                    .append(Text.literal(" ↑")
                                                            .styled(style -> style.withColor(Palette.SALMON_DARK))))));
                        }
                        MC.player.sendMessage(entrymsg, false);
                    }

                    MC.player.sendMessage(Text.empty());
                } catch (IOException e) {
                    MC.player.sendMessage(Text.literal("Error while requesting").formatted(Formatting.RED), false);
                    LOGGER.error("Error while requesting", e);
                    return 0;
                }
                return 1;
            }).then(argument("type", StringArgumentType.word()).then(argument("id", StringArgumentType.word()).executes(ctx -> {
                if (MC.player == null) {
                    return -1;
                }
                String id = ctx.getArgument("id", String.class);
                String type = ctx.getArgument("type", String.class);

                if (id.equals("null")) {
                    return 0;
                }

                switch (type) {
                    case "hideandjoin":
                        if (!QueueEntry.getHiddenEntries().contains(id)) {
                            QueueEntry.getHiddenEntries().add(id);
                        }
                        MC.player.networkHandler.sendCommand("join " + id);
                        MC.player.sendMessage(Text.literal("⏩ ")
                                .styled(style -> style.withColor(Palette.MINT_DARK)
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/queue show " + id))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to unhide!")
                                                .formatted(Formatting.GRAY))))
                                .append(Text.literal("Plot " + id + " hidden from queue. Click here to unhide!")
                                        .styled(style -> style.withColor(Palette.MINT_LIGHT))
                                        .append(Text.literal(" ⏪").styled(style -> style.withColor(Palette.MINT_DARK)))), false);
                        break;
                    case "show":
                        QueueEntry.getHiddenEntries().removeIf(id::equals);
                        MC.player.sendMessage(Text.literal("⏩ ")
                                .styled(style -> style.withColor(Palette.MINT_DARK))
                                .append(Text.literal("Plot " + id + " will now be shown in queue.")
                                        .styled(style -> style.withColor(Palette.MINT_LIGHT))
                                        .append(Text.literal(" ⏪").styled(style -> style.withColor(Palette.MINT_DARK)))), false);
                        break;
                    default:
                        MC.player.sendMessage(Text.literal("⏩ ")
                                .styled(style -> style.withColor(Palette.SALMON_DARK))
                                .append(Text.literal("Invalid plot ID!")
                                        .styled(style -> style.withColor(Palette.SALMON))
                                        .append(Text.literal(" ⏪")
                                                .styled(style -> style.withColor(Palette.SALMON_DARK)))), false);
                        break;
                }
                return 0;
            }))));
        });
    }

    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }

}
