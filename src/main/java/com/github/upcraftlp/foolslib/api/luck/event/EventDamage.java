package com.github.upcraftlp.foolslib.api.luck.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EventDamage extends LuckyEvent {

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        player.attackEntityFrom(DamageSource.magic, RANDOM.nextFloat() * 12.0F);
    }
}
