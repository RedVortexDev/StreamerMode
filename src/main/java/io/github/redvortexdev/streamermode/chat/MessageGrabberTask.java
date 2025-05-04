package io.github.redvortexdev.streamermode.chat;

import io.github.redvortexdev.streamermode.chat.message.MessageType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Consumer;

public record MessageGrabberTask(int messages, Consumer<List<Text>> consumer, boolean silent, MessageType filter) {
}
