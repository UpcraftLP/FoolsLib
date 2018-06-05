package com.github.upcraftlp.foolslib.client;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.item.armor.EnumArmorType;
import com.github.upcraftlp.foolslib.api.item.armor.ItemSkinArmor;
import com.github.upcraftlp.foolslib.api.util.EventBusSubscriber;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(value = FoolsLib.MODID, forge = true)
public class SkinArmorRenderer {

    private static ResourceLocation armorTexture;
    public static final ModelEmpty EMPTY_MODEL = new ModelEmpty();

    @SubscribeEvent
    public void onRenderArmor(RenderPlayerEvent.Pre event) {
        for(EnumArmorType type : EnumArmorType.values()) {
            ItemStack stack = event.entityPlayer.getCurrentArmor(type.getArmorSlot());
            if(stack == null || !(stack.getItem() instanceof ItemSkinArmor)) {
                return;
            }
        }
        ItemSkinArmor armor = (ItemSkinArmor) event.entityPlayer.getCurrentArmor(0).getItem();
        armorTexture = armor.getSkinTexture();
    }

    @SubscribeEvent
    public void afterRenderArmor(RenderPlayerEvent.Post event) {
        if(armorTexture != null) armorTexture = null;
    }

    @SuppressWarnings("unused")
    public static ResourceLocation getTexture(AbstractClientPlayer player) {
        if(hasSkinTexture()) return armorTexture;
        else return player.getLocationSkin();
    }

    public static boolean hasSkinTexture() {
        return armorTexture != null;
    }
}