package com.github.upcraftlp.foolslib.world.gen;

import com.github.upcraftlp.foolslib.api.world.structure.StructureRegistry;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGeneratorStructure implements IWorldGenerator {

    private final ResourceLocation structure;

    public WorldGeneratorStructure(String structure) {
        this(new ResourceLocation(structure));
    }

    public WorldGeneratorStructure(ResourceLocation structure) {
        this.structure = structure;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        int x = chunkX * 16 + 8 + random.nextInt(16);
        int z = chunkZ * 16 + 8 + random.nextInt(16);
        if(random.nextInt(100) == 0) {
            BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x + random.nextInt(16), 255, z + random.nextInt(16)));
            if(StructureRegistry.structureExists(this.structure)) {
                StructureRegistry.getStructure(this.structure).placeBlocksInWorld(world, pos, true);
            }
        }
    }
}
