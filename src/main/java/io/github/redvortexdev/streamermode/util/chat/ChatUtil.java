package io.github.redvortexdev.streamermode.util.chat;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class ChatUtil {

    private ChatUtil() {
    }

    public static void playSound(Config.Sound sound) {
        if (StreamerMode.MC.player == null) {
            return;
        }
        if (sound.getSound() != null) {
            StreamerMode.MC.player.playSound(sound.getSound(), 1.0F, 1.0F);
        }
    }

    public static void sendMessage(String message, ChatType type) {
        sendMessage(Text.literal(message), type);
    }

    public static void sendMessage(String message) {
        sendMessage(Text.literal(message));
    }

    public static void sendMessage(Text message) {
        sendMessage(message, ChatType.INFO);
    }

    public static void sendMessage(Text message, ChatType type) {
        if (StreamerMode.MC.player == null) {
            return;
        }
        StreamerMode.MC.player.sendMessage(type.getPrefix().append(Text.empty().styled(style -> style.withBold(false).withColor(Formatting.WHITE)).append(message)));
    }

}
