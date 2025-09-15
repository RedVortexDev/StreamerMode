package io.github.redvortexdev.streamermode.message;

import io.github.redvortexdev.streamermode.message.check.*;

public final class MessageChecker {

    private static final MessageCheck[] MESSAGE_CHECKS = new MessageCheck[]{
            // General
            new DirectMessageCheck(),
            new PlotAdCheck(),
            new PlotBoostCheck(),

            // Support
            new SupportQueueCheck(),
            new SupportCheck(),
            new SupportQuestionCheck(),
            new SupportAnswerCheck(),

            // Moderation
            new ModerationCheck(),
            new ReportCheck(),
            new SilentPunishmentCheck(),
            new AltCheck(),
            new TeleportCheck(),
            new BannedJoinCheck(),

            // Admin
            new SpyCheck(),
            new PluginUpdateCheck(),
            new AdminCheck(),

            // Custom regex
            new CustomRegexCheck()
    };

    private MessageChecker() {
    }

    /**
     * Checks for the first passing message check and returns its type.
     *
     * @param message The message to check.
     * @return The type of the check it passed.
     */
    public static MessageCheckType getPassedMessageTypeCheck(Message message) {
        for (MessageCheck check : MESSAGE_CHECKS) {
            if (check.isCheckEnabled() && check.passesCheck(message)) {
                return check.getMessageType();
            }
        }
        return MessageCheckType.NONE;
    }

}
