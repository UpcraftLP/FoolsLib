package com.github.upcraftlp.foolslib.api.server.net;

import com.github.upcraftlp.foolslib.api.net.packet.CPacketSyncCapability;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * @author jamieswhiteshirt
 * @see "https://github.com/JamiesWhiteShirt/demolitions/blob/4ea3135fa3b42dd089b6fc893e8f588d9f7e05d1/src/main/java/com/jamieswhiteshirt/demolitions/common/network/messagehandler/DummyMessageHandler.java"
 */
@SideOnly(Side.SERVER)
public class DummyNetworkHandler implements IMessageHandler<CPacketSyncCapability, IMessage> {

    @Nullable
    @Override
    public IMessage onMessage(CPacketSyncCapability iMessage, MessageContext messageContext) {
        throw new UnsupportedOperationException("This message handler should never have been called");
    }
}
