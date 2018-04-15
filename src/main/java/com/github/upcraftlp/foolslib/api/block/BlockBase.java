package com.github.upcraftlp.foolslib.api.block;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block implements IHasItemBlock {

    public BlockBase(String name, Material material) {
        super(material);
        this.setUnlocalizedName(ModHelper.getActiveModid() + "." + name);
    }

    public BlockBase(String name) {
        this(name, Material.rock);
        this.setStepSound(soundTypeStone);
    }

}
