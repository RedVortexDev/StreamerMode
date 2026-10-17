package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class PluginUpdateCheck extends MessageCheck {

    private static final Pattern FAWE_BUILD_UPDATE_REGEX = Pattern.compile("^\\(FAWE\\) An update for FastAsyncWorldEdit is available\\. You are \\d+ build\\(s\\) behind\\.\nYou are running build \\d+, the latest build is \\d+\\.\nUpdate at .+$");
    private static final Pattern FAWE_RELEASE_UPDATE_REGEX = Pattern.compile("^\\(FAWE\\) A new release for FastAsyncWorldEdit is available: \\S+\\. You are currently on \\S+\\. Download from .+ or .+\\.$");
    private static final Pattern VIA_VERSION_UPDATE_REGEX = Pattern.compile("^\\[ViaVersion] (?:There is a newer plugin version available: [^ ,]+, you're on: [^ ,]+|You are using a (?:debug/)?custom version, consider updating\\.)$");

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.PLUGIN_UPDATE;
    }

    @Override
    public boolean passesCheck(Message message) {
        String text = message.getStripped();
        return FAWE_BUILD_UPDATE_REGEX.matcher(text).matches()
            || FAWE_RELEASE_UPDATE_REGEX.matcher(text).matches()
            || VIA_VERSION_UPDATE_REGEX.matcher(text).matches();
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.HANDLER.instance().hidePluginUpdate;
    }

}
