package com.github.upcraftlp.foolslib.api.world;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WorldScheduledTask {

    private final Runnable task;
    private int delay;

    public WorldScheduledTask(Runnable task) {
        this.task = task;
    }

    public void schedule(int ticks) {
        this.delay = ticks;
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            if(this.delay-- <= 0) {
                this.task.run();
                FMLCommonHandler.instance().bus().unregister(this);
            }
        }

    }
}
