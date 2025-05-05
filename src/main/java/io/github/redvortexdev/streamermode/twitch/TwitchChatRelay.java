package io.github.redvortexdev.streamermode.twitch;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Optional;

public final class TwitchChatRelay {

    public static final int HEX_RADIX = 16;
    private static final String CHANNEL_NAME = Config.instance().twitchRelayChannel;
    private TwitchChat chatClient;
    private boolean allowedToConnect = false;
    private boolean connected = false;

    private TwitchChatRelay() {
        if (!Config.instance().twitchRelayEnabled || CHANNEL_NAME.isEmpty()) {
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
        if (!Config.instance().twitchRelayEnabled) {
            return;
        }
        Optional<String> userNameOptional = event.getUserDisplayName();
        Optional<String> messageOptional = event.getMessage();
        if (userNameOptional.isEmpty() || messageOptional.isEmpty()) {
            return;
        }
        String message = messageOptional.get();
        String userName = userNameOptional.get();

        // If the user is nightbot or the message starts with "!", ignore it (bot & command)
        if (event.getUser() != null && event.getUser().getName().equals("nightbot") || message.startsWith("!")) {
            return;
        }

        Optional<String> messageId = event.getTagValue("msg-id");
        boolean isHighlighted = messageId.isPresent() && messageId.get().equals("highlighted-message");

        String modString = event.getTagValue("mod").orElse("0");
        boolean mod = modString.equals("1");

        String chatColor = event.getUserChatColor().orElse("#FFFFFF");

        this.sendTwitchMessage(userName, message, chatColor, isHighlighted, mod);
    }

    private void sendTwitchMessage(String user, String message, String chatColor, boolean isHighlighted, boolean mod) {
        MutableText highlight = Text.empty();
        if (isHighlighted) {
            highlight = Text.empty().styled(style -> style.withInsertion("twitch_relay_highlighted"));
        }

        String prefix = "T";
        if (mod) {
            prefix = "TM";
        }

        MutableText textMessage = Text.empty()
                .append(Text.literal(prefix).styled(style -> style.withFont(StreamerMode.identifier("twitch_relay"))))
                .append(Text.literal(" "))
                .append(highlight
                        .append(Text.literal(user).styled(style -> style.withColor(Integer.parseInt(chatColor.replace("#", ""), HEX_RADIX))))
                        .append(Text.literal(": "))
                        .append(Text.literal(message))
                );


        StreamerMode.MC.inGameHud.getChatHud().addMessage(textMessage);
    }

    public static class Instance {
        private static final TwitchChatRelay INSTANCE = new TwitchChatRelay();
    }

}
