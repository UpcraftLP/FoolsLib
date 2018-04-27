package com.github.upcraftlp.foolslib.api.potion;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionBase extends Potion {

    public PotionBase(int potionID, String name, boolean isBadEffect, int color) {
        this(potionID, new ResourceLocation(ModHelper.getActiveModID(), name), isBadEffect, color);
    }

    public PotionBase(int potionID, ResourceLocation name, boolean isBadEffect, int color) {
        super(potionID, name, isBadEffect, color);
        this.setPotionName(name.getResourceDomain() + "." + name.getResourcePath());
    }
}
