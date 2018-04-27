package com.github.upcraftlp.foolslib.api.util;

import com.github.upcraftlp.foolslib.FoolsLib;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;
import java.io.File;

public class ModHelper {

    private static String activeModID = null;
    private static File configDir;
    private static boolean hasLoaded = false;

    public static String getActiveModID() {
        if(!StringUtils.isNullOrEmpty(activeModID)) return activeModID;
        String ret = Loader.instance().activeModContainer().getModId();
        return !StringUtils.isNullOrEmpty(ret) ? ret : "minecraft";
    }

    public static void setActiveModID(@Nullable String modid) {
        ModHelper.activeModID = modid;
    }

    /**
     * internal use ONLY
     */
    @SuppressWarnings("unused")
    private static void setup(FMLPreInitializationEvent event) {
        if(hasLoaded) throw new IllegalStateException("attempted setup twice!");
        setActiveModID(FoolsLib.MODID);
        configDir = new File(event.getModConfigurationDirectory(), "foolscraft");
        if(!configDir.exists()) {
            FoolsLib.getLogger().info("creating config directory at {}", configDir.getAbsolutePath());
            if(!configDir.mkdirs()) {
                FoolsLib.getLogger().error("unable to create config directory at {}, aborting launch!", configDir.getAbsolutePath());
                FMLCommonHandler.instance().exitJava(1, false);
            }
        }
        hasLoaded = true;
    }

    public static Configuration getModConfig() {
        return getModConfig(getActiveModID());
    }

    public static Configuration getModConfig(@Nullable String name) {
        return getModConfig(name, null);
    }

    public static Configuration getModConfig(@Nullable String name, @Nullable String version) {
        if(StringUtils.isNullOrEmpty(name)) name = getActiveModID();
        if(StringUtils.isNullOrEmpty(version)) version = FMLCommonHandler.instance().findContainerFor(name).getVersion();
        if("@VERSION@".equals(version)) {
            version = CalendarUtils.getFormattedDateTime();
            FoolsLib.getLogger().warn("Mod {} is using version @VERSION@, substituting current launch time {}as temporary version. The config will not persist!", name, version);
        }
        if(!hasLoaded) throw new IllegalStateException(name + " tried to get a config file before setup has finished!");
        return new Configuration(new File(configDir, name + ".cfg"), version);
    }

}
