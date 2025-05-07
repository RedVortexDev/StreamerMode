package io.github.redvortexdev.streamermode.message.processor.impl;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.processor.MessageProcessor;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.minecraft.text.Text;

public class MessageDebugLogger extends MessageProcessor {

    @Override
    protected void handle(Message message) {
        if (Config.getInstance().isDebugging()) {
            if (StreamerMode.MC.world == null) {
                return;
            }
            StreamerMode.LOGGER.info(Text.Serialization.toJsonString(FabricClientAudiences.of().toNative(message.getComponent()), StreamerMode.MC.world.getRegistryManager()));
        }
    }

}
