package io.github.redvortexdev.streamermode.chat;

import io.github.redvortexdev.streamermode.chat.message.MessageType;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.function.Consumer;

public record MessageGrabberTask(int messages, Consumer<List<Component>> consumer, boolean silent, MessageType filter) {
}
