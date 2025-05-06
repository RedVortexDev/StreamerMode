package io.github.redvortexdev.streamermode.util;

import io.github.redvortexdev.streamermode.StreamerMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;

public final class WebUtility {

    private WebUtility() {
    }

    public static CompletableFuture<String> getString(String urlToRead) throws IOException {
        return getString(urlToRead, Charset.defaultCharset());
    }

    public static CompletableFuture<String> getString(String urlToRead, Charset charset) throws IOException {
        URL url = URI.create(urlToRead).toURL();

        return CompletableFuture.supplyAsync(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), charset))) {
                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    builder.append("\n").append(line);
                }
                return builder.toString();
            } catch (IOException e) {
                StreamerMode.LOGGER.error("Error reading from URL: {}", urlToRead, e);
                return null;
            }
        });
    }

}
