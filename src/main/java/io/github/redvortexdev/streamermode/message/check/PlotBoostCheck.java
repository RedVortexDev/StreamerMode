package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class PlotBoostCheck extends MessageCheck {

    private static final Pattern PLOT_BOOST_REGEX = Pattern.compile(" {78}\n(.+)\n {29}\u200c→ Click to join!\n {78}");

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.PLOT_BOOST;
    }

    @Override
    public boolean passesCheck(Message message) {
        return PLOT_BOOST_REGEX.matcher(message.getStripped()).matches();
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHidePlotBoosts();
    }

}
