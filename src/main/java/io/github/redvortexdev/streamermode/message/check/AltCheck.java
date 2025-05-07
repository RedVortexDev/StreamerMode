package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class AltCheck extends MessageCheck {

    private static final Pattern ALT_SCAN_REGEX = Pattern.compile("^Scanning \\w+(.|\n)*\\[Online] \\[Offline] \\[(IP|)Banned]\1*$");

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.SCANNING;
    }

    @Override
    public boolean passesCheck(Message message) {
        return ALT_SCAN_REGEX.matcher(message.getStripped()).matches();
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideModeration();
    }

}
