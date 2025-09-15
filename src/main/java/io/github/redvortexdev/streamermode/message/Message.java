package io.github.redvortexdev.streamermode.message;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.processor.MessageProcessorRunner;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.Component;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A message about to be sent to the player that can be hidden by a passing check's message type.
 */
public class Message {

    private final Text text;
    private final CallbackInfo callback;
    private final MessageCheckType passedCheckType;

    public Message(GameMessageS2CPacket packet, CallbackInfo ci) {
        this.text = packet.content();
        this.callback = ci;
        this.passedCheckType = MessageChecker.getPassedMessageTypeCheck(this);
        MessageProcessorRunner.process(this);
    }

    public Component getComponent() {
        return MinecraftClientAudiences.of().asAdventure(this.text);
    }

    public String getStripped() {
        return this.text.getString();
    }

    public MessageCheckType getPassedCheckType() {
        return this.passedCheckType;
    }

    /**
     * Hides this message, following ones associated with it if there are any, and the associated sound if there is any.
     */
    public void cancel() {
        this.callback.cancel();

        if (Config.HANDLER.instance().debugging) {
            StreamerMode.LOGGER.info("[CANCELLED] [{}] {} | {}", this.passedCheckType.name(), this.getStripped(), this.text);
        }
    }

}
