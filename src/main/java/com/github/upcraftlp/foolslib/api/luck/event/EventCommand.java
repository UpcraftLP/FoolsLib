package com.github.upcraftlp.foolslib.api.luck.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EventCommand extends LuckyEvent {

    protected final String[] commands;

    public EventCommand(String... commands) {
        this.commands = commands;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        for(String cmd : commands) {
            String command = cmd.replace("%p%", player.getDisplayNameString());
            //TODO add more values to replace

            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            server.getCommandManager().executeCommand(server, command);
        }
    }
}
