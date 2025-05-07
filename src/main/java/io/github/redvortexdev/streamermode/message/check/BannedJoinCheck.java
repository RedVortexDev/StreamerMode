package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

public class BannedJoinCheck extends MessageCheck {

    private static final String BANNED_JOIN_REGEX = "^([^ ]{3,}) tried to join, but is banned \\(.*\\)!$";

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.JOIN_FAIL;
    }

    @Override
    public boolean passesCheck(Message message) {
        return message.getStripped().matches(BANNED_JOIN_REGEX);
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideModeration();
    }

}
