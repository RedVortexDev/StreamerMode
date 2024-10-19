package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

public class DirectMessageCheck extends MessageCheck {
    private static final String DIRECT_MESSAGE_REGEX = "^\\[(\\w{3,16}) → You] .+$";

    public static boolean usernameMatches(Message message, String username) {
        return message.getStripped().matches("^\\[" + username + " → You] .+$");
    }

    @Override
    public MessageType getType() {
        return MessageType.DIRECT_MESSAGE;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return stripped.matches(DIRECT_MESSAGE_REGEX);
    }

    @Override
    public void onReceive(Message message) {

    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideDMs;
    }
}
