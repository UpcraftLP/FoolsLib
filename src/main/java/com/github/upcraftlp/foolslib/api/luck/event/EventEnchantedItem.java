package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.api.enchantment.ItemEnchantHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EventEnchantedItem extends LuckyEvent {

    private final ItemStack stack;
    private final int enchantmentCount;
    private final int minEnchantmentLevel, maxEnchantmentLevel;

    public EventEnchantedItem(ItemStack stack, int count) {
        this(stack, count, 12, 30);
    }

    public EventEnchantedItem(ItemStack stack, int count, int minEnchantmentLevel, int maxEnchantmentLevel) {
        this.stack = stack;
        enchantmentCount = count;
        this.minEnchantmentLevel = minEnchantmentLevel;
        this.maxEnchantmentLevel = maxEnchantmentLevel;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        world.createExplosion(player, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 3.5F, false);
        ItemStack toSpawn = stack.copy();
        ItemEnchantHelper.enchant(RANDOM, toSpawn, this.enchantmentCount, this.minEnchantmentLevel, this.maxEnchantmentLevel);
        EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, toSpawn);
        world.spawnEntityInWorld(item);
    }
}
