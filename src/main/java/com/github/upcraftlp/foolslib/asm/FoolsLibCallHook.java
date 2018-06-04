package com.github.upcraftlp.foolslib.asm;

import com.github.upcraftlp.foolslib.FoolsLib;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

import java.util.Map;

public class FoolsLibCallHook implements IFMLCallHook {
    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public Void call() throws Exception {
        FoolsLib.getLogger().debug("Hello Minecraft! When is LastCube going to come back?");
        return null;
    }
}
