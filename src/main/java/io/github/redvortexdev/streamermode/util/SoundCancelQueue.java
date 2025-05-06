package io.github.redvortexdev.streamermode.util;

public final class SoundCancelQueue {

    private static int pendingCancellations = 0;

    private SoundCancelQueue() {
    }

    public static void scheduleCancellation(int amount) {
        pendingCancellations += amount;
    }

    public static boolean shouldCancelSound() {
        if (pendingCancellations > 0) {
            pendingCancellations--;
            return true;
        }
        return false;
    }

}
