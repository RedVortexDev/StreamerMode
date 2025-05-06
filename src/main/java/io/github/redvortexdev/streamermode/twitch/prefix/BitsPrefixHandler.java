package io.github.redvortexdev.streamermode.twitch.prefix;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.twitch.TwitchChatMessage;
import io.github.redvortexdev.streamermode.util.Palette;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class BitsPrefixHandler implements PrefixHandler {

    @Override
    public Component getText(TwitchChatMessage message) {
        BitColor bitColor = BitColor.fromBits(message.bitCount());
        if (bitColor == BitColor.NONE) {
            return Component.empty();
        }

        return Component.empty().hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(message.bitCount() + " Bits", bitColor.getColor())))
                .append(Component.text(bitColor.getFontLetter())
                        .font(StreamerMode.identifier("twitch_relay")))
                .append(Component.text(message.bitCount(), bitColor.getColor(), TextDecoration.BOLD));
    }

    public enum BitColor {
        NONE("X", 0, Palette.GRAY),
        GRAY("1", 1, Palette.GRAY),
        PURPLE("2", 100, Palette.PURPLE),
        GREEN("3", 1000, Palette.MINT),
        BLUE("4", 5000, Palette.SKY),
        RED("5", 10000, Palette.RED_LIGHT);

        private final String fontLetter;
        private final int bitMinimum;
        private final TextColor color;

        BitColor(String fontLetter, int bitMinimum, TextColor color) {
            this.fontLetter = fontLetter;
            this.bitMinimum = bitMinimum;
            this.color = color;
        }

        public static BitColor fromBits(int bits) {
            BitColor result = NONE;
            for (BitColor color : BitColor.values()) {
                if (bits >= color.getBitMinimum()) {
                    result = color;
                }
            }
            return result;
        }

        public String getFontLetter() {
            return this.fontLetter;
        }

        public int getBitMinimum() {
            return this.bitMinimum;
        }

        public TextColor getColor() {
            return this.color;
        }
    }

}
