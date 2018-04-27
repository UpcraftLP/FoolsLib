package com.github.upcraftlp.foolslib.api.enchantment;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentBase extends Enchantment {

    public EnchantmentBase(int enchID, String name, int enchWeight, EnumEnchantmentType enchType) {
        this(enchID, new ResourceLocation(ModHelper.getActiveModID(), name), enchWeight, enchType);
    }

    public EnchantmentBase(int enchID, ResourceLocation name, int enchWeight, EnumEnchantmentType enchType) {
        super(enchID, name, enchWeight, enchType);
        this.setName(name.getResourceDomain() + "." + name.getResourcePath());
    }

}
