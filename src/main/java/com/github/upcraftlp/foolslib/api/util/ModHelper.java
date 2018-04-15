package com.github.upcraftlp.foolslib.api.util;

import com.github.upcraftlp.foolslib.FoolsLib;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.File;

public class ModHelper {

    private static String modid = null;
    private static File configDir;

    public static String getActiveModid() {
        return modid != null ? modid : Loader.instance().activeModContainer().getModId();
    }

    public static void setModid(@Nullable String modid) {
        ModHelper.modid = modid;
    }

    /**
     * internal use ONLY
     */
    @SuppressWarnings("unused")
    private static void setup(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), FoolsLib.MODID);
        if(!configDir.exists()) {
            FoolsLib.getLogger().info("creating config directory at {}", configDir.getAbsolutePath());
            configDir.mkdirs();
        }
    }

    public static Configuration getModConfig() {
        return getModConfig(getActiveModid());
    }

    public static Configuration getModConfig(@Nullable String name) {
        return getModConfig(name, "1.0");
    }

    public static Configuration getModConfig(@Nullable String name, String version) {
        return new Configuration(new File(configDir, name != null ? name : getActiveModid() + ".cfg"), version);
    }
}
