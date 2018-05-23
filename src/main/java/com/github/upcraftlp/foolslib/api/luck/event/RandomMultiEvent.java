package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.api.luck.ILuckyEvent;
import com.github.upcraftlp.foolslib.api.luck.LuckyBlockRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RandomMultiEvent implements ILuckyEvent {

    private final int eventCount;

    public RandomMultiEvent(int eventCount) {
        this.eventCount = eventCount;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        for(int i = 0; i < eventCount; i ++) {
            ILuckyEvent event;
            do {
                event = LuckyBlockRegistry.getRegistryFor(block).getRandomEvent(luck);
            }
            while (event instanceof RandomMultiEvent); //filter out other RandomEvents to not cause infinite loops
            event.activate(block, world, pos, player, luck);
        }
    }
}
