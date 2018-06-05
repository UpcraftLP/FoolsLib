package com.github.upcraftlp.foolslib.proxy;

import com.github.upcraftlp.foolslib.api.net.NetworkHandler;
import com.github.upcraftlp.foolslib.api.net.packet.CPacketSyncCapability;
import com.github.upcraftlp.foolslib.api.server.net.DummyNetworkHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        NetworkHandler.register(DummyNetworkHandler.class, CPacketSyncCapability.class, Side.CLIENT);
    }
}
