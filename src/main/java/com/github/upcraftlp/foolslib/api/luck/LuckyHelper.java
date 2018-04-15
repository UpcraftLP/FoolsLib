package com.github.upcraftlp.foolslib.api.luck;

import com.github.upcraftlp.foolslib.api.luck.event.EventDrop;
import com.github.upcraftlp.foolslib.api.luck.event.EventLightningStrike;
import com.github.upcraftlp.foolslib.api.luck.event.EventNotFound;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;

public class LuckyHelper {

    public static void registerDefaultDrops(ResourceLocation luckyblock) {
        LuckyBlockRegistry registry = LuckyBlockRegistry.getRegistryFor(luckyblock);

        //yes, this will indeed result in every single item in the registry dropped.
        Iterator registryKeys = Item.itemRegistry.iterator();
        ItemStack[] stacks = new ItemStack[Item.itemRegistry.getKeys().size()];
        int i = 0;
        while (registryKeys.hasNext()) {
            Item item = (Item) registryKeys.next();
            stacks[i++] = new ItemStack(item);
        }

        registry.register(EnumLuck.NEUTRAL, new EventDrop(Math.sqrt(stacks.length / Math.PI) / 2, stacks));
        registry.register(EnumLuck.NEUTRAL, new EventNotFound());

        registry.register(EnumLuck.BAD, new EventLightningStrike());
    }

    public static void registerDefaultBowDrops(ResourceLocation luckyblock) {
        LuckyBlockRegistry registry = LuckyBlockRegistry.getRegistryFor(luckyblock);
        registry.register(EnumLuck.BAD, new EventLightningStrike());

    }
}
