package io.github.redvortexdev.streamermode.util.chat;

import java.util.regex.Pattern;

public final class SectionSanitizer {

    private static final Pattern LEGACY_COLOR_PATTERN = Pattern.compile("§[1-9a-fx]");

    private SectionSanitizer() {
    }

    public static String sanitize(final String message) {
        return LEGACY_COLOR_PATTERN.matcher(message).replaceAll("");
    }

}
