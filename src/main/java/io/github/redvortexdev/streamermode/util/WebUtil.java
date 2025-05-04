package io.github.redvortexdev.streamermode.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public final class WebUtil {

    private WebUtil() {
    }

    public static String getString(String urlToRead) throws IOException {
        return getString(urlToRead, Charset.defaultCharset());
    }

    public static String getString(String urlToRead, Charset charset) throws IOException {
        URL url = new URL(urlToRead);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), charset));
        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            builder.append("\n").append(line);
        }
        in.close();
        return builder.toString();
    }

}
