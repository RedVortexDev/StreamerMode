package io.github.redvortexdev.streamermode.message.processor;

import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.processor.impl.MessageDebugLogger;
import io.github.redvortexdev.streamermode.message.processor.impl.MessageHider;
import io.github.redvortexdev.streamermode.message.processor.impl.MessageHidingQueuer;

public abstract class MessageProcessorRunner {

    private static final MessageProcessor[] MESSAGE_PROCESSORS = new MessageProcessor[]{
            new MessageDebugLogger(),
            new MessageHidingQueuer(),
            new MessageHider()
    };

    public static void process(Message message) {
        for (MessageProcessor processor : MESSAGE_PROCESSORS) {
            processor.handle(message);
        }
    }

}
