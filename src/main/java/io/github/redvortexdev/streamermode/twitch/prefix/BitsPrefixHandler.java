package io.github.redvortexdev.streamermode.twitch.prefix;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.twitch.TwitchChatMessage;
import io.github.redvortexdev.streamermode.util.Palette;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class BitsPrefixHandler implements PrefixHandler {

    @Override
    public Text getText(TwitchChatMessage message) {
        BitColor bitColor = BitColor.fromBits(message.bitCount());
        if (bitColor == BitColor.NONE) {
            return Text.empty();
        }

        return Text.empty().styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(message.bitCount() + " Bits").withColor(bitColor.getColor().getRgb()))))
                .append(Text.literal(bitColor.getFontLetter())
                        .styled(style -> style.withFont(StreamerMode.identifier("twitch_relay"))))
                .append(Text.literal("" + message.bitCount())
                        .styled(style -> style.withColor(bitColor.getColor()).withBold(true)));
    }

    public enum BitColor {
        NONE("X", 0, Palette.GRAY),
        GRAY("1", 1, Palette.GRAY),
        PURPLE("2", 100, Palette.PURPLE),
        GREEN("3", 1000, Palette.MINT),
        BLUE("4", 5000, Palette.SKY),
        RED("5", 10000, Palette.SALMON);

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
