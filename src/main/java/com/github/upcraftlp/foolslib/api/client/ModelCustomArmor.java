package com.github.upcraftlp.foolslib.api.client;

import com.github.upcraftlp.foolslib.api.item.armor.EnumArmorType;
import com.github.upcraftlp.foolslib.api.item.ItemSkinArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCustomArmor extends ModelBiped {

    private final EnumArmorType slot;

    public ModelCustomArmor(EnumArmorType armorType) {
        this.slot = armorType;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        //FIXME does not render?

        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        if(entityIn instanceof EntityPlayer) {
            ItemStack stack = ((EntityPlayer) entityIn).getCurrentArmor(slot.ordinal());
            if(stack != null && stack.getItem() instanceof ItemSkinArmor) {
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(stack.getItem().getArmorTexture(stack, entityIn, slot.ordinal(), null)));
            }
        }

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(entityIn.posX, entityIn.posY, entityIn.posZ);
            if(this.isChild) {
                if(slot == EnumArmorType.HELMET) { //larger baby head
                    GlStateManager.scale(0.75F, 0.75F, 0.75F);
                    GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
                    this.bipedHead.render(scale);
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                }
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            }
            else if(entityIn.isSneaking()) GlStateManager.translate(0.0F, 0.2F, 0.0F);

            switch(slot) {
                //render
                case HELMET:
                    if(!this.isChild) this.bipedHead.render(scale);
                    this.bipedHeadwear.render(scale);
                    break;
                case CHESTPLATE:
                    this.bipedBody.render(scale);
                    this.bipedRightArm.render(scale);
                    this.bipedLeftArm.render(scale);
                    break;
                case LEGGINGS:
                case BOOTS:
                    this.bipedRightLeg.render(scale);
                    this.bipedLeftLeg.render(scale);
            }
        }
        GlStateManager.popMatrix();
    }
}
