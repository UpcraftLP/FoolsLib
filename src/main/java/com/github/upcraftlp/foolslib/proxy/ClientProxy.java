package com.github.upcraftlp.foolslib.proxy;

import com.github.upcraftlp.foolslib.api.net.NetworkHandler;
import com.github.upcraftlp.foolslib.api.net.packet.CPacketSyncCapability;
import com.github.upcraftlp.foolslib.client.ClientUpdateHandler;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
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
        NetworkHandler.register(CPacketSyncCapability.Handler.class, CPacketSyncCapability.class, Side.CLIENT);
        if(FoolsConfig.enableUpdateChecker) FMLCommonHandler.instance().bus().register(new ClientUpdateHandler());
    }
}
