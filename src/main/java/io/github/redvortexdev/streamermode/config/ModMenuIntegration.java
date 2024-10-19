package io.github.redvortexdev.streamermode.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.redvortexdev.streamermode.StreamerMode;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> StreamerMode.isAllowed() ? Config.HANDLER.generateGui().generateScreen(parentScreen) : null;
    }
}
