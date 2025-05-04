package io.github.redvortexdev.streamermode.chat.message;

import io.github.redvortexdev.streamermode.chat.message.check.AdminCheck;
import io.github.redvortexdev.streamermode.chat.message.check.AltCheck;
import io.github.redvortexdev.streamermode.chat.message.check.BannedJoinCheck;
import io.github.redvortexdev.streamermode.chat.message.check.CustomRegexCheck;
import io.github.redvortexdev.streamermode.chat.message.check.DirectMessageCheck;
import io.github.redvortexdev.streamermode.chat.message.check.ModerationCheck;
import io.github.redvortexdev.streamermode.chat.message.check.PlotAdCheck;
import io.github.redvortexdev.streamermode.chat.message.check.PlotBoostCheck;
import io.github.redvortexdev.streamermode.chat.message.check.PluginUpdateCheck;
import io.github.redvortexdev.streamermode.chat.message.check.ReportCheck;
import io.github.redvortexdev.streamermode.chat.message.check.SilentPunishmentCheck;
import io.github.redvortexdev.streamermode.chat.message.check.SpyCheck;
import io.github.redvortexdev.streamermode.chat.message.check.SupportAnswerCheck;
import io.github.redvortexdev.streamermode.chat.message.check.SupportCheck;
import io.github.redvortexdev.streamermode.chat.message.check.SupportQuestionCheck;
import io.github.redvortexdev.streamermode.chat.message.check.SupportQueueCheck;
import io.github.redvortexdev.streamermode.chat.message.check.TeleportCheck;

public final class MessageChecker {

    private static final MessageCheck[] checks = new MessageCheck[]{
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
