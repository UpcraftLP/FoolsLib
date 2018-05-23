package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.luck.LuckyBlockRegistry;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

public interface LuckyEventProvider {

    default void activateEvent(@Nullable ResourceLocation block, World world, BlockPos pos, @Nullable EntityPlayerMP player, int luck) {
        if(block == null) return; //silently discard error
        if(player == null) player = PlayerSelector.matchOnePlayer(FMLCommonHandler.instance().getMinecraftServerInstance(), "@p");
        if(player == null) FoolsLib.getLogger().error("{} unable to find a matching player at [{}, {}, {}] (luck: {})", block, pos.getX(), pos.getY(), pos.getZ(), luck);
        else {
            LuckyBlockRegistry.getRegistryFor(block).getRandomEvent(luck).activate(block, world, pos, player, luck);
        }
    }

    void setLuckyBlock(ResourceLocation luckyBlock);

    ResourceLocation getLuckyBlock();

    void setLuck(int luck);

    int getLuck();

}
