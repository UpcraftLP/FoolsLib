package com.github.upcraftlp.foolslib.api.luck;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.luck.event.EventBedrock;
import com.github.upcraftlp.foolslib.api.luck.event.EventDamage;
import com.github.upcraftlp.foolslib.api.luck.event.EventDropRegisteredItems;
import com.github.upcraftlp.foolslib.api.luck.event.EventEnchantedItem;
import com.github.upcraftlp.foolslib.api.luck.event.EventLightningStrike;
import com.github.upcraftlp.foolslib.api.luck.event.EventNotFound;
import com.github.upcraftlp.foolslib.api.luck.event.EventSpawn;
import com.github.upcraftlp.foolslib.api.luck.event.MultiEvent;
import com.github.upcraftlp.foolslib.api.luck.event.RandomMultiEvent;
import com.google.common.annotations.Beta;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.NotImplementedException;

public class LuckyHelper {

    public static void registerDefaultDrops(ResourceLocation luckyblock) {
        LuckyBlockRegistry registry = LuckyBlockRegistry.getRegistryFor(luckyblock);

        ILuckyEvent eventDrop = new EventDropRegisteredItems(0.7D, "minecraft", FoolsLib.MODID);
        ILuckyEvent eventLightningStrike = new EventLightningStrike();
        ILuckyEvent eventDamage = new EventDamage();
        ILuckyEvent randomEvent = new RandomMultiEvent(3);
        ItemStack[] boats = new ItemStack[10*4];
        ItemStack oneBoat = new ItemStack(Items.boat);
        for(int i = 0; i < boats.length; i++) {
            boats[i] = oneBoat;
        }

        registry.register(EnumLuck.VERY_GOOD,
                new EventEnchantedItem(new ItemStack(Items.diamond_sword).setStackDisplayName("GOD SWORD"), 12, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.diamond_helmet), 5, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.diamond_chestplate), 5, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.diamond_leggings), 5, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.diamond_boots), 5, 12, 30)
        );

        ItemStack snowballs = new ItemStack(Items.snowball, 16);
        registry.registerDrop(EnumLuck.GOOD, snowballs, snowballs, snowballs, snowballs);
        registry.register(EnumLuck.GOOD,
                new EventEnchantedItem(new ItemStack(Items.diamond_axe), 3, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.iron_sword), 3, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.chainmail_helmet), 3, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.chainmail_chestplate), 3, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.chainmail_leggings), 3, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.chainmail_boots), 3, 12, 30)
        );

        registry.registerDrop(EnumLuck.NEUTRAL, new ItemStack(Blocks.log, 64, 0), new ItemStack(Blocks.log, 64, 0));
        registry.registerDrop(EnumLuck.NEUTRAL, new ItemStack(Blocks.log, 64, 1), new ItemStack(Blocks.log, 64, 1));
        registry.registerDrop(EnumLuck.NEUTRAL, new ItemStack(Blocks.log, 64, 2), new ItemStack(Blocks.log, 64, 2));
        registry.registerDrop(EnumLuck.NEUTRAL, new ItemStack(Blocks.log, 64, 3), new ItemStack(Blocks.log, 64, 3));
        registry.registerDrop(EnumLuck.NEUTRAL, new ItemStack(Blocks.log2, 64, 0), new ItemStack(Blocks.log2, 64, 0));
        registry.registerDrop(EnumLuck.NEUTRAL, new ItemStack(Blocks.log2, 64, 1), new ItemStack(Blocks.log2, 64, 1));
        registry.register(EnumLuck.NEUTRAL,
                new EventNotFound(),
                eventDrop,
                randomEvent,
                new EventEnchantedItem(new ItemStack(Items.iron_axe), 3, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.iron_sword), 2, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.iron_helmet), 2, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.iron_chestplate), 2, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.iron_leggings), 2, 12, 30),
                new EventEnchantedItem(new ItemStack(Items.iron_boots), 2, 12, 30)
        );

        registry.registerDrop(EnumLuck.BAD, boats);
        registry.registerDrop(EnumLuck.BAD, boats);
        registry.register(EnumLuck.BAD,
                eventDrop,
                eventDamage,
                randomEvent,
                new EventBedrock(),
                new EventNotFound(),
                eventLightningStrike,
                new MultiEvent(eventDamage, eventLightningStrike)
        );
        ILuckyEvent eventSpawn = new EventSpawn(3, 24, EntityZombie.class, EntitySkeleton.class, EntityArrow.class, EntitySpider.class, EntityCreeper.class);
        registry.register(EnumLuck.VERY_BAD,
                eventSpawn,
                new MultiEvent(eventLightningStrike, eventSpawn)
        );
    }

    @Beta
    public static void registerDefaultBowDrops(ResourceLocation luckyblock) {
        if(true) throw new NotImplementedException("Bow Drops are not done yet!"); //FIXME implement bow drop registry!s
        LuckyBlockRegistry registry = LuckyBlockRegistry.getRegistryFor(luckyblock);
        registry.register(EnumLuck.BAD,
                new EventLightningStrike(),
                new EventBedrock()
        );

    }
}
