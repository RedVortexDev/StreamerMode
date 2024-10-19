package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class FastAsyncWorldEditUpdateCheck extends MessageCheck {
    private static final Pattern WORLD_EDIT_UPDATE_REGEX = Pattern.compile("^\\(FAWE\\) An update for FastAsyncWorldEdit is available\\. You are \\d+ build\\(s\\) out of date\\.\\nYou are running build \\d+, the latest version is build \\d+\\.\\nUpdate at https://www.spigotmc.org/resources/\\d+/$");

    @Override
    public MessageType getType() {
        return MessageType.WORLD_EDIT_PLUGIN_UPDATE;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return WORLD_EDIT_UPDATE_REGEX.matcher(stripped).matches();
    }

    @Override
    public void onReceive(Message message) {

    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hidePluginUpdate;
    }
}
