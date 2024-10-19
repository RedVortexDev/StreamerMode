package io.github.redvortexdev.streamermode.chat.message.finalizer;

import io.github.redvortexdev.streamermode.chat.MessageGrabber;
import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageFinalizer;

public class MessageGrabberFinalizer extends MessageFinalizer {
    @Override
    protected void receive(Message message) {
        if (MessageGrabber.isActive()) {
            MessageGrabber.supply(message);
        }
    }
}