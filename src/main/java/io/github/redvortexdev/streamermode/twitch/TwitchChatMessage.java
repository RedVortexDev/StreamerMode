package io.github.redvortexdev.streamermode.twitch;

import net.kyori.adventure.text.format.TextColor;

public record TwitchChatMessage(String user, String message, TextColor userColor, boolean isHighlighted, boolean isMod,
                                boolean isSubscriber, int bitCount) {
}
