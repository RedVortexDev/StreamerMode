package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class TeleportCheck extends MessageCheck {

    private static final Pattern TELEPORTING_REGEX = Pattern.compile("^\\[([^ ]{3,}): Teleported ([^ ]{3,}) to ([^ ]{3,})]$");

    @Override
    public MessageType getType() {
        return MessageType.TELEPORTING;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return TELEPORTING_REGEX.matcher(stripped).matches();
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hideModeration;
    }

}
