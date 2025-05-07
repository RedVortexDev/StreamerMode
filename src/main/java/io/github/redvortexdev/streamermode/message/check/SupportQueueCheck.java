package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

public class SupportQueueCheck extends MessageCheck {

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.SUPPORT_QUEUE;
    }

    @Override
    public boolean passesCheck(Message message) {
        return message.getStripped().startsWith("[SUPPORT] ") &&
                (message.getStripped().contains("joined the support queue") ||
                        message.getStripped().contains("left the support queue"));
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideSupport();
    }

}
