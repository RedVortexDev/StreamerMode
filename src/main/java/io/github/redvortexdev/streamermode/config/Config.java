package io.github.redvortexdev.streamermode.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.IntField;
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

    /* TODO:
        * /sb hiding
        * /mb hiding
        * /ab hiding
        * spy hiding (session, dm, muted)
        * /msg hiding
        * plugin update hiding (buycraftx, fawe)
        * plot ad hiding
        * boost hiding
        * report hiding
        * punishment hiding
        * /tp hiding
        * alt scan hiding
        * joined but banned hiding
        *
     */

    @SerialEntry
    @AutoGen(category = "general")
    @Boolean
    public boolean myCoolBoolean = true;

    @SerialEntry
    @AutoGen(category = "general")
    @IntField
    public int myCoolInteger = 5;

    @SerialEntry(comment = "This string is amazing")
    @AutoGen(category = "general")
    @StringField
    public String myCoolString = "How amazing!";

}