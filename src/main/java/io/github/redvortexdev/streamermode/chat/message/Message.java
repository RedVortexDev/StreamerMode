package io.github.redvortexdev.streamermode.chat.message;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.chat.MessageGrabber;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.event.ReceiveSoundEvent;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Message {
    private final Text text;
    private final CallbackInfo callback;
    private final MessageType type;

    private MessageCheck check;

    public Message(GameMessageS2CPacket packet, CallbackInfo ci) {
        this.text = packet.content();
        this.callback = ci;
        this.type = MessageChecker.run(this);
        MessageFinalizers.run(this);
    }

    public Text getText() {
        return text;
    }

    public MessageCheck getCheck() {
        return check;
    }

    public void setCheck(MessageCheck check) {
        this.check = check;
    }

    public String getStripped() {
        return text.getString();
    }

    public boolean typeIs(MessageType toCompare) {
        return type == toCompare;
    }

    /**
     * Cancels this message. Also cancels the associated sound if there is any, plus cancels following messages if they are part.
     */
    public void cancel() {
        System.out.println("cancel");
        callback.cancel();

        if (type.hasSound()) {
            ReceiveSoundEvent.cancelNextSound();
        }
        MessageGrabber.hide(type.getMessageAmount() - 1);

        if (Config.instance().debugging) {
            StreamerMode.LOGGER.info("[CANCELLED] {}", text.toString());
        }
    }
}