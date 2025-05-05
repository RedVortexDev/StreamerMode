package io.github.redvortexdev.streamermode.twitch;

import io.github.redvortexdev.streamermode.twitch.prefix.BitsPrefixHandler;
import io.github.redvortexdev.streamermode.twitch.prefix.PrefixHandler;
import io.github.redvortexdev.streamermode.twitch.prefix.SimplePrefixHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TwitchMessageFormatter {

    private final PrefixHandler defaultPrefix = new SimplePrefixHandler("T", "Twitch Relay");
    private final PrefixHandler modPrefix = new SimplePrefixHandler("M", "Moderator");
    private final PrefixHandler subscriberPrefix = new SimplePrefixHandler("S", "Subscriber");
    private final PrefixHandler bitsPrefix = new BitsPrefixHandler();

    public Text formatPrefix(TwitchChatMessage message) {
        MutableText prefix = Text.empty();

        prefix.append(this.defaultPrefix.getText(message));

        if (message.isMod()) {
            prefix.append(this.modPrefix.getText(message));
        }

        if (message.isSubscriber()) {
            prefix.append(this.subscriberPrefix.getText(message));
        }

        if (message.bitCount() > 0) {
            prefix.append(this.bitsPrefix.getText(message));
        }

        return prefix;
    }

    public MutableText getHighlightMarker() {
        return Text.empty().styled(
                style -> style.withInsertion("twitch_relay_highlighted")
        );
    }

}
