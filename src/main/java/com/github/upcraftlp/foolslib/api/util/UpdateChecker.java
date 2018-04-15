package com.github.upcraftlp.foolslib.api.util;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;

public class UpdateChecker {

    public static final List<String> MODS_TO_CHECK = new ArrayList<>();

    /**
     * used to register a mod for update notifications
     * @param modid the modid String
     */
    public static void registerMod(String modid) {
        MODS_TO_CHECK.add(modid);
    }

    public static boolean hasUpdate(String modid) {
        ForgeVersion.CheckResult result = getResult(modid);
        ForgeVersion.Status status = result.status;
        if(status == ForgeVersion.Status.PENDING || status == ForgeVersion.Status.FAILED) {
            FoolsLib.getLogger().warn("Error getting update status for {}, found status: {}!", modid, status.toString());
            return false;
        }
        else return status == ForgeVersion.Status.OUTDATED || (FoolsConfig.announceBetaUpdates && status == ForgeVersion.Status.BETA_OUTDATED);
    }

    public static ForgeVersion.CheckResult getResult(String modid) {
        return ForgeVersion.getResult(FMLCommonHandler.instance().findContainerFor(modid));
    }

    public static String getLatestVersion(String modid) {
        return getResult(modid).target.toString();
    }

}
