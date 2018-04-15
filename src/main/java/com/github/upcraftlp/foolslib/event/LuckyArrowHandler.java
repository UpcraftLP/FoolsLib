package com.github.upcraftlp.foolslib.event;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.entity.EntityLuckyArrow;
import com.github.upcraftlp.foolslib.api.util.EventBusSubscriber;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(value = FoolsLib.MODID, forge = true)
public class LuckyArrowHandler {

    @SubscribeEvent
    public void onArrowAttack(LivingHurtEvent event) {
        if(event.source instanceof EntityDamageSourceIndirect) {
            EntityDamageSourceIndirect ds = (EntityDamageSourceIndirect) event.source;
            if(ds.getEntity() instanceof EntityLuckyArrow && ds.getSourceOfDamage() instanceof EntityPlayerMP) {
                ((EntityLuckyArrow) ds.getEntity()).activate((EntityPlayerMP) ds.getSourceOfDamage());
            }
        }
    }
}
