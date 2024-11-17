package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class BuycraftXUpdateCheck extends MessageCheck {
    private static final Pattern BUYCRAFT_UPDATE_REGEX = Pattern.compile("^A new version of BuycraftX \\([0-9.]+\\) is available\\. Go to your server panel at https://server.tebex.io/plugins to download the update\\.$");

    @Override
    public MessageType getType() {
        return MessageType.BUYCRAFT_PLUGIN_UPDATE;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return BUYCRAFT_UPDATE_REGEX.matcher(stripped).matches();
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hidePluginUpdate;
    }
}
