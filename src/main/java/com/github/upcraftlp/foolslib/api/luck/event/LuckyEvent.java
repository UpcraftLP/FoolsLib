package com.github.upcraftlp.foolslib.api.luck.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Random;

public abstract class LuckyEvent {

    protected static final Random RANDOM = new Random();

    public abstract void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck);
}
