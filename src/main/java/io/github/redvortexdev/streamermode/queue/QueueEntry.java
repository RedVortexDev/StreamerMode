package io.github.redvortexdev.streamermode.queue;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueueEntry {

    private static final Pattern ENTRY_PLOT_ID_REGEX = Pattern.compile("^[a-z0-9-]+");
    private static final String DESCRIPTION_REGEX = "^\\d+\\. ";
    private static final ArrayList<String> hiddenEntries = new ArrayList<>();

    private final boolean beta;
    private final Integer position;
    private final String description;
    private String plotId;

    public QueueEntry(String rawEntry, int i) {
        // Contains Beta
        this.beta = rawEntry.toLowerCase().contains("beta");

        // Description (Full entry excluding position)
        this.description = rawEntry.replaceFirst(DESCRIPTION_REGEX, "");

        // Queue Position
        this.position = i;

        // Plot ID
        Matcher matcher = ENTRY_PLOT_ID_REGEX.matcher(this.description);
        if (matcher.find()) {
            this.plotId = matcher.group(0);
        }

    }

    public static ArrayList<String> getHiddenEntries() {
        return hiddenEntries;
    }

    public boolean isBeta() {
        return this.beta;
    }

    private String getDescription() {
        return this.description;
    }

    public String getStrippedDescription() {
        try {
            return this.getDescription().replaceAll(this.getPlotId(), "").replaceFirst("^( |-)+|\\1$", "") // ??
                    .trim();
        } catch (NullPointerException e) {
            return this.getDescription().trim();
        }
    }

    public Integer getPosition() {
        return this.position;
    }

    public String getPlotId() {
        return this.plotId;
    }

}
