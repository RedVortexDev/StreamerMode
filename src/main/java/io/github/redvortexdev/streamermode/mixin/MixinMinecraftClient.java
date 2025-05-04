package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.config.Config;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "close", at = @At("HEAD"))
    public void close(CallbackInfo ci) {
        Config.HANDLER.save();
    }

}
