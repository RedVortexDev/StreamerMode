package io.github.redvortexdev.streamermode.chat.message;

public abstract class MessageFinalizer {

    /**
     * Use {@link Message#cancel()} to cancel the message
     */
    protected abstract void receive(Message message);

}
