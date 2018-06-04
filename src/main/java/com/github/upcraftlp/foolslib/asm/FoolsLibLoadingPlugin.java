package com.github.upcraftlp.foolslib.asm;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(Loader.MC_VERSION)
@IFMLLoadingPlugin.TransformerExclusions({"com.github.upcraftlp.foolslib.asm", "com.github.upcraftlp.foolslib.FoolsLib"})
public class FoolsLibLoadingPlugin implements IFMLLoadingPlugin {

    private static boolean runtimeDeobfuscation;

    public static boolean isNormalEnvironment() {
        return runtimeDeobfuscation;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                "com.github.upcraftlp.foolslib.asm.TransformPlayerRender"
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return "com.github.upcraftlp.foolslib.asm.FoolsLibCallHook";
    }

    @Override
    public void injectData(Map<String, Object> data) {
        runtimeDeobfuscation = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
