package io.github.redvortexdev.streamermode.chat;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public final class MessageGrabber {

    public static final int DEFAULT_TIMEOUT = 1000;
    private static final List<Component> currentMessages = new ArrayList<>();
    private static final List<MessageGrabberTask> tasks = new ArrayList<>();
    private static Consumer<List<Component>> messageConsumer;
    private static int messagesToGrab = 0;
    private static boolean silent = false;
    private static MessageType filter = null;
    private static Date timeout = null;

    private MessageGrabber() {
    }

    public static void grabSilently(int messages, int time, Consumer<List<Component>> consumer, MessageType filter) {
        if (isActive()) {
            tasks.add(new MessageGrabberTask(messages, consumer, true, filter));
            return;
        }
        messagesToGrab = messages;
        messageConsumer = consumer;
        silent = true;
        timeout = new Date(new Date().getTime() + time);
        MessageGrabber.filter = filter;
    }

    public static void hide(int messages) {
        if (messages > 0) {
            grabSilently(messages, getDefaultTimeout(), ignored -> {
            }, null);
        }
    }

    public static void supply(Message msg) {
        if (filter != null && !msg.typeIs(filter)) {
            return;
        }
        if (timeout != null && new Date().after(timeout)) {
            return;
        }

        Component message = msg.getComponent();
        currentMessages.add(message);

        if (silent) {
            msg.cancel();
        }

        if (currentMessages.size() >= messagesToGrab) {
            messageConsumer.accept(currentMessages);
            currentMessages.clear();
            messagesToGrab = 0;
            messageConsumer = null;
            timeout = null;

            if (!tasks.isEmpty()) {
                MessageGrabberTask task = tasks.remove(0);
                messagesToGrab = task.messages();
                messageConsumer = task.consumer();
                silent = task.silent();
            }
        }
    }

    public static int getDefaultTimeout() { // I was rather planning to make this use the player's ping
        return DEFAULT_TIMEOUT;
    }

    /**
     * Resets the grabber, clearing all tasks and the current message list. Currently unused, might have a /streamermode reset command in the future.
     */
    public static void reset() {
        tasks.clear();
        messageConsumer = null;
        messagesToGrab = 0;
        silent = false;
        filter = null;
    }

    public static boolean isActive() {
        return messageConsumer != null;
    }

}
