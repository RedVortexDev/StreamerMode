package io.github.redvortexdev.streamermode.queue;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueueEntry {

    private static final Pattern ENTRY_REGEX = Pattern.compile("^\\d+\\. (?<id>[a-z][a-z0-9-]{2,19}|[0-9]+) [ -]*(?<description>.+)$");
    private static final ArrayList<String> hiddenEntries = new ArrayList<>();

    private final boolean beta;
    private final int position;
    private final String description;
    private final String plotId;

    public QueueEntry(int position, String plotId, String description, boolean beta) {
        this.position = position;
        this.plotId = plotId;
        this.description = description;
        this.beta = beta;
    }

    public static QueueEntry of(String rawEntry, int position) {
        Matcher matcher = ENTRY_REGEX.matcher(rawEntry);
        if (!matcher.find()) {
            return null;
        }

        String plotId = matcher.group("id");
        String description = matcher.group("description").trim();
        boolean beta = description.toLowerCase(Locale.ROOT).contains("node beta");

        return new QueueEntry(position, plotId, description, beta);
    }

    public static ArrayList<String> getHiddenEntries() {
        return hiddenEntries;
    }

    public boolean isBeta() {
        return this.beta;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getPosition() {
        return this.position;
    }

    public String getPlotId() {
        return this.plotId;
    }

}
