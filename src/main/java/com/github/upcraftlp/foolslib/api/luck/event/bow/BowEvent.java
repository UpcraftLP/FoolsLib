package com.github.upcraftlp.foolslib.api.luck.event.bow;

import com.github.upcraftlp.foolslib.api.world.WorldScheduledTask;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BowEvent {

    public static final IBlockState[] STONE = {
            Blocks.stone.getDefaultState(),
            Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
            Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE),
            Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)
    };

    public static void drawLine(Random random, World world, List<BlockPos> positionMarkers, IBlockState... blocks) {
        final IBlockState[] copy = blocks.length > 0 ? blocks : STONE;

        //TODO generate spline
        for(int i = 0; i < positionMarkers.size(); i++) {
            final int index = i;
            new WorldScheduledTask(() -> world.setBlockState(positionMarkers.get(index), copy[random.nextInt(copy.length)], 2)).schedule(i);
        }
    }

}
