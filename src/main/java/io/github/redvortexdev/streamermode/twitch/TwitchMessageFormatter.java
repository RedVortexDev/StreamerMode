package io.github.redvortexdev.streamermode.twitch;

import io.github.redvortexdev.streamermode.twitch.prefix.BitsPrefixHandler;
import io.github.redvortexdev.streamermode.twitch.prefix.PrefixHandler;
import io.github.redvortexdev.streamermode.twitch.prefix.SimplePrefixHandler;
import net.kyori.adventure.text.Component;

public class TwitchMessageFormatter {

    private final PrefixHandler defaultPrefix = new SimplePrefixHandler("T", "Twitch Relay");
    private final PrefixHandler modPrefix = new SimplePrefixHandler("M", "Moderator");
    private final PrefixHandler subscriberPrefix = new SimplePrefixHandler("S", "Subscriber");
    private final PrefixHandler bitsPrefix = new BitsPrefixHandler();

    public Component formatPrefix(TwitchChatMessage message) {
        Component prefix = Component.empty();

        prefix = prefix.append(this.defaultPrefix.getText(message));

        if (message.isMod()) {
            prefix = prefix.append(this.modPrefix.getText(message));
        }

        if (message.isSubscriber()) {
            prefix = prefix.append(this.subscriberPrefix.getText(message));
        }

        if (message.bitCount() > 0) {
            prefix = prefix.append(this.bitsPrefix.getText(message));
        }

        return prefix;
    }

    public Component getHighlightMarker() {
        return Component.empty().insertion("twitch_relay_highlighted");
    }

}
