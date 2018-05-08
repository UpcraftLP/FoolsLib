package com.github.upcraftlp.foolslib.api.item.armor;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.item.ItemArmor;

import java.util.Locale;

public class ItemArmorBase extends ItemArmor {

    public ItemArmorBase(String name, ArmorMaterial materialIn, EnumRenderIndex renderIndex, EnumArmorType armorType) {
        super(materialIn, renderIndex.ordinal(), armorType.ordinal());
        String prefixedName = ModHelper.getActiveModID() + "." + name + "_" + armorType.name().toLowerCase(Locale.ROOT);
        this.setUnlocalizedName(prefixedName);
    }


}
