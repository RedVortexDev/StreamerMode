package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectMessageCheck extends MessageCheck {

    private static final String DIRECT_MESSAGE_REGEX = "^\\[(\\w{3,16}) → You] .+$";

    public static String getSendingPlayer(Message message) {
        Matcher matcher = Pattern.compile(DIRECT_MESSAGE_REGEX).matcher(message.getStripped());
        if (!matcher.find()) {
            return null;
        }
        return matcher.group(1);
    }

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.DIRECT_MESSAGE;
    }

    @Override
    public boolean passesCheck(Message message) {
        return message.getStripped().matches(DIRECT_MESSAGE_REGEX);
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideDMs();
    }

}
