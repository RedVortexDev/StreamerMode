package io.github.redvortexdev.streamermode.twitch.prefix;

import io.github.redvortexdev.streamermode.twitch.TwitchChatMessage;
import net.minecraft.text.Text;

public interface PrefixHandler {

    Text getText(TwitchChatMessage message);

}
