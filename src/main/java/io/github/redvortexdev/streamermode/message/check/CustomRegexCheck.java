package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;
import io.github.redvortexdev.streamermode.util.chat.ChatSender;
import io.github.redvortexdev.streamermode.util.chat.ChatType;

import java.util.regex.PatternSyntaxException;

public class CustomRegexCheck extends MessageCheck {

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.STREAMER_MODE_REGEX;
    }

    @Override
    public boolean passesCheck(Message message) {
        try {
            return message.getStripped().matches(Config.HANDLER.instance().customRegex);
        } catch (PatternSyntaxException e) {
            StreamerMode.LOGGER.error("Invalid custom regex: {}", Config.HANDLER.instance().customRegex, e);
            ChatSender.sendMessage("Invalid custom regex, check console for more info.", ChatType.FAIL);
        }
        return false;
    }

    @Override
    public boolean isCheckEnabled() {
        return !Config.HANDLER.instance().customRegex.isEmpty();
    }

}
