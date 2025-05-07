package io.github.redvortexdev.streamermode.message.processor;

import io.github.redvortexdev.streamermode.message.Message;

public abstract class MessageProcessor {

    /**
     * Use {@link Message#hide()} to cancel the message
     */
    protected abstract void handle(Message message);

}
