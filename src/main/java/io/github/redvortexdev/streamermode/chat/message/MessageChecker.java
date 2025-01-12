package io.github.redvortexdev.streamermode.chat.message;

import io.github.redvortexdev.streamermode.chat.message.check.*;

public class MessageChecker {
    private static final MessageCheck[] checks = new MessageCheck[]{
            // General
            new DirectMessageCheck(),
            new PlotAdCheck(),
            new PlotBoostCheck(),

            // Support
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

    public static MessageType run(Message message) {
        for (MessageCheck check : checks) {
            if (check.check(message, message.getStripped())) {
                check.onReceive(message);
                message.setCheck(check);
                return check.getType();
            }
        }
        return MessageType.OTHER;
    }
}
