package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.config.Config;
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
        // The support leave message is sent after the sound play, left in debugging code
        // to be able to confirm this in the future if the bug is fixed.
        if (Config.instance().debugging) {
            StreamerMode.LOGGER.info("[SOUND] {}", packet.getSound().getKey().get().getValue().getPath());
        }
        if (StreamerMode.isAllowed() && ReceiveSoundEvent.onEvent()) {
            if (Config.instance().debugging) {
                StreamerMode.LOGGER.info("^ Cancelled");
            }
            ci.cancel();
        } else {
            if (Config.instance().debugging) {
                StreamerMode.LOGGER.info("^ Not cancelled");
            }
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
