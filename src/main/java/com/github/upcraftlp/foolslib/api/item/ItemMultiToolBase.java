package com.github.upcraftlp.foolslib.api.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import java.util.Set;
import java.util.TreeSet;

public class ItemMultiToolBase extends ItemToolBase {

    public ItemMultiToolBase(String name, float attackDamageIn, ToolMaterial materialIn) {
        super(name, attackDamageIn, materialIn);
        this.setMaxDamage(((materialIn.getMaxUses() - 1) * 3) + 1);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        Set<String> toolClasses = new TreeSet<>();
        toolClasses.add("pickaxe");
        toolClasses.add("axe");
        toolClasses.add("shovel");
        return toolClasses;
    }

    @Override
    public float getDigSpeed(ItemStack stack, IBlockState state) {
        return efficiencyOnProperMaterial;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        IBlockState state = block.getDefaultState();
        String tool = block.getHarvestTool(state);
        return tool == null || (this.getToolClasses(stack).contains(tool) && block.getHarvestLevel(state) <= this.toolMaterial.getHarvestLevel());
    }

}
