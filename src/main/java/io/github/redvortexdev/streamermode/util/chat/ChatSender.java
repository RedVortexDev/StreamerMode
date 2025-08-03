package io.github.redvortexdev.streamermode.util.chat;

import io.github.redvortexdev.streamermode.StreamerMode;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.Component;

public final class ChatSender {

    private ChatSender() {
    }

    public static void sendMessage(String message, ChatType type) {
        sendMessage(Component.text(message), type);
    }

    public static void sendMessage(Component message, ChatType type) {
        if (StreamerMode.MC.player == null) {
            StreamerMode.LOGGER.info(MinecraftClientAudiences.of().asNative(message).getString());
            return;
        }
        StreamerMode.MC.player.sendMessage(Component.empty()
                .append(type.getPrefix())
                .append(message)
        );
    }

}
