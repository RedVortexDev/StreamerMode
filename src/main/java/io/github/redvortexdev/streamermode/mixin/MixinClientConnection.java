package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.StreamerMode;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "connect(Ljava/lang/String;ILnet/minecraft/network/listener/PacketListener;Lnet/minecraft/network/packet/c2s/handshake/ConnectionIntent;)V", at = @At("TAIL"))
    public void connect(CallbackInfo ci) {
        if (StreamerMode.STREAMER_MODE_ALLOWED_PLAYERS.contains(StreamerMode.MC.getSession().getUuidOrNull())) {
            StreamerMode.enable();
        }
    }
}
