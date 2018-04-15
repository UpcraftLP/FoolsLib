package com.github.upcraftlp.foolslib.api.net;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.net.packet.CPacketSyncCapability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(FoolsLib.MODID);
    public static int packetID = 0;

    public static void init() {
        register(CPacketSyncCapability.Handler.class, CPacketSyncCapability.class, Side.CLIENT);
    }

    /**
     * convenience method to register packets
     */
    public static <REQ extends IMessage, REPLY extends IMessage> void register(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        INSTANCE.registerMessage(messageHandler, requestMessageType, packetID++, side);
    }

    /**
     * convenience method to register packets
     */
    public static <REQ extends IMessage & IMessageHandler<REQ, REPLY>, REPLY extends IMessage> void register(Class<REQ> message, Side side) {
        INSTANCE.registerMessage(message, message, packetID++, side);
    }
}
