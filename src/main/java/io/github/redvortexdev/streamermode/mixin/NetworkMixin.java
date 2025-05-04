package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientCommonNetworkHandler.class})
public class NetworkMixin {

    @Inject(method = {"onPacketException"}, at = {@At("HEAD")}, cancellable = true)
    private void onPacketException(Packet<?> packet, Exception exception, CallbackInfo ci) {
        if (Config.instance().networkProtocolErrorSuppression) {
            StreamerMode.LOGGER.error("Network Protocol Error suppressed for {}", packet, exception);
            ci.cancel();
        }
    }
}