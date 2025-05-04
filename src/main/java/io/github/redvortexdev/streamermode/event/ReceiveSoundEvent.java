package io.github.redvortexdev.streamermode.event;

public final class ReceiveSoundEvent {

    private static int soundsToCancel = 0;

    private ReceiveSoundEvent() {
    }

    public static void cancelNextSound(int amount) {
        soundsToCancel += amount;
    }

    public static boolean onEvent() {
        if (soundsToCancel > 0) {
            soundsToCancel--;
            return true;
        }
        return false;
    }

}
