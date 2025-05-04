package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

public class SupportCheck extends MessageCheck {

    @Override
    public MessageType getType() {
        return MessageType.SUPPORT;
    }

    @Override
    public boolean check(Message message, String stripped) {
        // General support messages (Broadcast, session requests and completion, etc.)
        return stripped.startsWith("[SUPPORT]");
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideSupport;
    }

}
