package io.github.redvortexdev.streamermode.chat;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class MessageGrabber {

    private static final List<Text> currentMessages = new ArrayList<>();
    private static final List<MessageGrabberTask> tasks = new ArrayList<>();
    private static Consumer<List<Text>> messageConsumer;
    private static int messagesToGrab = 0;
    private static boolean silent = false;
    private static MessageType filter = null;
    private static Date timeout = null;

    public static void grabSilently(int messages, int time, Consumer<List<Text>> consumer, MessageType filter) {
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
        if (messages > 0) grabSilently(messages, getDefaultTimeout(), ignored -> {
        }, null);
    }

    public static void supply(Message msg) {
        if (filter != null && !msg.typeIs(filter)) return;
        if (timeout != null && new Date().after(timeout)) return;

        Text message = msg.getText();
        currentMessages.add(message);

        if (silent) msg.cancel();

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
        return 1000;
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