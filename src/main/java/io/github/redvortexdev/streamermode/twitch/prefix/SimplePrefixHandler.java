package io.github.redvortexdev.streamermode.twitch.prefix;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.twitch.TwitchChatMessage;
import io.github.redvortexdev.streamermode.util.Palette;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;

public class SimplePrefixHandler implements PrefixHandler {

    private final String prefix;
    private final String description;

    public SimplePrefixHandler(String prefix, String description) {
        this.prefix = prefix;
        this.description = description;
    }

    @Override
    public Component getText(TwitchChatMessage message) {
        return Component.text(this.prefix)
                .font(StreamerMode.identifier("twitch_relay"))
                .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(this.description, Palette.PURPLE)));
    }

}
