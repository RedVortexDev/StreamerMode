package io.github.redvortexdev.streamermode.twitch;

public record TwitchChatMessage(String user, String message, int userColor, boolean isHighlighted, boolean isMod,
                                boolean isSubscriber, int bitCount) {
}
