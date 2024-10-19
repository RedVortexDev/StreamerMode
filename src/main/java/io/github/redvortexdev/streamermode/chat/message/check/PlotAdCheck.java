package io.github.redvortexdev.streamermode.chat.message.check;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.config.Config;

import java.util.regex.Pattern;

public class PlotAdCheck extends MessageCheck {
    private static final Pattern PLOT_AD_REGEX = Pattern.compile(" {32}\\[ Plot Ad ] {32}\\n(.+)\\n {78}");

    @Override
    public MessageType getType() {
        return MessageType.PLOT_AD;
    }

    @Override
    public boolean check(Message message, String stripped) {
        return PLOT_AD_REGEX.matcher(stripped).matches();
    }

    @Override
    public void onReceive(Message message) {

    }

    @Override
    public boolean streamerHideEnabled() {
        return Config.instance().hidePlotAds;
    }
}
