package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

public class AdminCheck extends MessageCheck {

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.ADMIN;
    }

    @Override
    public boolean passesCheck(Message message) {
        return message.getStripped().startsWith("[ADMIN]");
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideAdmin();
    }

}
