package com.github.upcraftlp.foolslib.world.structure;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.util.WorldScheduledTask;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class InstantStructure {

    protected static final Random RANDOM = new Random();
    private ResourceLocation block;
    private int meta;

    public InstantStructure(ResourceLocation block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox(EntityPlayer placer, World world, BlockPos pos) {
        List<BlockPos> posList = getPositions(placer, world, pos, false);
        if(posList.isEmpty()) return null;
        Collections.sort(posList);
        return new AxisAlignedBB(posList.get(0), posList.get(posList.size() - 1).add(1, 1, 1));
    }

    public List<BlockPos> getPositions(Entity placer, World world, BlockPos start) {
        return getPositions(placer, world, start, true);
    }

    public abstract List<BlockPos> getPositions(Entity placer, World world, BlockPos start, boolean offset);

    public void placeBlocksInWorldDelay(Entity placer, World world, BlockPos start, int delayTicks, int maxDelay, boolean preserveBlocks) {
        IBlockState state = this.getBlock(placer);
        for(BlockPos pos : this.getPositions(placer, world, start)) {
            if(preserveBlocks && !world.getBlockState(pos).getBlock().isReplaceable(world, pos)) continue;
            new WorldScheduledTask(() -> world.setBlockState(pos, state)).schedule(delayTicks * RANDOM.nextInt(maxDelay + 1));
        }
    }

    public void placeBlocksInWorld(Entity placer, World world, BlockPos start, boolean preserveBlocks) {
        IBlockState state = this.getBlock(placer);
        for(BlockPos pos : this.getPositions(placer, world, start)) {
            if(preserveBlocks && !world.getBlockState(pos).getBlock().isReplaceable(world, pos)) continue;
            else world.setBlockState(pos, state);
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public IBlockState getBlock(Entity placer) {
        Block block = GameData.getBlockRegistry().getObject(this.block);
        if(block == null) {
            FoolsLib.getLogger().error("unable to find block for structure! {}", this.block);
            return null;
        }
        IBlockState state = block.getStateFromMeta(meta);
        for(IProperty property : (Set<IProperty>) state.getProperties().keySet()) {
            if(property.getName().equals("facing")) {
                return state.withProperty(property, placer.getHorizontalFacing());
            }
        }
        return state; //TODO better rotations

    }
}
