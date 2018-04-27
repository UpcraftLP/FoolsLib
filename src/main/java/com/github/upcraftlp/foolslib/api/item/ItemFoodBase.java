package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood {

    public ItemFoodBase(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(ModHelper.getActiveModID() + "." + name);
    }

}
