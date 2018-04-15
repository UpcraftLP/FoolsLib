package com.github.upcraftlp.foolslib.init;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.block.BlockLuckyBase;
import com.github.upcraftlp.foolslib.api.item.ItemLuckyBowBase;
import com.github.upcraftlp.foolslib.api.util.RegistryCreate;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@RegistryCreate(modid = FoolsLib.MODID, value = Item.class)
public class FoolsItems {

    public static final Item LUCKY_BOW = new ItemLuckyBowBase("lucky_bow", FoolsLib.LUCKY_BOW);
}
