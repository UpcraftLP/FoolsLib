package com.github.upcraftlp.foolslib.asm;

import com.github.upcraftlp.foolslib.api.util.PrefixMessageFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(Loader.MC_VERSION)
@IFMLLoadingPlugin.TransformerExclusions({"com.github.upcraftlp.foolslib.asm"})
public class FoolsLibLoadingPlugin implements IFMLLoadingPlugin {

    private static boolean runtimeDeobfuscation;
    public static final Logger log = LogManager.getLogger("FoolsLib:Core", new PrefixMessageFactory("FoolsLib:Core"));

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
