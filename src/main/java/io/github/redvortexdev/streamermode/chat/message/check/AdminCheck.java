package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

public class AdminCheck extends MessageCheck {

    @Override
    public MessageType getType() {
        return MessageType.ADMIN;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return stripped.startsWith("[ADMIN]");
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideAdmin;
    }

}
