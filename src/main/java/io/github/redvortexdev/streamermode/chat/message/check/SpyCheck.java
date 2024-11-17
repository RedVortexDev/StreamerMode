package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

public class SpyCheck extends MessageCheck {
    @Override
    public MessageType getType() {
        return MessageType.SPIES;
    }

    @Override
    public boolean check(Message message, String stripped) {
        // Hide spies (Session spy, Muted spy, DM spy)
        return stripped.startsWith("*");
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideSpy;
    }
}
