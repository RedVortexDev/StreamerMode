package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

public class SpyCheck extends MessageCheck {

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.SPIES;
    }

    @Override
    public boolean passesCheck(Message message) {
        // Hide spies (Session spy, Muted spy, DM spy)
        return message.getStripped().startsWith("*");
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideSpy();
    }

}
