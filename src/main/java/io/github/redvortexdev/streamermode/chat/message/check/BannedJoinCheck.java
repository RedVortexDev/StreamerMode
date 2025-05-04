package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

public class BannedJoinCheck extends MessageCheck {

    private static final String BANNED_JOIN_REGEX = "^([^ ]{3,}) tried to join, but is banned \\(.*\\)!$";

    @Override
    public MessageType getType() {
        return MessageType.JOIN_FAIL;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return stripped.matches(BANNED_JOIN_REGEX);
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideModeration;
    }

}
