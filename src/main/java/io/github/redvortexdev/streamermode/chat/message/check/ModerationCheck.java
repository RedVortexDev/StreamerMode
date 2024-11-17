package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

public class ModerationCheck extends MessageCheck {
    @Override
    public MessageType getType() {
        return MessageType.MODERATION;
    }

    @Override
    public boolean check(Message message, String stripped) {
        // General moderation messages (Broadcast, AntiX, etc.)
        return stripped.startsWith("[MOD]");
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideModeration;
    }
}
