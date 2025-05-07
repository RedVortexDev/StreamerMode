package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class TeleportCheck extends MessageCheck {

    private static final Pattern TELEPORTING_REGEX = Pattern.compile("^\\[([^ ]{3,}): Teleported ([^ ]{3,}) to ([^ ]{3,})]$");

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.TELEPORTING;
    }

    @Override
    public boolean passesCheck(Message message) {
        return TELEPORTING_REGEX.matcher(message.getStripped()).matches();
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHideModeration();
    }

}
