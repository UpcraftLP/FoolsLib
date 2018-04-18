package com.github.upcraftlp.foolslib.world.gen;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.block.tile.TileEntityLuckyBlock;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGeneratorLuckyBlocks implements IWorldGenerator {

    private final Block block;

    public WorldGeneratorLuckyBlocks(Block luckyblock) {
        this.block = luckyblock;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        int x = chunkX * 16 + 8 + random.nextInt(16);
        int z = chunkZ * 16 + 8 + random.nextInt(16);
        //TODO different structures
        if(random.nextInt(100) == 0) { //TODO check for neighbor chunks to contain a structure already
            BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 255, z));
            if(world.getBiomeGenForCoords(pos) != BiomeGenBase.plains) return;
            IBlockState state = this.block.getDefaultState();
            world.setBlockState(pos, state, 2);
            this.block.onBlockAdded(world, pos, state);
            TileEntity te = world.getTileEntity(pos);
            if(te instanceof TileEntityLuckyBlock) {
                int luck = random.nextInt(101);
                if(random.nextInt(2) == 0) luck = -luck; //also generate bad blocks
                ((TileEntityLuckyBlock) te).setLuck(luck);
            }
            if(FoolsConfig.isDebugMode) FoolsLib.getLogger().info("generated Lucky BLock at [{} ,{} ,{}]", pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
