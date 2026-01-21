package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.util.StreamerAllowlist;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class MixinClientConnection {

    @Inject(method = "initiateServerboundConnection(Ljava/lang/String;ILnet/minecraft/network/ProtocolInfo;Lnet/minecraft/network/ProtocolInfo;Lnet/minecraft/network/ClientboundPacketListener;Lnet/minecraft/network/protocol/handshake/ClientIntent;)V", at = @At("TAIL"))
    public void connect(CallbackInfo ci) {
        if (StreamerAllowlist.isStreamer(StreamerMode.MC.getUser().getProfileId())) {
            StreamerMode.enableStreaming();
        }
    }

}
