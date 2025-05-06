package io.github.redvortexdev.streamermode.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.CustomDescription;
import dev.isxander.yacl3.config.v2.api.autogen.StringField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import io.github.redvortexdev.streamermode.StreamerMode;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class Config {

    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of(StreamerMode.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(StreamerMode.MOD_ID + ".json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    @AutoGen(category = "hiding")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, admin messages will be hidden.")
    public boolean hideAdmin = true;

    @SerialEntry
    @AutoGen(category = "hiding")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, moderation-related messages will be hidden.")
    public boolean hideModeration = true;

    @SerialEntry
    @AutoGen(category = "hiding")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, support-related messages will be hidden.")
    public boolean hideSupport = true;

    @SerialEntry
    @AutoGen(category = "hiding")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, direct messages from players will be hidden.")
    public boolean hideDMs = true;

    @SerialEntry
    @AutoGen(category = "hiding")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, plot advertisements will be hidden.")
    public boolean hidePlotAds = true;

    @SerialEntry
    @AutoGen(category = "hiding")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, plot boosts will be hidden.")
    public boolean hidePlotBoosts = true;

    @SerialEntry
    @AutoGen(category = "hiding")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, session, dm, and muted spies will be hidden.")
    public boolean hideSpy = true;

    @SerialEntry
    @AutoGen(category = "hiding")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, BuycraftX, FastAsyncWorldEdit, and ViaVersion update messages will be hidden.")
    public boolean hidePluginUpdate = true;

    @SerialEntry
    @AutoGen(category = "hiding")
    @StringField
    @CustomDescription("Custom regex to hide messages that match it.")
    public String customRegex = "";

    @SerialEntry
    @AutoGen(category = "twitch")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("Enable Twitch -> Minecraft chat relay.")
    public boolean twitchRelayEnabled = true;

    @SerialEntry
    @AutoGen(category = "twitch")
    @StringField
    @CustomDescription("Channel ID to receive Twitch chat messages from.")
    public String twitchRelayChannel = "jeremaster104";

    @SerialEntry
    @AutoGen(category = "misc")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, Network Protocol Error kicks will be suppressed.")
    public boolean networkProtocolErrorSuppression = true;

    @SerialEntry
    @AutoGen(category = "misc")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, the mod will print debug messages to the console.")
    public boolean debugging = false;

    public static Config instance() {
        return HANDLER.instance();
    }

}
