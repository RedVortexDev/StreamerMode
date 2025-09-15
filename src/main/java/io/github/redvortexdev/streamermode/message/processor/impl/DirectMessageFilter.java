package io.github.redvortexdev.streamermode.message.processor.impl;

import io.github.redvortexdev.streamermode.config.Config;
import io.github.redvortexdev.streamermode.message.Message;
import io.github.redvortexdev.streamermode.message.MessageCheckType;
import io.github.redvortexdev.streamermode.message.check.DirectMessageCheck;
import io.github.redvortexdev.streamermode.message.processor.MessageProcessor;
import io.github.redvortexdev.streamermode.util.StreamerAllowlist;

public class DirectMessageFilter extends MessageProcessor {

    private static boolean isUserAllowedDirectMessage(Message message) {
        return StreamerAllowlist.isPlayerAllowed(DirectMessageCheck.getSendingPlayer(message));
    }

    @Override
    protected void handle(Message message) {
        if (Config.HANDLER.instance().hideDMs && message.getPassedCheckType() == MessageCheckType.DIRECT_MESSAGE && !isUserAllowedDirectMessage(message)) {
            message.cancel(); // hide if the message is a direct message and the user is not allowed
        }
    }

}
