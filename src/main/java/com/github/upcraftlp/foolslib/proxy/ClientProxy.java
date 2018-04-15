package com.github.upcraftlp.foolslib.proxy;

import com.github.upcraftlp.foolslib.client.ClientUpdateHandler;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        if(FoolsConfig.enableUpdateChecker) FMLCommonHandler.instance().bus().register(new ClientUpdateHandler());
    }
}
