package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.twitch.TwitchMessageFormatter;
import io.github.redvortexdev.streamermode.util.Palette;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class MixinDrawContext {

    @Unique
    private static final int ALPHA_SHIFT = 24;
    @Unique
    private static final int BYTE_MASK = 0xFF;

    @Inject(method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V", at = @At("HEAD"))
    public void streamerMode$drawTextWithShadow(Font textRenderer, FormattedCharSequence text, int x, int y, int color, boolean shadow, CallbackInfo ci) {
        this.drawPurpleBackground(textRenderer, text, x, y, color);
    }

    @Unique
    private void drawPurpleBackground(Font textRenderer, FormattedCharSequence text, int x, int y, int color) {
        MutableComponent[] mutableTexts = {Component.empty()};
        text.accept((index, style, c) -> {
            mutableTexts[0] = mutableTexts[0].append(Component.literal(Character.toString(c)).setStyle(style));
            return true;
        });
        MutableComponent mutableText = mutableTexts[0];

        // Scan for an insert component of TwitchMessageFormatter.HIGHLIGHT_MARKER
        for (Component sibling : mutableText.getSiblings()) {
            if (sibling.getStyle().getInsertion() != null && sibling.getStyle().getInsertion().equals(TwitchMessageFormatter.HIGHLIGHT_MARKER)) {
                // draw the text with a purple background
                GuiGraphics context = (GuiGraphics) (Object) this;
                int alpha = (color >> ALPHA_SHIFT) & BYTE_MASK;
                context.fill(x - 1, y, x + textRenderer.width(text) + 1, y + textRenderer.lineHeight, (alpha << ALPHA_SHIFT) | (Palette.PURPLE.value()));
                return;
            }
        }
    }

}
