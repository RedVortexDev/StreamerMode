package io.github.redvortexdev.streamermode;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.twitch.TwitchChatRelay;
import io.github.redvortexdev.streamermode.util.QueueCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class StreamerMode implements ClientModInitializer {

    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final String MOD_ID = "streamermode";
    public static final String MOD_NAME = "Streamer Mode";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final List<UUID> STREAMER_MODE_ALLOWED_PLAYERS = List.of(
            new UUID(0x6c66947530264603L, 0xb3e752c97681ad3aL), // Jeremaster
            new UUID(0x4a60515152604ea9L, 0x92244be2d600dddfL), // Maximization
            new UUID(0xfc513f92b66246e2L, 0xada30a3eb73808a3L), // RedVortx
            new UUID(0x901c4cd098b94d3dL, 0xa8e908d15c6a4472L)  // GeorgeRNG
    );
    public static final int HEADER_PAD = 24;

    private static boolean onDiamondFire = false;
    private static boolean allowed = false;
    private static Screen queuedScreen;

    @SuppressWarnings("unused")
    private final String WARNING = "Do not modify the mod to enable Streamer Mode, it includes banned capabilities.";
    @SuppressWarnings("unused")
    private final String WARNING_ = "Only Jeremaster and developers should be able to use this mod.";

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();

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

    public static void enable() {
        allowed = true;
    }

    public static boolean isAllowed() {
        if (!StreamerMode.allowed) {
            StreamerMode.MC.getToastManager().add(new SystemToast(
                    SystemToast.Type.PERIODIC_NOTIFICATION,
                    Text.literal("Streamer Mode is disabled"),
                    Text.literal("You are not allowed to use Streamer Mode, please delete the mod."))
            );
        }
        return StreamerMode.allowed;
    }

    public static void setQueuedScreen(Screen screen) {
        queuedScreen = screen;
    }

    public static void setOnDiamondFire(boolean onDiamondFire) {
        StreamerMode.onDiamondFire = onDiamondFire;
    }

    public static boolean isOnDiamondFire() {
        return StreamerMode.onDiamondFire;
    }

    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }

}
