package io.github.redvortexdev.streamermode.twitch;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.util.chat.ChatSender;
import io.github.redvortexdev.streamermode.util.chat.ChatType;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class TwitchChatRelay {

    public static final int UPDATE_INTERVAL = 20;
    private static final TwitchChatRelay INSTANCE = new TwitchChatRelay();
    private static final TwitchMessageFormatter FORMATTER = new TwitchMessageFormatter();

    private TwitchChat chatClient;
    private boolean connected;
    private String currentChannel;
    private int tickCounter = 0;

    private TwitchChatRelay() {
        this.connected = false;
        this.currentChannel = "";

        CompletableFuture.runAsync(() -> {
            String channel = Config.getInstance().getTwitchRelayChannel().trim();

            if (Config.getInstance().isTwitchRelayEnabled() && !channel.isEmpty()) {
                this.createClientIfNeeded();
                this.connectToChannel(channel);
                ChatSender.sendMessage("Twitch relay connecting to " + channel, ChatType.INFO);
            }
        });
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
        this.updateConnection();
    }

    public void updateConnection() {
        boolean enabled = Config.getInstance().isTwitchRelayEnabled();
        String channel = Config.getInstance().getTwitchRelayChannel().trim();

        if (enabled && !channel.isEmpty() && StreamerMode.isOnDiamondFire()) {
            if (!this.connected) {
                this.createClientIfNeeded();
                this.connectToChannel(channel);
                ChatSender.sendMessage("Twitch relay connected to " + channel, ChatType.INFO);
            } else if (!channel.equals(this.currentChannel)) {
                this.switchChannel(channel);
                ChatSender.sendMessage("Twitch relay channel changed to " + channel, ChatType.INFO);
            }
        } else {
            if (this.connected) {
                this.disconnect();
                ChatSender.sendMessage("Twitch relay disconnected", ChatType.INFO);
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
        this.connected = true;
        this.currentChannel = channel;

        CompletableFuture.runAsync(() -> {
            this.chatClient.connect();
            this.chatClient.joinChannel(channel);
        });
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
        if (!Config.getInstance().isTwitchRelayEnabled()) {
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
        TextColor color = TextColor.fromHexString(colorCode);

        this.sendTwitchMessage(new TwitchChatMessage(user, message, color, highlighted, isMod, isSubscriber, bitCount));
    }

    private void sendTwitchMessage(TwitchChatMessage message) {
        Component prefix = FORMATTER.formatPrefix(message);

        Component highlightMarker = Component.empty();

        if (message.isHighlighted()) {
            highlightMarker = FORMATTER.getHighlightMarker();
        }

        Component content = Component.empty()
                .append(highlightMarker
                        .append(Component.text(message.user(), message.userColor()))
                        .append(Component.text(": "))
                        .append(Component.text(message.message()))
                );

        Component result = Component.empty()
                .append(prefix)
                .append(Component.text(" "))
                .append(content);

        StreamerMode.MC.inGameHud.getChatHud().addMessage(FabricClientAudiences.of().toNative(result));
    }

}
