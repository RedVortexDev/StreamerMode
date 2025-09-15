package io.github.redvortexdev.streamermode.message.processor.impl;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.processor.MessageProcessor;
import io.github.redvortexdev.streamermode.util.SoundCancelQueue;

public class MessageHider extends MessageProcessor {

    private static int pendingCancellations = 0;

    @Override
    protected void handle(Message message) {
        int amount = message.getPassedCheckType().getMessageAmount();
        if (Config.HANDLER.instance().debugging) {
            StreamerMode.LOGGER.info("[CHECK] [{}] {} | Amount: {} | Pending: {}", message.getPassedCheckType().name(), message.getStripped(), amount, pendingCancellations);
        }

        if (amount > 0) {
            if (message.getPassedCheckType().getSoundCount() > 0) {
                SoundCancelQueue.queueCancellation(message.getPassedCheckType().getSoundCount());
            }
            pendingCancellations = Math.max(pendingCancellations, amount);
        }

        if (pendingCancellations > 0) {
            if (Config.HANDLER.instance().debugging) {
                StreamerMode.LOGGER.info("[CANCELLING] [{}] {} | Pending: {}", message.getPassedCheckType().name(), message.getStripped(), pendingCancellations);
            }
            message.cancel();
            pendingCancellations--;
        }
    }

}
