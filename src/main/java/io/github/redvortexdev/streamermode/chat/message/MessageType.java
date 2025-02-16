package io.github.redvortexdev.streamermode.chat.message;

public enum MessageType {
    OTHER,

    // General
    DIRECT_MESSAGE(true),
    PLOT_AD(true),
    PLOT_BOOST(true),

    // Support
    SUPPORT,
    SUPPORT_QUEUE(1, 2),
    SUPPORT_QUESTION(true),
    SUPPORT_ANSWER(true),

    // Moderation
    MODERATION,
    INCOMING_REPORT(true),
    SILENT_PUNISHMENT,
    SCANNING(2),
    TELEPORTING,
    JOIN_FAIL,

    // Admin
    SPIES,
    ADMIN,
    PLUGIN_UPDATE,

    // Custom regex
    STREAMER_MODE_REGEX;

    private final int messageAmount;
    private final int soundCount;

    MessageType() {
        this(1);
    }

    MessageType(int messageAmount) {
        this(messageAmount, 0);
    }

    MessageType(boolean hasSound) {
        this(1, hasSound ? 1 : 0);
    }

    MessageType(int messageAmount, int soundCount) {
        this.messageAmount = messageAmount;
        this.soundCount = soundCount;
    }

    public int getMessageAmount() {
        return messageAmount;
    }

    public int getSoundCount() {
        return soundCount;
    }
}
