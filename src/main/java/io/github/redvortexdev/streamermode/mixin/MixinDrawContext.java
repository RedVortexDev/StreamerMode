package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.util.Palette;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DrawContext.class)
public abstract class MixinDrawContext {

    @Unique
    private static final int ALPHA_SHIFT = 24;
    @Unique
    private static final int BYTE_MASK = 0xFF;

    @Inject(method = "drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I", at = @At("HEAD"), cancellable = true)
    public void streamerMode$drawTextWithShadow(TextRenderer textRenderer, OrderedText text, int x, int y, int color, CallbackInfoReturnable<Integer> cir) {
        this.drawPurpleBackground(textRenderer, text, x, y, color);
    }

    @Unique
    private void drawPurpleBackground(TextRenderer textRenderer, OrderedText text, int x, int y, int color) {
        MutableText[] mutableTexts = {Text.empty()};
        text.accept((index, style, c) -> {
            mutableTexts[0] = mutableTexts[0].append(Text.literal(Character.toString(c)).setStyle(style));
            return true;
        });
        MutableText mutableText = mutableTexts[0];

        // Scan for an insert component of "twitch_relay_highlighted"
        for (Text sibling : mutableText.getSiblings()) {
            if (sibling.getStyle().getInsertion() != null && sibling.getStyle().getInsertion().equals("twitch_relay_highlighted")) {
                // draw the text with a purple backgroundt
                DrawContext context = (DrawContext) (Object) this;
                int alpha = (color >> ALPHA_SHIFT) & BYTE_MASK;
                context.fill(x - 1, y, x + textRenderer.getWidth(text) + 1, y + textRenderer.fontHeight, (alpha << ALPHA_SHIFT) | (Palette.PURPLE.value()));
                return;
            }
        }
    }

}
