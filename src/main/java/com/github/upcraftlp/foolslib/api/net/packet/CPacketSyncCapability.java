package com.github.upcraftlp.foolslib.api.net.packet;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class CPacketSyncCapability implements IMessage {

    //FIXME finish!

    private NBTTagCompound data;
    private UUID entityID;
    private String capabilityName;

    @SuppressWarnings("unused")
    public CPacketSyncCapability() {
        //NO-OP
    }

    public CPacketSyncCapability(EntityPlayer player, IExtendedEntityProperties capability, String capabilityName) {
        NBTTagCompound nbt = new NBTTagCompound();
        capability.saveNBTData(nbt);
        this.data = nbt;
        this.entityID = player.getUniqueID();
        this.capabilityName = capabilityName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = new UUID(buf.readLong(), buf.readLong());
        this.capabilityName = ByteBufUtils.readUTF8String(buf);
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.entityID.getMostSignificantBits());
        buf.writeLong(this.entityID.getLeastSignificantBits());
        ByteBufUtils.writeUTF8String(buf, this.capabilityName);
        ByteBufUtils.writeTag(buf, this.data);
    }

    public static class Handler implements IMessageHandler<CPacketSyncCapability, IMessage> {

        @Override
        public IMessage onMessage(CPacketSyncCapability message, MessageContext ctx) {
            World world = Minecraft.getMinecraft().theWorld;
            if(world != null) {
                EntityPlayer player = world.getPlayerEntityByUUID(message.entityID);
                if(player == null) return null; //ignore entities that are too far away
                IExtendedEntityProperties capability = player.getExtendedProperties(message.capabilityName);
                capability.loadNBTData(message.data);
            }
            else if(FoolsConfig.isDebugMode) FoolsLib.getLogger().warn("received capability sync packet for capability \"{}\" when world was null, ignoring!", message.capabilityName);
            return null;
        }
    }
}
