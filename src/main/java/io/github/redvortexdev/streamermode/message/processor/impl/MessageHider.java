package io.github.redvortexdev.streamermode.message.processor.impl;

import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.processor.MessageProcessor;

public class MessageHider extends MessageProcessor {

    private static final int DEFAULT_TIMEOUT = 1000;
    private static int pendingCancellations = 0;
    private static long timeoutTimestamp = 0;

    public static void queueCancellation(int messages) {
        if (messages <= 0) {
            return;
        }
        pendingCancellations = messages;
        timeoutTimestamp = System.currentTimeMillis() + DEFAULT_TIMEOUT;
    }

    @Override
    protected void handle(Message message) {
        if (System.currentTimeMillis() > timeoutTimestamp) {
            if (pendingCancellations <= 0) {
                return;
            }
            reset();
            return;
        }

        message.hide();
        pendingCancellations--;

        if (pendingCancellations <= 0) {
            reset();
        }
    }

    private static void reset() {
        pendingCancellations = 0;
        timeoutTimestamp = 0;
    }

}
