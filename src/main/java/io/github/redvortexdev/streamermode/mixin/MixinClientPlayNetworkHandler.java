package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.event.ReceiveSoundEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Inject(method = "onPlaySoundFromEntity", at = @At("HEAD"), cancellable = true)
    public void onPlaySound(PlaySoundFromEntityS2CPacket packet, CallbackInfo ci) {
        if (StreamerMode.isAllowed() && ReceiveSoundEvent.onEvent()) {
            ci.cancel();
        }
    }

    @Inject(method = "onGameMessage", cancellable = true, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/thread/ThreadExecutor;)V",
            shift = At.Shift.AFTER
    ))
    public void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        if (/* TODO: DFState.isOnDF() && */ StreamerMode.isAllowed()) {
            new Message(packet, ci);
        }
    }
}
