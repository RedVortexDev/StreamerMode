package io.github.redvortexdev.streamermode;

import io.github.redvortexdev.streamermode.queue.QueueCommand;
import io.github.redvortexdev.streamermode.twitch.TwitchChatRelay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamerMode implements ClientModInitializer {

    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final String MOD_ID = "streamermode";
    public static final String MOD_NAME = "Streamer Mode";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final int HEADER_PAD = 24;

    private static boolean onDiamondFire = false;
    private static boolean streamingAllowed = false;
    private static Screen queuedScreen;

    /**
     * Do not modify the mod to enable Streamer Mode, it includes banned capabilities.
     * Only Jeremaster and developers should be able to use this mod.
     */
    public static void enableStreaming() {
        streamingAllowed = true;
    }

    /**
     * Checks if the player is allowed to use Streamer Mode.
     *
     * @return true if the player is allowed to use Streamer Mode, false otherwise.
     */
    public static boolean isStreamingAllowed() {
        return StreamerMode.streamingAllowed;
    }

    public static void setQueuedScreen(Screen screen) {
        queuedScreen = screen;
    }

    public static boolean isOnDiamondFire() {
        return StreamerMode.onDiamondFire;
    }

    public static void setOnDiamondFire(boolean onDiamondFire) {
        StreamerMode.onDiamondFire = onDiamondFire;
    }

    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (queuedScreen != null) {
                MC.setScreen(queuedScreen);
                queuedScreen = null;
            }
            if (MC.player != null) {
                TwitchChatRelay.getInstance().tick();
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            QueueCommand.registerQueueCommand(dispatcher);
        });
    }

}
