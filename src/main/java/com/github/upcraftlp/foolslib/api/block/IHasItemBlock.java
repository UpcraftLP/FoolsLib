package com.github.upcraftlp.foolslib.api.block;

import net.minecraft.item.ItemBlock;

public interface IHasItemBlock {

    /**
     * the custom itemblock class, null for no itemblock
     */
    default Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlock.class;
    }

    default Object[] getAdditionalItemBlockConstructorArguments() {
        return new Object[0];
    }
}
