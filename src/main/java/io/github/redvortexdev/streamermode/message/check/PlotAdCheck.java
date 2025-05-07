package io.github.redvortexdev.streamermode.message.check;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheck;
import io.github.redvortexdev.streamermode.message.MessageCheckType;

import java.util.regex.Pattern;

public class PlotAdCheck extends MessageCheck {

    private static final Pattern PLOT_AD_REGEX = Pattern.compile(" {32}\\[ Plot Ad ] {32}\\n(.+)\\n {78}");

    @Override
    public MessageCheckType getMessageType() {
        return MessageCheckType.PLOT_AD;
    }

    @Override
    public boolean passesCheck(Message message) {
        return PLOT_AD_REGEX.matcher(message.getStripped()).matches();
    }

    @Override
    public boolean isCheckEnabled() {
        return Config.getInstance().isHidePlotAds();
    }

}
