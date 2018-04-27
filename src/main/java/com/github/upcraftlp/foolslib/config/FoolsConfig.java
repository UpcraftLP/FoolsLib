package com.github.upcraftlp.foolslib.config;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.util.EventBusSubscriber;
import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FoolsConfig {

    public static Configuration config;
    public static final String CATEGORY_GENERAL = "foolslib.config.general";

    public static boolean
            announceBetaUpdates,
            enableUpdateChecker,
            isDebugMode,
            enableStructureCommand;

    public static void init() {
        config = ModHelper.getModConfig(FoolsLib.MODID, FoolsLib.VERSION);
        config.load();
        syncConfig();
        config.save();
    }

    private static void syncConfig() {

        enableUpdateChecker = config.getBoolean("Enable Update Checker", CATEGORY_GENERAL, true, "en/disable the update notifications");
        announceBetaUpdates = config.getBoolean("Announce Beta Updates", CATEGORY_GENERAL, false, "enable to be informed about beta updates for mods");
        isDebugMode = config.getBoolean("Debug Mode", CATEGORY_GENERAL, false, "show additional debug information");
        enableStructureCommand = config.getBoolean("Enable Structure Command", CATEGORY_GENERAL, false, "enable usage of the /loadStructure command");

        if(config.hasChanged()) config.save();
    }

    @EventBusSubscriber(value = FoolsLib.MODID, fml = true)
    public static class Handler {

        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(FoolsLib.MODID.equals(event.modID)) syncConfig();
        }
    }
}
