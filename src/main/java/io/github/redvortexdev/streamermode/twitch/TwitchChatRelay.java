package io.github.redvortexdev.streamermode.twitch;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.util.Palette;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Optional;

public final class TwitchChatRelay {

    public static final int UPDATE_INTERVAL = 20;
    private static final int HEX_RADIX = 16;
    private static final TwitchChatRelay INSTANCE = new TwitchChatRelay();
    private final TwitchMessageFormatter formatter;
    private TwitchChat chatClient;
    private boolean connected;
    private String currentChannel;
    private int tickCounter = 0;

    private TwitchChatRelay() {
        this.formatter = new TwitchMessageFormatter();
        this.connected = false;
        this.currentChannel = "";

        String channel = Config.instance()
                .twitchRelayChannel
                .trim();

        if (Config.instance().twitchRelayEnabled && !channel.isEmpty()) {
            this.createClientIfNeeded();
            this.connectToChannel(channel);
            this.sendSystemMessage(
                    "Twitch relay connected to " + channel
            );
        }
    }

    public static TwitchChatRelay getInstance() {
        return INSTANCE;
    }

    public void tick() {
        this.tickCounter++;
        if (this.tickCounter < UPDATE_INTERVAL) {
            return;
        }
        this.tickCounter = 0;
        this.updateFromConfig();
    }

    public void updateFromConfig() {
        boolean enabled = Config.instance().twitchRelayEnabled;
        String channel = Config.instance()
                .twitchRelayChannel
                .trim();

        if (enabled && !channel.isEmpty()) {
            if (!this.connected) {
                this.createClientIfNeeded();
                this.connectToChannel(channel);
                this.sendSystemMessage("Twitch relay connected to " + channel);

            } else if (!channel.equals(this.currentChannel)) {
                this.switchChannel(channel);
                this.sendSystemMessage("Twitch relay channel changed to " + channel);
            }
        } else {
            if (this.connected) {
                this.disconnect();
                this.sendSystemMessage("Twitch relay disconnected");
            }
        }
    }

    private void createClientIfNeeded() {
        if (this.chatClient != null) {
            return;
        }

        this.chatClient = TwitchChatBuilder.builder().build();

        this.chatClient.getEventManager().onEvent(IRCMessageEvent.class, this::onChatMessage);
    }

    private void connectToChannel(String channel) {
        this.chatClient.connect();
        this.chatClient.joinChannel(channel);

        this.connected = true;
        this.currentChannel = channel;
    }

    private void switchChannel(String newChannel) {
        this.chatClient.leaveChannel(this.currentChannel);
        this.chatClient.joinChannel(newChannel);

        this.currentChannel = newChannel;
    }

    private void disconnect() {
        if (!this.connected) {
            return;
        }

        this.chatClient.disconnect();
        this.connected = false;
        this.currentChannel = "";
    }

    private void onChatMessage(IRCMessageEvent event) {
        if (!Config.instance().twitchRelayEnabled) {
            return;
        }

        Optional<String> nameOpt = event.getUserDisplayName();
        Optional<String> msgOpt = event.getMessage();

        if (nameOpt.isEmpty() || msgOpt.isEmpty()) {
            return;
        }

        String user = nameOpt.get();
        String message = msgOpt.get();

        // ignore nightbot
        if (event.getUser() != null && event.getUser().getName().equals("nightbot")) {
            return;
        }
        // ignore commands
        if (message.startsWith("!")) {
            return;
        }

        Optional<String> messageIdTag = event.getTagValue("msg-id");
        boolean highlighted = messageIdTag.isPresent() && messageIdTag.get().equals("highlighted-message");

        Optional<String> modTag = event.getTagValue("isMod");
        boolean isMod = modTag.isPresent() && modTag.get().equals("1");

        Optional<String> subscriberTag = event.getTagValue("subscriber");
        boolean isSubscriber = subscriberTag.isPresent() && subscriberTag.get().equals("1");

        Optional<String> bitsTag = event.getTagValue("bits");
        int bitCount = Integer.parseInt(bitsTag.orElse("0"));

        String colorCode = event.getUserChatColor().orElse("#FFFFFF");
        int color = Integer.parseInt(colorCode.replace("#", ""), HEX_RADIX);

        this.sendTwitchMessage(new TwitchChatMessage(user, message, color, highlighted, isMod, isSubscriber, bitCount));
    }

    private void sendTwitchMessage(TwitchChatMessage message) {
        Text prefix = this.formatter.formatPrefix(message);

        MutableText highlightMarker = Text.empty();

        if (message.isHighlighted()) {
            highlightMarker = this.formatter.getHighlightMarker();
        }

        Text content = Text.empty()
                .append(highlightMarker
                        .append(Text.literal(message.user())
                                .styled(style -> style.withColor(message.userColor()))
                        )
                        .append(Text.literal(": "))
                        .append(Text.literal(message.message()))
                );

        MutableText result = Text.empty()
                .append(prefix)
                .append(Text.literal(" "))
                .append(content);

        StreamerMode.MC.inGameHud.getChatHud().addMessage(result);
    }

    private void sendSystemMessage(String text) {
        Text sys = Text.literal("[StreamerMode] " + text)
                .styled(style -> style.withColor(Palette.MINT_LIGHT));

        StreamerMode.MC.inGameHud.getChatHud().addMessage(sys);
    }

}
