package io.github.redvortexdev.streamermode.chat;

import io.github.redvortexdev.streamermode.StreamerMode;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.util.Mth;

public final class SideChatHooks {

    private SideChatHooks() {
    }

    public static void register() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.CHAT, StreamerMode.identifier("side_chat"), (graphics, _) -> {
            Minecraft minecraft = StreamerMode.MC;
            if (!SideChat.isVisible() || minecraft.gui.hud.getChat().isChatFocused()) {
                return;
            }

            graphics.nextStratum();
            SideChat.extractRenderState(graphics, minecraft.font, minecraft.gui.hud.getGuiTicks(), -1, -1, ChatComponent.DisplayMode.BACKGROUND, false);
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, _) -> {
            if (!(screen instanceof ChatScreen)) {
                return;
            }

            ScreenEvents.afterExtract(screen).register((_, graphics, mouseX, mouseY, _) -> {
                if (SideChat.isVisible()) {
                    SideChat.extractRenderState(graphics, client.font, client.gui.hud.getGuiTicks(), mouseX, mouseY, ChatComponent.DisplayMode.FOREGROUND, client.hasShiftDown());
                }
            });

            ScreenMouseEvents.allowMouseScroll(screen).register((_, mouseX, _, _, verticalAmount) -> {
                if (!SideChat.isVisible() || !SideChat.isOverSideChat(mouseX, scaledWidth)) {
                    return true;
                }

                double amount = Mth.clamp(verticalAmount, -1.0, 1.0);
                if (!client.hasShiftDown()) {
                    amount *= ChatScreen.MOUSE_SCROLL_SPEED;
                }
                SideChat.scroll((int) amount);
                return false;
            });

            ScreenEvents.remove(screen).register(removed -> SideChat.resetScroll());
        });
    }

}
