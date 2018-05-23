package com.github.upcraftlp.foolslib.api.luck;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface ILuckyEvent {
    
    void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck);
}
