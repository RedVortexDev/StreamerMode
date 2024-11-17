package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.util.chat.ChatType;
import io.github.redvortexdev.streamermode.util.chat.ChatUtil;

import java.util.regex.PatternSyntaxException;

public class CustomRegexCheck extends MessageCheck {
    @Override
    public MessageType getType() {
        return MessageType.STREAMER_MODE_REGEX;
    }

    @Override
    public boolean check(Message message, String stripped) {
        try {
            return stripped.matches(Config.instance().customRegex);
        } catch (PatternSyntaxException e) {
            StreamerMode.LOGGER.error("Invalid custom regex: {}", Config.instance().customRegex, e);
            ChatUtil.sendMessage("Invalid custom regex, check console for more info.", ChatType.FAIL);
        }
        return false;
    }

    @Override
    public void onReceive(Message message) {

    }

    @Override
    public boolean streamerHideEnabled() {
        return !Config.instance().customRegex.isEmpty();
    }
}
