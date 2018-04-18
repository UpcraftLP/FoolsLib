package com.github.upcraftlp.foolslib.api.client;

import com.github.upcraftlp.foolslib.FoolsLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class ClientHelper {

    private static final String CATEGORY = "general";

    /**
     * @param keyCode a code using the format of LWJGL's Keyboard
     */
    public static KeyBinding registerKeyBinding(String key, int keyCode, @Nullable String modid) {
        return registerKeyBinding(key, keyCode, modid, null);
    }

    /**
     * @param keyCode a code using the format of LWJGL's Keyboard
     */
    public static KeyBinding registerKeyBinding(String key, int keyCode, @Nullable String modid, @Nullable String category) {
        if(modid == null) modid = FoolsLib.MODID;
        if(category == null) category = CATEGORY;
        KeyBinding keyBinding = new KeyBinding("key." + modid + "." + key + ".name", keyCode, "keyCategory." + modid + "." + category);
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }

    public static float getRenderPartialTick() {
        return Minecraft.getMinecraft().timer.renderPartialTicks;
    }
}
