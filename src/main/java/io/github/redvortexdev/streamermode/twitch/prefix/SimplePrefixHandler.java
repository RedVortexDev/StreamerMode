package io.github.redvortexdev.streamermode.twitch.prefix;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.twitch.TwitchChatMessage;
import io.github.redvortexdev.streamermode.util.Palette;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;

public class SimplePrefixHandler implements PrefixHandler {

    private final String prefix;
    private final String description;

    public SimplePrefixHandler(String prefix, String description) {
        this.prefix = prefix;
        this.description = description;
    }

    @Override
    public Text getText(TwitchChatMessage message) {
        return Text.literal(this.prefix)
                .styled(style -> style.withFont(StreamerMode.identifier("twitch_relay"))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(this.description).withColor(Palette.PURPLE.getRgb()))));
    }

}
