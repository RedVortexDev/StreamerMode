package io.github.redvortexdev.streamermode.util.chat;

import io.github.redvortexdev.streamermode.config.Config;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum ChatType {
    SUCCESS(Formatting.GREEN, Config.Sound.NONE),
    INFO(Formatting.AQUA, Config.Sound.NONE),
    FAIL(Formatting.RED, Config.Sound.DIDGERIDOO);

    final Formatting formatting;
    final Config.Sound sound;

    ChatType(Formatting formatting, Config.Sound sound) {
        this.formatting = formatting;
        this.sound = sound;
    }

    public MutableText getPrefix() {
        return Text.literal("» ").formatted(formatting, Formatting.BOLD);
    }
}
