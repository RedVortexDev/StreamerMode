package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.chat.SideChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatComponent.class)
public class MixinChatComponent {

    @Inject(method = "rescaleChat", at = @At("TAIL"))
    private void streamerMode$rescaleSideChat(CallbackInfo ci) {
        if (this.streamerMode$isMainChat()) {
            SideChat.rescale();
        }
    }

    @Inject(method = "clearMessages", at = @At("TAIL"))
    private void streamerMode$clearSideChat(boolean history, CallbackInfo ci) {
        if (this.streamerMode$isMainChat()) {
            SideChat.clear();
        }
    }

    @Inject(method = "getWidth()I", at = @At("HEAD"), cancellable = true)
    private void streamerMode$sideChatWidth(CallbackInfoReturnable<Integer> cir) {
        if (SideChat.isSideChat((ChatComponent) (Object) this)) {
            cir.setReturnValue(SideChat.getWidth());
        }
    }

    @Inject(method = "getHeight()I", at = @At("HEAD"), cancellable = true)
    private void streamerMode$sideChatHeight(CallbackInfoReturnable<Integer> cir) {
        if (SideChat.isSideChat((ChatComponent) (Object) this)) {
            cir.setReturnValue(SideChat.getHeight());
        }
    }

    @Unique
    private boolean streamerMode$isMainChat() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.gui != null && minecraft.gui.hud.getChat() == (Object) this;
    }

}
