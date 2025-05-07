package io.github.redvortexdev.streamermode.message;

public enum MessageCheckType {

    NONE,

    // General
    DIRECT_MESSAGE(1, 1),
    PLOT_AD(1, 1),
    PLOT_BOOST(1, 1),

    // Support
    SUPPORT,
    SUPPORT_QUEUE(1, 2),
    SUPPORT_QUESTION(1, 1),
    SUPPORT_ANSWER(1, 1),

    // Moderation
    MODERATION,
    INCOMING_REPORT(1, 1),
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

    MessageCheckType() {
        this(0);
    }

    MessageCheckType(int messageAmount) {
        this(messageAmount, 0);
    }

    MessageCheckType(int messageAmount, int soundCount) {
        this.messageAmount = messageAmount;
        this.soundCount = soundCount;
    }

    public int getMessageAmount() {
        return this.messageAmount;
    }

    public int getSoundCount() {
        return this.soundCount;
    }

}

