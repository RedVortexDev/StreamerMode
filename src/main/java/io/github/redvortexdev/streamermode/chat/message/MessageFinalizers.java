package io.github.redvortexdev.streamermode.chat.message;

import io.github.redvortexdev.streamermode.chat.message.finalizer.DebugFinalizer;
import io.github.redvortexdev.streamermode.chat.message.finalizer.MessageGrabberFinalizer;
import io.github.redvortexdev.streamermode.chat.message.finalizer.StreamerModeFinalizer;

public abstract class MessageFinalizers {

    private static final MessageFinalizer[] finalizers = new MessageFinalizer[]{
            new StreamerModeFinalizer(),
            new DebugFinalizer(),
            new MessageGrabberFinalizer()
    };

    public static void run(Message message) {
        for (MessageFinalizer finalizer : finalizers) {
            finalizer.receive(message);
        }
    }
}