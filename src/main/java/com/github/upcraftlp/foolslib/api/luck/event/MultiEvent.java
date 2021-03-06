package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.api.luck.ILuckyEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MultiEvent implements ILuckyEvent {

    private final ILuckyEvent[] events;

    public MultiEvent(ILuckyEvent... events) {
        this.events = events;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        for(ILuckyEvent event : this.events) {
            event.activate(block, world, pos, player, luck);
        }
    }
}
