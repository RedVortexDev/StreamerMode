package io.github.redvortexdev.streamermode.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.ControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.ConfigField;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.ListGroup;
import dev.isxander.yacl3.config.v2.api.autogen.OptionAccess;
import dev.isxander.yacl3.config.v2.api.autogen.StringField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.util.StreamerAllowlist;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public final class Config {

    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(StreamerMode.identifier("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(StreamerMode.MOD_ID + ".json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    // Hiding.

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
    @AutoGen(category = "hiding")
    @ListGroup(valueFactory = StringListFactory.class, controllerFactory = StringListFactory.class)
    public List<String> hideDmsExceptions = StreamerAllowlist.getStreamerNames();

    // Twitch.

    @SerialEntry
    @AutoGen(category = "twitch")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean twitchRelayEnabled = true;

    @SerialEntry
    @AutoGen(category = "twitch")
    @StringField
    public String twitchRelayChannel = "jeremaster104";

    // Miscellaneous.

    @SerialEntry
    @AutoGen(category = "miscellaneous")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean disableAdminVanishOnJoin = true;

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

    public static class StringListFactory implements ListGroup.ValueFactory<String>, ListGroup.ControllerFactory<String> {

        @Override
        public String provideNewValue() {
            return "";
        }

        @Override
        public ControllerBuilder<String> createController(ListGroup annotation, ConfigField<List<String>> field, OptionAccess storage, Option<String> option) {
            return StringControllerBuilder.create(option);
        }

    }

}
