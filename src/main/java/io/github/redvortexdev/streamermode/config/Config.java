package io.github.redvortexdev.streamermode.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import io.github.redvortexdev.streamermode.StreamerMode;
import net.minecraft.text.Text;

public final class Config {

    private static final Config INSTANCE = new Config();

    private boolean hideAdmin = true;
    private boolean hideModeration = true;
    private boolean hideSupport = true;
    private boolean hideDMs = true;
    private boolean hidePlotAds = true;
    private boolean hidePlotBoosts = true;
    private boolean hideSpy = true;
    private boolean hidePluginUpdate = true;
    private String customRegex = "";
    private boolean twitchRelayEnabled = true;
    private String twitchRelayChannel = "jeremaster104";
    private boolean networkProtocolErrorSuppression = true;
    private boolean nonStreamerJoinNotice = true;
    private boolean debugging = false;

    private Config() {
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    /**
     * Generates the configuration for the mod.
     *
     * @return The generated configuration.
     */
    public YetAnotherConfigLib generateConfig() {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Streamer Mode Configuration"))

                // <editor-fold desc="Category: Hiding">
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Hiding"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Hide Message Types (Streamer Only)"))
                                .description(OptionDescription.of(Text.literal("Control which types of messages to hide while streaming.")))

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide Admin Messages"))
                                        .description(OptionDescription.of(Text.literal("If enabled, admin messages will be hidden.")))
                                        .binding(true, () -> this.hideAdmin, val -> this.hideAdmin = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide Moderation Messages"))
                                        .description(OptionDescription.of(Text.literal("If enabled, moderation-related messages will be hidden.")))
                                        .binding(true, () -> this.hideModeration, val -> this.hideModeration = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide Support Messages"))
                                        .description(OptionDescription.of(Text.literal("If enabled, support-related messages will be hidden.")))
                                        .binding(true, () -> this.hideSupport, val -> this.hideSupport = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide Direct Messages"))
                                        .description(OptionDescription.of(Text.literal("If enabled, direct messages from players will be hidden.")))
                                        .binding(true, () -> this.hideDMs, val -> this.hideDMs = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide Plot Advertisements"))
                                        .description(OptionDescription.of(Text.literal("If enabled, plot advertisements will be hidden.")))
                                        .binding(true, () -> this.hidePlotAds, val -> this.hidePlotAds = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide Plot Boosts"))
                                        .description(OptionDescription.of(Text.literal("If enabled, plot boosts will be hidden.")))
                                        .binding(true, () -> this.hidePlotBoosts, val -> this.hidePlotBoosts = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide Spy Features"))
                                        .description(OptionDescription.of(Text.literal("If enabled, session, dm, and muted spies will be hidden.")))
                                        .binding(true, () -> this.hideSpy, val -> this.hideSpy = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide Plugin Update Messages"))
                                        .description(OptionDescription.of(Text.literal("If enabled, update messages will be hidden.")))
                                        .binding(true, () -> this.hidePluginUpdate, val -> this.hidePluginUpdate = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<String>createBuilder()
                                        .name(Text.literal("Custom Hide Regex"))
                                        .description(OptionDescription.of(Text.literal("Custom regex to hide messages that match it.")))
                                        .binding("", () -> this.customRegex, val -> this.customRegex = val)
                                        .controller(StringControllerBuilder::create)
                                        .build())

                                .build())
                        .build())
                // </editor-fold>

                // <editor-fold desc="Category: Twitch">
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Twitch"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Twitch Relay"))
                                .description(OptionDescription.of(Text.literal("Relay chat messages from Twitch to Minecraft.")))

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Enable Twitch Relay"))
                                        .description(OptionDescription.of(Text.literal("Enable Twitch -> Minecraft chat relay.")))
                                        .binding(true, () -> this.twitchRelayEnabled, val -> this.twitchRelayEnabled = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<String>createBuilder()
                                        .name(Text.literal("Twitch Channel ID"))
                                        .description(OptionDescription.of(Text.literal("Channel ID to receive Twitch chat messages from.")))
                                        .binding("jeremaster104", () -> this.twitchRelayChannel, val -> this.twitchRelayChannel = val)
                                        .controller(StringControllerBuilder::create)
                                        .build())

                                .build())
                        .build())
                // </editor-fold>

                // <editor-fold desc="Category: Miscellaneous">
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Miscellaneous"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Miscellaneous Settings"))

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Suppress Network Protocol Error Kicks"))
                                        .description(OptionDescription.of(Text.literal("If enabled, Network Protocol Error kicks will be suppressed.")))
                                        .binding(true, () -> this.networkProtocolErrorSuppression, val -> this.networkProtocolErrorSuppression = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Non-streamer Join Notice"))
                                        .description(OptionDescription.of(Text.literal("If disabled, you will no longer see a notice when using the mod as a non-streamer")))
                                        .binding(true, () -> this.nonStreamerJoinNotice, val -> this.nonStreamerJoinNotice = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Debugging"))
                                        .description(OptionDescription.of(Text.literal("If enabled, the mod will print debug messages to the console.")))
                                        .binding(false, () -> this.debugging, val -> this.debugging = val)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .build())
                        .build())
                // </editor-fold>

                .build();
    }

    // <editor-fold desc="Getters">
    public boolean isHideAdmin() {
        return StreamerMode.isStreamingAllowed() && this.hideAdmin;
    }

    public boolean isHideModeration() {
        return StreamerMode.isStreamingAllowed() && this.hideModeration;
    }

    public boolean isHideSupport() {
        return StreamerMode.isStreamingAllowed() && this.hideSupport;
    }

    public boolean isHideDMs() {
        return StreamerMode.isStreamingAllowed() && this.hideDMs;
    }

    public boolean isHidePlotAds() {
        return StreamerMode.isStreamingAllowed() && this.hidePlotAds;
    }

    public boolean isHidePlotBoosts() {
        return StreamerMode.isStreamingAllowed() && this.hidePlotBoosts;
    }

    public boolean isHideSpy() {
        return StreamerMode.isStreamingAllowed() && this.hideSpy;
    }

    public boolean isHidePluginUpdate() {
        return StreamerMode.isStreamingAllowed() && this.hidePluginUpdate;
    }

    public String getCustomRegex() {
        if (StreamerMode.isStreamingAllowed()) {
            return "";
        }
        return this.customRegex;
    }

    public boolean isTwitchRelayEnabled() {
        return this.twitchRelayEnabled;
    }

    public String getTwitchRelayChannel() {
        return this.twitchRelayChannel;
    }

    public boolean isNetworkProtocolErrorSuppression() {
        return this.networkProtocolErrorSuppression;
    }

    public boolean isNonStreamerJoinNotice() {
        return this.nonStreamerJoinNotice;
    }

    public boolean isDebugging() {
        return this.debugging;
    }
    // </editor-fold>

}
