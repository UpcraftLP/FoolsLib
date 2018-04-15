package com.github.upcraftlp.foolslib.api.world;

import com.google.common.annotations.Beta;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

@Beta
public class WorldGenerationHelper {

    public static final int MAX_BUILD_HEIGHT = 255;
    public static final int MIN_BUILD_HEIGHT = 0;

    public static BlockPos getGroundFromAbove(World world, BlockPos pos) {
        return getGroundFromAbove(world, pos.getX(), pos.getZ());
    }

    public static BlockPos getGroundFromAbove(World world, int x, int z) {
        BlockPos pos = new BlockPos(x, MAX_BUILD_HEIGHT, z);
        while (pos.getY() >= MIN_BUILD_HEIGHT) {
            if(world.isSideSolid(pos, EnumFacing.UP)) {
                return pos;
            }
            pos = pos.down();
        }
        return pos;
    }

    public static BlockPos getGroundFromBelow(World world, BlockPos pos) {
        return getGroundFromBelow(world, pos.getX(), pos.getZ());
    }

    public static BlockPos getGroundFromBelow(World world, int x, int z) {
        BlockPos pos = new BlockPos(x, MIN_BUILD_HEIGHT, z);
        while (pos.getY() <= MAX_BUILD_HEIGHT) {
            if(world.isSideSolid(pos, EnumFacing.UP)) {
                return pos;
            }
            pos = pos.up();
        }
        return pos;
    }
}
