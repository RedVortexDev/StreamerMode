package io.github.redvortexdev.streamermode.event;

public class ReceiveSoundEvent {

    private static int soundsToCancel = 0;

    public static void cancelNextSound() {
        soundsToCancel++;
    }

    public static boolean onEvent() {
        if (soundsToCancel > 0) {
            soundsToCancel--;
            return true;
        }
        return false;
    }
}
