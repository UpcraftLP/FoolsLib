package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.client.ModelCustomArmor;
import com.github.upcraftlp.foolslib.api.item.armor.EnumArmorType;
import com.github.upcraftlp.foolslib.api.item.armor.EnumRenderIndex;
import com.github.upcraftlp.foolslib.api.item.armor.ItemArmorBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemSkinArmor extends ItemArmorBase {

    @SideOnly(Side.CLIENT)
    protected ModelCustomArmor helmet = new ModelCustomArmor(EnumArmorType.HELMET);
    @SideOnly(Side.CLIENT)
    protected ModelCustomArmor chest = new ModelCustomArmor(EnumArmorType.CHESTPLATE);
    @SideOnly(Side.CLIENT)
    protected ModelCustomArmor legs = new ModelCustomArmor(EnumArmorType.LEGGINGS);
    @SideOnly(Side.CLIENT)
    protected ModelCustomArmor boots = new ModelCustomArmor(EnumArmorType.BOOTS);

    public ItemSkinArmor(String name, ArmorMaterial armorMaterial, EnumRenderIndex renderIndex, EnumArmorType armorType) {
        super(name, armorMaterial, renderIndex, armorType);
    }

    @Override
    public abstract String getArmorTexture(ItemStack stack, Entity entity, int slot, String type);

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        switch(armorSlot) {
            case 0:
                return helmet;
            case 1:
                return chest;
            case 2:
                return legs;
            case 3:
            default:
                return boots;
        }
    }
}
