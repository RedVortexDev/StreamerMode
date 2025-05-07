package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

public class ModerationCheck extends MessageCheck {

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.MODERATION;
    }

    @Override
    public boolean passesCheck(Message message) {
        return message.getStripped().startsWith("[MOD]");
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideModeration();
    }

}
