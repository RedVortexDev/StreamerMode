package io.github.redvortexdev.streamermode.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.StringField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import io.github.redvortexdev.streamermode.StreamerMode;
import net.fabricmc.loader.api.FabricLoader;

public final class Config {

    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(StreamerMode.identifier("config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().getConfigDir().resolve(StreamerMode.MOD_ID + ".json5"))
                            .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean hideAdmin = true;

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean hideModeration = true;

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean hideSupport = true;

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean hideDMs = true;

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean hidePlotAds = true;

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean hidePlotBoosts = true;

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean hideSpy = true;

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean hidePluginUpdate = true;

    @SerialEntry
    @AutoGen(category = "hiding", group = "types")
    @StringField
    public String customRegex = "";

    @SerialEntry
    @AutoGen(category = "twitch")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean twitchRelayEnabled = true;

    @SerialEntry
    @AutoGen(category = "twitch")
    @StringField
    public String twitchRelayChannel = "jeremaster104";

    @SerialEntry
    @AutoGen(category = "miscellaneous")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean networkProtocolErrorSuppression = true;

    @SerialEntry
    @AutoGen(category = "miscellaneous")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean nonStreamerJoinNotice = true;

    @SerialEntry
    @AutoGen(category = "miscellaneous")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean debugging = false;

}
