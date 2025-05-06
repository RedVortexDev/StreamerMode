package io.github.redvortexdev.streamermode.util.chat;

import io.github.redvortexdev.streamermode.util.Palette;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum ChatType {

    SUCCESS(Palette.MINT_LIGHT),
    INFO(Palette.SKY),
    FAIL(Palette.RED_LIGHT);

    private final TextColor color;

    ChatType(TextColor color) {
        this.color = color;
    }

    public Component getPrefix() {
        return Component.text("» ", this.color, TextDecoration.BOLD);
    }

}
