package io.github.redvortexdev.streamermode.chat.message.finalizer;

import io.github.redvortexdev.streamermode.chat.message.Message;
import io.github.redvortexdev.streamermode.chat.message.MessageCheck;
import io.github.redvortexdev.streamermode.chat.message.MessageFinalizer;
import io.github.redvortexdev.streamermode.chat.message.MessageType;
import io.github.redvortexdev.streamermode.chat.message.check.DirectMessageCheck;

public class StreamerModeFinalizer extends MessageFinalizer {

    private static final String[] HIDE_DMS_EXEMPTIONS = new String[]{
            "RedVortx",
            "GeorgeRNG",
            "Electrosolt"
    };

    private static boolean matchesDirectMessageExemptions(Message message) {
        if (message.typeIs(MessageType.DIRECT_MESSAGE)) {
            for (String username : HIDE_DMS_EXEMPTIONS) {
                if (DirectMessageCheck.usernameMatches(message, username)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void receive(Message message) {
        MessageCheck check = message.getCheck();
        if (
                check != null
                        && check.streamerHideEnabled()
                        && !matchesDirectMessageExemptions(message)
        ) {
            message.cancel();
        }
    }

}
