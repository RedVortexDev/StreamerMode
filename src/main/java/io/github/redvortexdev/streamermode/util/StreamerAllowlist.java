package io.github.redvortexdev.streamermode.util;

import io.github.redvortexdev.streamermode.config.Config;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Do not modify the mod to enable Streamer Mode, it includes banned capabilities.
 * Only official streamers and developers should be able to use this mod.
 */
public final class StreamerAllowlist {

    private static final Set<Streamer> STREAMERS = Set.of(
            new Streamer("Jeremaster", "6c669475-3026-4603-b3e7-52c97681ad3a"),
            new Streamer("Maximization", "4a605151-5260-4ea9-9224-4be2d600dddf"),
            new Streamer("Electrosolt", "cadf53b3-2a42-4119-9ebd-cec6fada6305"),
            new Streamer("RedVortx", "fc513f92-b662-46e2-ada3-0a3eb73808a3"),
            new Streamer("GeorgeRNG", "901c4cd0-98b9-4d3d-a8e9-08d15c6a4472"),
            new Streamer("TheFoxPlush", "a3875f0b-db89-4d17-bc33-b51c3b84400d")
    );

    private StreamerAllowlist() {
    }

    public static boolean isStreamer(UUID uuid) {
        if (uuid == null) {
            return false;
        }
        // Let contributors use the mod in a development environment.
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            return true;
        }
        return STREAMERS.stream().anyMatch(player -> player.uuid.equals(uuid));
    }

    public static boolean isDmAllowed(String name) {
        boolean isStreamer = STREAMERS.stream().anyMatch(player -> player.name.equalsIgnoreCase(name));
        boolean isDmAllowed = Config.HANDLER.instance().hideDmsExceptions.contains(name);
        return isStreamer || isDmAllowed;
    }

    public static List<String> getStreamerNames() {
        return STREAMERS.stream().map(player -> player.name).toList();
    }

    public record Streamer(String name, UUID uuid) {

        public Streamer(String name, String uuid) {
            this(name, UUID.fromString(uuid));
        }

    }

}
