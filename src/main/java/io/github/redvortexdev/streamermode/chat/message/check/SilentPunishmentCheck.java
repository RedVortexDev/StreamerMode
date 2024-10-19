package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

public class SilentPunishmentCheck extends MessageCheck {
    @Override
    public MessageType getType() {
        return MessageType.SILENT_PUNISHMENT;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return stripped.startsWith("[Silent]");
    }

    @Override
    public void onReceive(Message message) {

    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideModeration;
    }
}
