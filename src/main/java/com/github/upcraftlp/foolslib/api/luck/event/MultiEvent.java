package com.github.upcraftlp.foolslib.api.luck.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MultiEvent extends LuckyEvent {

    private final LuckyEvent[] events;

    public MultiEvent(LuckyEvent... events) {
        this.events = events;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        for(LuckyEvent event : this.events) {
            event.activate(block, world, pos, player, luck);
        }
    }
}
