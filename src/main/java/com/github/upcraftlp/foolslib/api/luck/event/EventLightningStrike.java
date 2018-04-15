package com.github.upcraftlp.foolslib.api.luck.event;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EventLightningStrike extends LuckyEvent {

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        for(int i = 0; i <  RANDOM.nextInt(7); i++) {
            EntityLightningBolt lightningBolt = new EntityLightningBolt(world, pos.getX() - 2 + RANDOM.nextInt(5), pos.getY(), pos.getZ() - 2 + RANDOM.nextInt(5));
            world.addWeatherEffect(lightningBolt);
        }
    }
}
