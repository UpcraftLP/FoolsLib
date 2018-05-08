package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.api.world.WorldScheduledTask;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.lang.ref.WeakReference;

public class DelayedEvent extends LuckyEvent {

    private final int delayTicks;
    private final Runnable task;
    protected ResourceLocation block;
    protected WeakReference<World> world;
    protected WeakReference<EntityPlayerMP> player;
    protected int luck;
    protected BlockPos pos;


    public DelayedEvent(int delayTicks, Runnable task) {
        this.delayTicks = delayTicks;
        this.task = task;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        this.block = block;
        this.world = new WeakReference<>(world);
        this.pos = pos;
        this.player = new WeakReference<>(player);
        this.luck = luck;
        new WorldScheduledTask(task).schedule(delayTicks);
    }
}
