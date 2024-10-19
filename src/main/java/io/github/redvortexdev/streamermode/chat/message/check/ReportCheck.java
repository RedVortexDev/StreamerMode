package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.util.chat.ChatUtil;

public class ReportCheck extends MessageCheck {

    @Override
    public MessageType getType() {
        return MessageType.INCOMING_REPORT;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return stripped.startsWith("! Incoming Report ");
    }

    @Override
    public void onReceive(Message message) {
        ChatUtil.playSound(Config.instance().reportSound);
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideModeration;
    }
}