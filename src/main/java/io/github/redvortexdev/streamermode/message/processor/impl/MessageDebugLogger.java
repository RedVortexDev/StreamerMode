package io.github.redvortexdev.streamermode.message.processor.impl;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.processor.MessageProcessor;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.text.Text;

public class MessageDebugLogger extends MessageProcessor {

    @Override
    protected void handle(Message message) {
        if (Config.HANDLER.instance().debugging) {
            if (StreamerMode.MC.world == null) {
                return;
            }
            StreamerMode.LOGGER.info(GsonComponentSerializer.gson().serialize(message.getComponent()));
        }
    }

}
