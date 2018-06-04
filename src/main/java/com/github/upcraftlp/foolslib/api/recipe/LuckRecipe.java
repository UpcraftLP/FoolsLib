package com.github.upcraftlp.foolslib.api.recipe;

import com.github.upcraftlp.foolslib.api.luck.LuckyBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class LuckRecipe implements IRecipe {

    private final Block luckyblock;
    private final ResourceLocation blockName;

    public LuckRecipe(ResourceLocation blockName) {
        this.blockName = blockName;
        this.luckyblock = (Block) Block.blockRegistry.getObject(blockName);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return findLuckyBlock(inv) != -1;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        int index = findLuckyBlock(inv);
        ItemStack stack = inv.getStackInSlot(index).copy();
        stack.stackSize = 1;
        int luck = stack.getMetadata();
        if(luck > 100) luck = 100 - luck;
        LuckyBlockRegistry registry = LuckyBlockRegistry.getRegistryFor(this.blockName);
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            if(i == index) continue;
            luck = MathHelper.clamp_int(luck + registry.getLuckFor(inv.getStackInSlot(i)), -100, 100);
        }
        if(luck < 0) luck = 100 - luck;
        stack.setItemDamage(luck);
        return stack.copy();
    }

    @Override
    public int getRecipeSize() {
        return 1;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(this.luckyblock);
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = ForgeHooks.getContainerItem(inv.getStackInSlot(i));
        }
        return ret;
    }

    /**
     * @return the slot that the lucky block is in, or -1 if none or more than 1 lucky block was found.
     */
    private int findLuckyBlock(InventoryCrafting inv) {
        int ret = -1;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null && Block.getBlockFromItem(stack.getItem()) == this.luckyblock) {
                    if(ret == -1) ret = i;
                    else return -1; //we found more than one block
            }
        }
        return ret;
    }
}
