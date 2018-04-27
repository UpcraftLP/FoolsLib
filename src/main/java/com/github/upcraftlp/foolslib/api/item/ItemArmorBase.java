package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.item.ItemArmor;

public class ItemArmorBase extends ItemArmor {

    private static final String[] names = {"helmet", "chestplate", "leggings", "boots"};

    public ItemArmorBase(String name, ArmorMaterial materialIn,  int renderIndex, int armorType) {
        super(materialIn, renderIndex, armorType);
        String prefixedName = ModHelper.getActiveModID() + "." + name + "_" + names[armorType];
        this.setUnlocalizedName(prefixedName);
    }

}
