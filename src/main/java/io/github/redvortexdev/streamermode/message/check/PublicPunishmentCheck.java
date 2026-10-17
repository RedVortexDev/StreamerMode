package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class PublicPunishmentCheck extends MessageCheck {

    private static final Pattern PUBLIC_PUNISHMENT_REGEX = Pattern.compile("^» \\S+ was (?:temp )?(?:IP-)?(?:temp)?(?:banned|muted|warned|kicked|unbanned|unmuted|unwarned)(?: .*)?$", Pattern.DOTALL);

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.PUBLIC_PUNISHMENT;
    }

    @Override
    public boolean passesCheck(Message message) {
        return PUBLIC_PUNISHMENT_REGEX.matcher(message.getStripped()).matches();
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.HANDLER.instance().hidePublicPunishments;
    }

}
