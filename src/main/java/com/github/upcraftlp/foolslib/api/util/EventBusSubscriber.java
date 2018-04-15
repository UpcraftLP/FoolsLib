package com.github.upcraftlp.foolslib.api.util;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventBusSubscriber {

    /**
     * @return the modid that this class is registered for
     */
    String value();

    /**
     * @return whether this class should be subscribed to the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS} Event Bus
     */
    boolean forge() default false;

    /**
     * @return whether this class should be subscribed to the {@link FMLCommonHandler#bus()} Event Bus
     */
    boolean fml() default false;

    /**
     * @return whether this class should be subscribed to the {@link net.minecraftforge.common.MinecraftForge#TERRAIN_GEN_BUS} Event Bus
     */
    boolean terrainGen() default false;

    /**
     * @return whether this class should be subscribed to the {@link net.minecraftforge.common.MinecraftForge#ORE_GEN_BUS} Event Bus
     */
    boolean oreGen() default false;

    Side[] side() default {Side.CLIENT, Side.SERVER};
}
