package io.github.redvortexdev.streamermode.util.chat;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.util.Palette;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public enum ChatType {

    SUCCESS(Palette.MINT_LIGHT, Config.Sound.NONE),
    INFO(Palette.SKY, Config.Sound.NONE),
    FAIL(Palette.SALMON, Config.Sound.DIDGERIDOO);

    private final TextColor color;
    private final Config.Sound sound;

    ChatType(TextColor color, Config.Sound sound) {
        this.color = color;
        this.sound = sound;
    }

    public TextColor getColor() {
        return this.color;
    }

    public Config.Sound getSound() {
        return this.sound;
    }

    public MutableText getPrefix() {
        return Text.literal("» ").withColor(this.color.getRgb()).formatted(Formatting.BOLD);
    }

}
