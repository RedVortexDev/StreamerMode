package io.github.redvortexdev.streamermode.message.processor.impl;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.processor.MessageProcessor;
import io.github.redvortexdev.streamermode.util.MessageCancelQueue;

public class MessageHider extends MessageProcessor {

    @Override
    protected void handle(Message message) {
        if (MessageCancelQueue.shouldCancelMessage()) {
            if (Config.HANDLER.instance().debugging) {
                StreamerMode.LOGGER.info("[CANCELLING] [{}] {} | Pending(after): {}", message.getPassedCheckType().name(), message.getStripped(), MessageCancelQueue.getPendingCancellations());
            }
            message.cancel();
        }
    }

}
