package io.github.redvortexdev.streamermode.chat.message;

public abstract class MessageCheck {

    public abstract MessageType getType();

    public abstract boolean check(Message message, String stripped);

    public abstract boolean streamerHideEnabled();

}
