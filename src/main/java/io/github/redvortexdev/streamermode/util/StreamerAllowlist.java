package io.github.redvortexdev.streamermode.util;

import net.fabricmc.loader.api.FabricLoader;

import java.util.Set;
import java.util.UUID;

/**
 * Do not modify the mod to enable Streamer Mode, it includes banned capabilities.
 * Only Jeremaster and developers should be able to use this mod.
 */
public final class StreamerAllowlist {

    private static final Set<AllowedPlayer> ALLOWED_PLAYERS = Set.of(
            new AllowedPlayer("Jeremaster", "6c669475-3026-4603-b3e7-52c97681ad3a"),
            new AllowedPlayer("Maximization", "4a605151-5260-4ea9-9224-4be2d600dddf"),
            new AllowedPlayer("Electrosolt", "cadf53b3-2a42-4119-9ebd-cec6fada6305"),
            new AllowedPlayer("RedVortx", "fc513f92-b662-46e2-ada3-0a3eb73808a3"),
            new AllowedPlayer("GeorgeRNG", "901c4cd0-98b9-4d3d-a8e9-08d15c6a4472")
    );

    private StreamerAllowlist() {
    }

    public static boolean isPlayerAllowed(UUID uuid) {
        if (uuid == null) {
            return false;
        }
        // Let contributors use the mod in development environment.
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            return true;
        }
        return ALLOWED_PLAYERS.stream().anyMatch(player -> player.uuid.equals(uuid));
    }

    public static boolean isPlayerAllowed(String name) {
        System.out.println(name);
        System.out.println(ALLOWED_PLAYERS.stream().anyMatch(player -> player.name.equalsIgnoreCase(name)));
        // Let contributors use the mod in development environment.
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            return true;
        }
        return ALLOWED_PLAYERS.stream().anyMatch(player -> player.name.equalsIgnoreCase(name));
    }

    public record AllowedPlayer(String name, UUID uuid) {
        public AllowedPlayer(String name, String uuid) {
            this(name, UUID.fromString(uuid));
        }
    }

}
