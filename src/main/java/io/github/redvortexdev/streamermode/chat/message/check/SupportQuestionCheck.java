package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class SupportQuestionCheck extends MessageCheck {
    private static final Pattern SUPPORT_QUESTION_REGEX = Pattern.compile("^.*?» Support Question: \\(Click to answer\\)\\nAsked by \\w+ \\[[a-zA-Z]+]\\n.+$");

    @Override
    public MessageType getType() {
        return MessageType.SUPPORT_QUESTION;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return SUPPORT_QUESTION_REGEX.matcher(stripped).matches();
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideSupport;
    }
}
