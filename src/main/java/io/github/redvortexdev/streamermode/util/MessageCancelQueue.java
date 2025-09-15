package io.github.redvortexdev.streamermode.util;

public final class MessageCancelQueue {

    private static int pendingCancellations = 0;

    private MessageCancelQueue() {
    }

    public static void queueCancellation(int amount) {
        if (amount <= 0) return;
        pendingCancellations = Math.max(pendingCancellations, amount);
    }

    public static boolean shouldCancelMessage() {
        if (pendingCancellations > 0) {
            pendingCancellations--;
            return true;
        }
        return false;
    }

    public static int getPendingCancellations() {
        return pendingCancellations;
    }

}
