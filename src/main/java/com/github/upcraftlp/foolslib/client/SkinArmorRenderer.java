package com.github.upcraftlp.foolslib.client;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.item.armor.EnumArmorType;
import com.github.upcraftlp.foolslib.api.item.armor.ItemSkinArmor;
import com.github.upcraftlp.foolslib.api.util.EventBusSubscriber;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(side = Side.CLIENT, value = FoolsLib.MODID, forge = true)
public class SkinArmorRenderer {

    private static final ModelBiped model = new ModelBiped();

    @SubscribeEvent
    public void onRenderArmor(RenderPlayerEvent.Pre event) {
        for(EnumArmorType type : EnumArmorType.values()) {
            ItemStack stack = event.entityPlayer.getCurrentArmor(type.getArmorSlot());
            if(stack == null || !(stack.getItem() instanceof ItemSkinArmor)) {
                return;
            }
        }
        event.setCanceled(true);
        ItemSkinArmor armor = (ItemSkinArmor) event.entityPlayer.getCurrentArmor(0).getItem();
        event.renderer.bindTexture(armor.getSkinTexture());
        model.render(event.entityPlayer, event.entityPlayer.limbSwing, event.entityPlayer.limbSwingAmount, event.entityPlayer.getAge(), event.entityPlayer.rotationYawHead, event.entityPlayer.rotationPitch, 1.1F);
    }
}
