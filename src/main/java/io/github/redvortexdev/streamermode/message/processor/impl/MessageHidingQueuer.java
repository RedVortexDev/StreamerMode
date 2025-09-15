package io.github.redvortexdev.streamermode.message.processor.impl;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheckType;
import io.github.redvortexdev.streamermode.message.check.DirectMessageCheck;
import io.github.redvortexdev.streamermode.message.processor.MessageProcessor;
import io.github.redvortexdev.streamermode.util.MessageCancelQueue;
import io.github.redvortexdev.streamermode.util.SoundCancelQueue;
import io.github.redvortexdev.streamermode.util.StreamerAllowlist;

public class MessageHidingQueuer extends MessageProcessor {

    @Override
    protected void handle(Message message) {
        MessageCheckType type = message.getPassedCheckType();

        int amount;
        if (type == MessageCheckType.DIRECT_MESSAGE) {
            if (Config.HANDLER.instance().hideDMs) {
                String sender = DirectMessageCheck.getSendingPlayer(message);
                boolean allowed = StreamerAllowlist.isDmAllowed(sender);
                amount = allowed ? 0 : type.getMessageAmount();
            } else {
                amount = 0;
            }
        } else {
            amount = type.getMessageAmount();
        }

        if (Config.HANDLER.instance().debugging) {
            StreamerMode.LOGGER.info("[QUEUE] [{}] {} | Amount: {} | Pending(before): {}", type.name(), message.getStripped(), amount, MessageCancelQueue.getPendingCancellations());
        }

        if (amount > 0) {
            if (type.getSoundCount() > 0) {
                SoundCancelQueue.queueCancellation(type.getSoundCount());
            }
            MessageCancelQueue.queueCancellation(amount);
        }
    }

}
