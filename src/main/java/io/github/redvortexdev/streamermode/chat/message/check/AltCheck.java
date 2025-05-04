package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class AltCheck extends MessageCheck {

    private static final Pattern ALT_SCAN_REGEX = Pattern.compile("^Scanning \\w+(.|\n)*\\[Online] \\[Offline] \\[(IP|)Banned]\1*$");

    @Override
    public MessageType getType() {
        return MessageType.SCANNING;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return ALT_SCAN_REGEX.matcher(stripped).matches();
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideModeration;
    }

}
