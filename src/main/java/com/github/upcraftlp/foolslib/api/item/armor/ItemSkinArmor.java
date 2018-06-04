package com.github.upcraftlp.foolslib.api.item.armor;

import com.github.upcraftlp.foolslib.client.ModelEmpty;
import com.github.upcraftlp.foolslib.client.SkinArmorRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemSkinArmor extends ItemArmorBase {

    private static final ModelEmpty EMPTY_MODEL = new ModelEmpty();

    public ItemSkinArmor(String name, ArmorMaterial armorMaterial, EnumRenderIndex renderIndex, EnumArmorType armorType) {
        super(name, armorMaterial, renderIndex, armorType);
    }

    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        return SkinArmorRenderer.hasSkinTexture() ? EMPTY_MODEL : null;
    }

    @SideOnly(Side.CLIENT)
    public abstract ResourceLocation getSkinTexture();

}
