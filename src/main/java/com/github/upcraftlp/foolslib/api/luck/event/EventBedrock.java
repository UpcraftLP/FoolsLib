package com.github.upcraftlp.foolslib.api.luck.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EventBedrock extends LuckyEvent {

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                for(int k = -1; k < 1; k++)
                if(RANDOM.nextBoolean()) {
                    world.setBlockState(pos.add(i, k, j), Blocks.bedrock.getDefaultState());
                }
            }
        }
    }
}
