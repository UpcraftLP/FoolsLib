package com.github.upcraftlp.foolslib.api.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class ItemEnchantHelper {

    public static void enchant(Random random, ItemStack stack, int count, int minEnchantmentLevel, int maxEnchantmentLevel) {
        for(int i = 0; i < count; i++) EnchantmentHelper.addRandomEnchantment(random, stack, MathHelper.clamp_int(minEnchantmentLevel + random.nextInt(MathHelper.clamp_int(maxEnchantmentLevel - minEnchantmentLevel + 1, 1, 30)), 0, 30));
    }
}
