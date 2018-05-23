package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.api.luck.ILuckyEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class EventNotFound implements ILuckyEvent {

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        ChatStyle style = new ChatStyle().setColor(EnumChatFormatting.RED);
        player.playerNetServerHandler.sendPacket(new S45PacketTitle(S45PacketTitle.Type.TITLE, new ChatComponentTranslation("event.foolslib.not_found_title").setChatStyle(style), 60, 50, 30));
        player.playerNetServerHandler.sendPacket(new S45PacketTitle(S45PacketTitle.Type.SUBTITLE, new ChatComponentTranslation("event.foolslib.not_found_subtitle").setChatStyle(style), 80, 30, 30));
    }
}
