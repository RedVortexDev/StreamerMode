package io.github.redvortexdev.streamermode.twitch.prefix;

import io.github.redvortexdev.streamermode.twitch.TwitchChatMessage;
import net.kyori.adventure.text.Component;

public interface PrefixHandler {

    Component getText(TwitchChatMessage message);

}
