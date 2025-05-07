package io.github.redvortexdev.streamermode.queue;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.util.Palette;
import io.github.redvortexdev.streamermode.util.WebUtility;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.LinkedHashSet;

/**
 * The /queue command responsible for displaying the twitch plot queue,
 * and allowing them to be joined by clicking on the queue entry.
 */
public final class QueueCommand {

    private static final int NODE_BETA_PAD = 14;

    private QueueCommand() {
    }

    public static void registerQueueCommand(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("streamermode").executes(ctx -> {
            StreamerMode.setQueuedScreen(Config.getInstance().generateConfig().generateScreen(null));
            return 0;
        }));

        if (!StreamerMode.isStreamingAllowed()) {
            return;
        }
        dispatcher.register(ClientCommandManager.literal("queue")
                .executes(ctx -> {
                    try {
                        if (StreamerMode.MC.player == null) {
                            return -1;
                        }
                        WebUtility.getString("https://twitch.center/customapi/quote/list?token=18a3878c").thenAccept(content -> {
                            String[] splitQueue = content.replace('§', '&').split("\\n");
                            LinkedHashSet<QueueEntry> queue = new LinkedHashSet<>();

                            int i = 0;
                            for (String entry : splitQueue) {
                                if (entry.isEmpty()) {
                                    continue;
                                }
                                QueueEntry queueEntry = QueueEntry.of(entry, i);
                                if (queueEntry == null) {
                                    continue;
                                }
                                String plotId = queueEntry.getPlotId();

                                if (!QueueEntry.getHiddenEntries().contains(plotId)) {
                                    i++;
                                    queue.add(queueEntry);
                                }
                            }

                            StreamerMode.MC.player.playSound(SoundEvents.UI_TOAST_IN, 2F, 1F);

                            StreamerMode.MC.player.sendMessage(Component.text("\n" + " ".repeat(StreamerMode.HEADER_PAD))
                                    .append(Component.text("⏪  ", Palette.MINT_DARK)
                                            .append(Component.text("Twitch Plot Queue", Palette.MINT))
                                            .append(Component.text("  ⏩", Palette.MINT_DARK))
                                    )
                            );

                            for (QueueEntry entry : queue) {
                                String plotId = "?";
                                if (entry.getPlotId() != null) {
                                    plotId = entry.getPlotId();
                                }

                                Component description = Component.text("N/A", Palette.GRAY_LIGHT);

                                if (!entry.getDescription().isEmpty()) {
                                    description = Component.text(entry.getDescription(), Palette.SKY_LIGHT);
                                }

                                Component entryMessage = getQueueEntryComponent(entry, plotId, description);

                                StreamerMode.MC.player.sendMessage(entryMessage);
                            }

                            StreamerMode.MC.player.sendMessage(Text.empty());
                        });
                    } catch (IOException e) {
                        StreamerMode.MC.player.sendMessage(Component.text("Error while requesting", Palette.RED));
                        StreamerMode.LOGGER.error("Error while requesting", e);
                        return 0;
                    }
                    return 1;
                })
                .then(ClientCommandManager.argument("type", StringArgumentType.word())
                        .then(ClientCommandManager.argument("id", StringArgumentType.word())
                                .executes(ctx -> {
                                    if (StreamerMode.MC.player == null) {
                                        return -1;
                                    }
                                    String id = ctx.getArgument("id", String.class);
                                    String type = ctx.getArgument("type", String.class);

                                    if (id.equals("null")) {
                                        return 0;
                                    }

                                    switch (type) {
                                        case "join":
                                            if (!QueueEntry.getHiddenEntries().contains(id)) {
                                                QueueEntry.getHiddenEntries().add(id);
                                            }
                                            StreamerMode.MC.player.networkHandler.sendCommand("join " + id);
                                            StreamerMode.MC.player.sendMessage(Component.text("⏩ ", Palette.MINT_DARK)
                                                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/queue show " + id))
                                                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Click to unhide!", Palette.GRAY_LIGHT)))
                                                    .append(Component.text("Plot " + id + " hidden from queue. Click here to unhide!", Palette.MINT_LIGHT))
                                                    .append(Component.text(" ⏪", Palette.MINT_DARK))
                                            );
                                            break;
                                        case "show":
                                            QueueEntry.getHiddenEntries().removeIf(id::equals);
                                            StreamerMode.MC.player.sendMessage(Component.text("⏩ ", Palette.MINT_DARK)
                                                    .append(Component.text("Plot " + id + " will now be shown in queue.", Palette.MINT_LIGHT))
                                                    .append(Component.text(" ⏪", Palette.MINT_DARK))
                                            );
                                            break;
                                        default:
                                            StreamerMode.MC.player.sendMessage(Component.text("⏩ ", Palette.RED)
                                                    .append(Component.text("Invalid plot ID!", Palette.RED_LIGHT))
                                                    .append(Component.text(" ⏪", Palette.RED))
                                            );
                                            break;
                                    }
                                    return 0;
                                })
                        )
                )
        );
    }

    private static @NotNull Component getQueueEntryComponent(QueueEntry entry, String plotId, Component description) {
        Component entryMessage = Component.text("#" + entry.getPosition(), Palette.SKY)
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/queue join " + entry.getPlotId()))
                .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Click to join!", Palette.GRAY_LIGHT)))
                .append(Component.text(" - ", Palette.GRAY)
                        .append(Component.text(plotId, Palette.SKY)
                                .append(Component.text(" - ", Palette.GRAY)
                                        .append(description))));

        if (entry.isBeta()) {
            entryMessage = entryMessage.append(Component.text("\n " + " ".repeat(NODE_BETA_PAD) + "↑ ", Palette.RED)
                    .append(Component.text("This plot may be on ", Palette.RED_LIGHT))
                    .append(Component.text("Node Beta", Palette.RED_LIGHT_2))
                    .append(Component.text(" ↑", Palette.RED)));
        }

        return entryMessage;
    }

}
