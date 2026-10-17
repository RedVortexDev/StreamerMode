package io.github.redvortexdev.streamermode.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.redvortexdev.streamermode.chat.SideChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen {

    @WrapOperation(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;captureClickableText(Lnet/minecraft/client/gui/ActiveTextCollector;IILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;)V"))
    private void streamerMode$captureSideChatClickableText(ChatComponent chat, ActiveTextCollector activeTextCollector, int screenHeight, int ticks, ChatComponent.DisplayMode displayMode, Operation<Void> original) {
        original.call(chat, activeTextCollector, screenHeight, ticks, displayMode);

        if (SideChat.isVisible()) {
            SideChat.captureClickableText(activeTextCollector, Minecraft.getInstance().getWindow().getGuiScaledWidth(), screenHeight, ticks, displayMode);
        }
    }

}
