package io.github.redvortexdev.streamermode.twitch;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.chat.SideChat;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.util.chat.ChatSender;
import io.github.redvortexdev.streamermode.util.chat.ChatType;
import io.github.redvortexdev.streamermode.util.chat.SectionSanitizer;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.client.player.LocalPlayer;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public final class TwitchChatRelay {

    public static final int UPDATE_INTERVAL = 20;
    private static final TwitchChatRelay INSTANCE = new TwitchChatRelay();
    private static final TwitchMessageFormatter FORMATTER = new TwitchMessageFormatter();

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "StreamerMode-Twitch");
        thread.setDaemon(true);
        return thread;
    });

    private final AtomicBoolean updateQueued = new AtomicBoolean();
    private int tickCounter = 0;

    private TwitchChat chatClient;
    private boolean connected;
    private String currentChannel;

    private TwitchChatRelay() {
        this.connected = false;
        this.currentChannel = "";
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
        if (!this.updateQueued.compareAndSet(false, true)) {
            return;
        }

        EXECUTOR.execute(() -> {
            this.updateQueued.set(false);
            try {
                this.applyConnectionState();
            } catch (Throwable ex) {
                StreamerMode.LOGGER.error("Error while updating Twitch relay connection", ex);
            }
        });
    }

    private void applyConnectionState() {
        boolean enabled = Config.HANDLER.instance().twitchRelayEnabled;
        String channel = Config.HANDLER.instance().twitchRelayChannel.trim();

        if (enabled && !channel.isEmpty() && StreamerMode.isOnDiamondFire()) {
            if (!this.connected) {
                this.createClientIfNeeded();
                this.connectToChannel(channel);

                StreamerMode.MC.execute(() -> ChatSender.sendMessage("Twitch relay connected to " + channel, ChatType.INFO));
            } else if (!channel.equals(this.currentChannel)) {
                this.switchChannel(channel);

                StreamerMode.MC.execute(() -> ChatSender.sendMessage("Twitch relay channel changed to " + channel, ChatType.INFO));
            }
        } else if (this.connected) {
            this.disconnect();

            StreamerMode.MC.execute(() -> ChatSender.sendMessage("Twitch relay disconnected", ChatType.INFO));
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
        this.chatClient.disconnect();
        this.connected = false;
        this.currentChannel = "";
    }

    private void onChatMessage(IRCMessageEvent event) {
        if (!Config.HANDLER.instance().twitchRelayEnabled) {
            return;
        }

        Optional<String> nameOptional = event.getUserDisplayName();
        Optional<String> messageOptional = event.getMessage();

        if (nameOptional.isEmpty() || messageOptional.isEmpty()) {
            return;
        }

        String user = nameOptional.get();
        String message = SectionSanitizer.sanitize(messageOptional.get());

        if (event.getUser() != null && event.getUser().getName().equals("nightbot")) {
            return;
        }
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
        Component result = createTwitchMessage(message);

        StreamerMode.MC.execute(() -> {
            LocalPlayer player = StreamerMode.MC.player;
            if (player == null) {
                return;
            }

            if (SideChat.isEnabled()) {
                SideChat.addMessage(MinecraftClientAudiences.of().asNative(result));
            } else {
                player.sendMessage(result);
            }
        });
    }

    private static @NonNull Component createTwitchMessage(TwitchChatMessage message) {
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

        return Component.empty()
            .append(prefix)
            .append(Component.text(" "))
            .append(content);
    }

}
