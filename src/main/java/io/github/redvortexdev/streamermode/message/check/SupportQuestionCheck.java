package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class SupportQuestionCheck extends MessageCheck {

    private static final Pattern SUPPORT_QUESTION_REGEX = Pattern.compile("^.*?» Support Question: \\(Click to answer\\)\\nAsked by \\w+ \\[[a-zA-Z]+]\\n.+$");

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.SUPPORT_QUESTION;
    }

    @Override
    public boolean passesCheck(Message message) {
        return SUPPORT_QUESTION_REGEX.matcher(message.getStripped()).matches();
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideSupport();
    }

}
