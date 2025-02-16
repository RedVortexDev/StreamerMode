package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

public class SupportQueueCheck extends MessageCheck {

    @Override
    public MessageType getType() {
        return MessageType.SUPPORT_QUEUE;
    }

    // Regex just refused to work :( I tried my best
    @Override
    public boolean check(Message message, String stripped) {
        return stripped.startsWith("[SUPPORT] ") && (stripped.contains("joined the support queue") || stripped.contains("left the support queue"));
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideSupport;
    }
}
