package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class PlotBoostCheck extends MessageCheck {
    private static final Pattern PLOT_BOOST_REGEX = Pattern.compile(" {78}\n(.+)\n {29}\u200c→ Click to join!\n {78}");

    @Override
    public MessageType getType() {
        return MessageType.PLOT_BOOST;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return PLOT_BOOST_REGEX.matcher(stripped).matches();
    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hidePlotBoosts;
    }
}
