package io.github.redvortexdev.streamermode.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import io.github.redvortexdev.streamermode.StreamerMode;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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
    @AutoGen(category = "misc")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    @CustomDescription("If enabled, the mod will print debug messages to the console.")
    public boolean debugging = false;

    @SerialEntry
    @AutoGen(category = "misc")
    @EnumCycler
    @CustomDescription("The sound to play when a report is received.")
    public Sound reportSound = Sound.NONE;


    public static Config instance() {
        return HANDLER.instance();
    }

    public enum Sound {
        SHIELD_BLOCK(SoundEvents.ITEM_SHIELD_BLOCK),

        BASS_DRUM(SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM.value()),
        BANJO(SoundEvents.BLOCK_NOTE_BLOCK_BANJO.value()),
        BASS(SoundEvents.BLOCK_NOTE_BLOCK_BASS.value()),
        BELL(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value()),
        BIT(SoundEvents.BLOCK_NOTE_BLOCK_BIT.value()),
        CHIME(SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value()),
        COW_BELL(SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL.value()),
        DIDGERIDOO(SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO.value()),
        FLUTE(SoundEvents.BLOCK_NOTE_BLOCK_FLUTE.value()),
        GUITAR(SoundEvents.BLOCK_NOTE_BLOCK_GUITAR.value()),
        Harp(SoundEvents.BLOCK_NOTE_BLOCK_HARP.value()),
        PLING(SoundEvents.BLOCK_NOTE_BLOCK_PLING.value()),
        HAT(SoundEvents.BLOCK_NOTE_BLOCK_HAT.value()),
        SNARE(SoundEvents.BLOCK_NOTE_BLOCK_SNARE.value()),
        IRON_XYLOPHONE(SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE.value()),
        XYLOPHONE(SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE.value()),

        EXPERIENCE_ORB_PICKUP(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP),
        ITEM_PICKUP(SoundEvents.ENTITY_ITEM_PICKUP),

        NONE(null);

        private final SoundEvent sound;

        Sound(SoundEvent sound) {
            this.sound = sound;
        }

        public SoundEvent getSound() {
            return sound;
        }

        public Sound[] getValues() {
            return values();
        }
    }
}