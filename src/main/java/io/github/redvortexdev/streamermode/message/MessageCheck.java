package io.github.redvortexdev.streamermode.message;

public abstract class MessageCheck {

    public abstract MessageCheckType getMessageType();

    public abstract boolean passesCheck(Message message);

    public abstract boolean isCheckEnabled();

}
