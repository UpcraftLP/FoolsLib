package com.github.upcraftlp.foolslib.init;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.block.BlockLuckyBase;
import com.github.upcraftlp.foolslib.api.util.RegistryCreate;
import net.minecraft.block.Block;

@RegistryCreate(modid = FoolsLib.MODID, value = Block.class)
public class FoolsBlocks {

    public static final Block LUCKY_BLOCK = new BlockLuckyBase("lucky_block");
}
