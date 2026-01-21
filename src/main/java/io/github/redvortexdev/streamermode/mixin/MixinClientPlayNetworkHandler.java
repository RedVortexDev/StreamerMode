package io.github.redvortexdev.streamermode.mixin;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.util.SoundCancelQueue;
import io.github.redvortexdev.streamermode.util.chat.ChatSender;
import io.github.redvortexdev.streamermode.util.chat.ChatType;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPlayNetworkHandler {

    @Inject(method = "handleSoundEntityEvent", at = @At("HEAD"), cancellable = true)
    public void onPlaySound(ClientboundSoundEntityPacket packet, CallbackInfo ci) {
        // The support leave message is sent after the sound play, left in debugging code
        // to be able to confirm this in the future if the bug is fixed.
        if (Config.HANDLER.instance().debugging) {
            StreamerMode.LOGGER.info("[SOUND] {}", packet.getSound().unwrapKey().get().identifier().getPath());
        }
        if (StreamerMode.isStreamingAllowed() && SoundCancelQueue.shouldCancelSound()) {
            if (Config.HANDLER.instance().debugging) {
                StreamerMode.LOGGER.info("^ Cancelled");
            }
            ci.cancel();
        } else {
            if (Config.HANDLER.instance().debugging) {
                StreamerMode.LOGGER.info("^ Not cancelled");
            }
        }
    }

    @Inject(method = "handleSystemChat", cancellable = true, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V",
            shift = At.Shift.AFTER
    ))
    public void onGameMessage(ClientboundSystemChatPacket packet, CallbackInfo ci) {
        // It is likely no other servers will send this.
        // Nothing bad will happen if it's faked while already on DiamondFire as the variable is already true.
        if (packet.content().getString().equals("◆ Welcome back to DiamondFire! ◆")) {
            StreamerMode.setOnDiamondFire(true);
            if (!StreamerMode.isStreamingAllowed() && Config.HANDLER.instance().nonStreamerJoinNotice) {
                ChatSender.sendMessage("Streamer-only features are disabled (suppress in config)", ChatType.INFO);
            }
            if (Config.HANDLER.instance().disableAdminVanishOnJoin && StreamerMode.MC.getConnection() != null) {
                StreamerMode.MC.getConnection().sendCommand("adminv off");
            }
        }
        if (Config.HANDLER.instance().debugging) {
            System.out.println(packet.content());
        }

        if (StreamerMode.isOnDiamondFire() && StreamerMode.isStreamingAllowed()) {
            new Message(packet, ci);
        }

    }

}
