package io.github.redvortexdev.streamermode.twitch;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.util.Palette;
import net.minecraft.text.Text;

import java.util.Optional;

public final class TwitchChatRelay {

    private static final String CHANNEL_NAME = Config.instance().twitchRelayChannel;
    private static final String OAUTH_TOKEN = "oauth:" + Config.instance().twitchOAuthToken;

    private TwitchChat chatClient;
    private boolean allowedToConnect = false;
    private boolean connected = false;

    private TwitchChatRelay() {
        if (!Config.instance().twitchRelayEnabled || CHANNEL_NAME.isEmpty() || OAUTH_TOKEN.isEmpty()) {
            return;
        }
        this.allowedToConnect = true;
        this.chatClient = TwitchChatBuilder.builder().build();
    }

    public static TwitchChatRelay getInstance() {
        return Instance.INSTANCE;
    }

    public void connectToTwitchIRC() {
        if (!this.allowedToConnect || this.connected) {
            return;
        }
        this.connected = true;

        this.chatClient.getEventManager().onEvent(IRCMessageEvent.class, this::onChatMessage);

        this.chatClient.connect();
        this.chatClient.joinChannel(CHANNEL_NAME);
    }

    private void onChatMessage(IRCMessageEvent event) {
        Optional<String> messageOptional = event.getMessage();
        if (messageOptional.isEmpty()) {
            return;
        }
        String message = messageOptional.get();
        String user = event.getUser().getName();

        boolean isHighlighted = event.getTags().containsKey("msg-id") &&
                event.getTags().get("msg-id").equals("highlighted-message");

        message = this.convertEmojisToTwitchNames(message);

        this.sendToMinecraftChat(user, message, isHighlighted);
    }

    private void sendToMinecraftChat(String user, String message, boolean isHighlighted) {
        // Build the Minecraft chat message
        String formattedMessage = user + ": " + message;
        Text textMessage = Text.literal(formattedMessage);

        // If the message is highlighted, make it purple
        if (isHighlighted) {
            textMessage = Text.literal(formattedMessage).styled(style -> style.withColor(Palette.PURPLE));
        }

        // Send the message to Minecraft chat
        StreamerMode.MC.inGameHud.getChatHud().addMessage(textMessage);
    }

    private String convertEmojisToTwitchNames(String message) {
        return message; // TODO
    }

    public static class Instance {
        private static final TwitchChatRelay INSTANCE = new TwitchChatRelay();
    }

}
