package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class SupportAnswerCheck extends MessageCheck {

    private static final Pattern SUPPORT_ANSWER_REGEX = Pattern.compile("^.*\\n» \\w+ has answered \\w+'s question:\\n\\n.+\\n.*$");

    @Override
    public MessageType getType() {
        return MessageType.SUPPORT_ANSWER;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return SUPPORT_ANSWER_REGEX.matcher(stripped).matches();
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideSupport;
    }

}
