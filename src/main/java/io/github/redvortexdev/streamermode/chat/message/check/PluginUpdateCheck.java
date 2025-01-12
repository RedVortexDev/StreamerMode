package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class PluginUpdateCheck extends MessageCheck {
    private static final Pattern BUYCRAFT_UPDATE_REGEX = Pattern.compile("^A new version of BuycraftX \\([0-9.]+\\) is available\\. Go to your server panel at https://server.tebex.io/plugins to download the update\\.$");
    private static final Pattern WORLD_EDIT_UPDATE_REGEX = Pattern.compile("^\\(FAWE\\) An update for FastAsyncWorldEdit is available\\. You are \\d+ build\\(s\\) out of date\\.\\nYou are running build \\d+, the latest version is build \\d+\\.\\nUpdate at https://www.spigotmc.org/resources/\\d+/$");
    private static final Pattern VIA_VERSION_UPDATE_REGEX = Pattern.compile("^\\[ViaVersion] There is a newer plugin version available: [0-9.]+(-SNAPSHOT)?, you're on: [0-9.]+(-SNAPSHOT)?");

    @Override
    public MessageType getType() {
        return MessageType.PLUGIN_UPDATE;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return VIA_VERSION_UPDATE_REGEX.matcher(stripped).matches() || BUYCRAFT_UPDATE_REGEX.matcher(stripped).matches() || WORLD_EDIT_UPDATE_REGEX.matcher(stripped).matches();
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hidePluginUpdate;
    }
}
