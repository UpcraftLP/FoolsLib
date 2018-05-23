package com.github.upcraftlp.foolslib.api.item.armor;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemSkinArmor extends ItemArmorBase {

    public ItemSkinArmor(String name, ArmorMaterial armorMaterial, EnumRenderIndex renderIndex, EnumArmorType armorType) {
        super(name, armorMaterial, renderIndex, armorType);
    }

    @SideOnly(Side.CLIENT)
    public abstract ResourceLocation getSkinTexture();

}
