package com.github.upcraftlp.foolslib.client;

import com.github.upcraftlp.foolslib.api.util.UpdateChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.*;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//FMLCommonHandler#bus()
@SideOnly(Side.CLIENT)
public class ClientUpdateHandler {

    @SubscribeEvent
    public void showUpdateNotification(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if(player != null) {
            for (String modid : UpdateChecker.MODS_TO_CHECK) {
                if (UpdateChecker.hasUpdate(modid)) {
                    ModContainer modContainer = FMLCommonHandler.instance().findContainerFor(modid);
                    ForgeVersion.CheckResult result = UpdateChecker.getResult(modid);

                    IChatComponent update = new ChatComponentTranslation("message.foolslib.update", modContainer.getName(), Loader.MC_VERSION);

                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, result.url);
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(result.url).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA).setItalic(true)));
                    IChatComponent keyword = new ChatComponentTranslation("message.foolslib.update.download.keyword").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.BLUE).setChatClickEvent(clickEvent).setChatHoverEvent(hoverEvent));
                    IChatComponent download = new ChatComponentTranslation("message.foolslib.update.download", result.target, keyword, modContainer.getDisplayVersion());

                    player.addChatComponentMessage(update);
                    player.addChatComponentMessage(download);
                }
            }
            FMLCommonHandler.instance().bus().unregister(this);
        }
    }

}
