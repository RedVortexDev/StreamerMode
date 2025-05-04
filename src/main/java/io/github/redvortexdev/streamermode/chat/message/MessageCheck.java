package io.github.redvortexdev.streamermode.chat.message;

public abstract class MessageCheck {

    public abstract MessageType getType();

    public abstract boolean check(Message message, String stripped);

    /**
     * Use {@link Message#cancel()} to cancel the message
     */
    public void onReceive(Message message) {
    }

    public abstract boolean streamerHideEnabled();

}
