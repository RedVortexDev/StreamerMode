package io.github.redvortexdev.streamermode.message;

import io.github.redvortexdev.streamermode.message.check.AdminCheck;
import io.github.redvortexdev.streamermode.message.check.AltCheck;
import io.github.redvortexdev.streamermode.message.check.BannedJoinCheck;
import io.github.redvortexdev.streamermode.message.check.CustomRegexCheck;
import io.github.redvortexdev.streamermode.message.check.DirectMessageCheck;
import io.github.redvortexdev.streamermode.message.check.ModerationCheck;
import io.github.redvortexdev.streamermode.message.check.PlotAdCheck;
import io.github.redvortexdev.streamermode.message.check.PlotBoostCheck;
import io.github.redvortexdev.streamermode.message.check.PluginUpdateCheck;
import io.github.redvortexdev.streamermode.message.check.ReportCheck;
import io.github.redvortexdev.streamermode.message.check.SilentPunishmentCheck;
import io.github.redvortexdev.streamermode.message.check.SpyCheck;
import io.github.redvortexdev.streamermode.message.check.SupportAnswerCheck;
import io.github.redvortexdev.streamermode.message.check.SupportCheck;
import io.github.redvortexdev.streamermode.message.check.SupportQuestionCheck;
import io.github.redvortexdev.streamermode.message.check.SupportQueueCheck;
import io.github.redvortexdev.streamermode.message.check.TeleportCheck;

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
            if (check.passesCheck(message)) {
                return check.getMessageType();
            }
        }
        return MessageCheckType.NONE;
    }

}
