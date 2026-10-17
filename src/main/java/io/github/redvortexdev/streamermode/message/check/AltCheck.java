package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class AltCheck extends MessageCheck {

    private static final Pattern ALT_SCAN_REGEX = Pattern.compile("^Scanning \\S+( on .+)?\\.\n\\[Online] \\[Offline] \\[Banned] \\[IPBanned]$"
        + "|^Scanning \\d+ online players\\.\\. \\[Online] \\[Offline] \\[Banned]$");

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
        return Config.HANDLER.instance().hideModeration;
    }

}
