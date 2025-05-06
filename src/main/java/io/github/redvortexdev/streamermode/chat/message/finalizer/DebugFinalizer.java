package io.github.redvortexdev.streamermode.chat.message.finalizer;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageFinalizer;
import io.github.redvortexdev.streamermode.config.Config;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.minecraft.text.Text;

public class DebugFinalizer extends MessageFinalizer {

    @Override
    protected void receive(Message message) {
        if (Config.instance().debugging) {
            if (StreamerMode.MC.world == null) {
                return;
            }
            StreamerMode.LOGGER.info(Text.Serialization.toJsonString(FabricClientAudiences.of().toNative(message.getComponent()), StreamerMode.MC.world.getRegistryManager()));
        }
    }

}
