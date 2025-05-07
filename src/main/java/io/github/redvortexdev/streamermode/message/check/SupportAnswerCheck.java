package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class SupportAnswerCheck extends MessageCheck {

    private static final Pattern SUPPORT_ANSWER_REGEX = Pattern.compile("^.*\\n» \\w+ has answered \\w+'s question:\\n\\n.+\\n.*$");

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.SUPPORT_ANSWER;
    }

    @Override
    public boolean passesCheck(Message message) {
        return SUPPORT_ANSWER_REGEX.matcher(message.getStripped()).matches();
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideSupport();
    }

}
