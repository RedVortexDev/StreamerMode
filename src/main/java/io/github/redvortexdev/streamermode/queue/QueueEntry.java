package io.github.redvortexdev.streamermode.queue;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueueEntry {
    private static final Pattern ENTRY_PLOT_ID_REGEX = Pattern.compile("\\d+");
    private static final String DESCRIPTION_REGEX = "^\\d+\\. ";
    public static ArrayList<String> HIDDEN_ENTRIES = new ArrayList<>();

    private final boolean beta;
    private final Integer position;
    private final String description;
    private Integer plotId;

    public QueueEntry(String rawEntry, int i) {
        // Contains Beta
        this.beta = rawEntry.toLowerCase().contains("beta");

        // Description (Full entry excluding position)
        this.description = rawEntry.replaceFirst(DESCRIPTION_REGEX, "");

        // Queue Position
        this.position = i;

        // Plot ID
        Matcher matcher = ENTRY_PLOT_ID_REGEX.matcher(description);
        if (matcher.find()) try {
            this.plotId = Integer.parseInt(matcher.group(0));
        } catch (IndexOutOfBoundsException | IllegalStateException e) {
            this.plotId = null;
        }

    }

    public boolean isBeta() {
        return beta;
    }

    private String getDescription() {
        return description;
    }

    public String getStrippedDescription() {
        try {
            return getDescription().replaceAll(getPlotId().toString(), "").replaceFirst("^( |-)+|\\1$", "") // ??
                    .trim();
        } catch (NullPointerException e) {
            return getDescription().trim();
        }
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getPlotId() {
        return plotId;
    }
}