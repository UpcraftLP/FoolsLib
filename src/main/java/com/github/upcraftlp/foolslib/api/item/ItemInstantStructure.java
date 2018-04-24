package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.world.structure.InstantStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public  abstract class ItemInstantStructure<T extends InstantStructure> extends ItemBase {

    public static final double REACH_DISTANCE = 5.0D;
    protected int placeDelay = 4;
    protected final T structure;
    protected final int maxDelay;

    public ItemInstantStructure(String name, ResourceLocation blockName, int maxDelay) {
        this(name, blockName, maxDelay, 0);
    }

    public ItemInstantStructure(String name, ResourceLocation blockName, int maxDelay,  int meta) {
        super(name);
        this.maxDelay = maxDelay;
        this.structure = getStructure(blockName, meta);
    }

    protected abstract T getStructure(ResourceLocation blockName, int meta);

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if(!worldIn.isRemote) {
            Vec3 eyePos = new Vec3(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
            Vec3 look = playerIn.getLookVec();
            Vec3 target = eyePos.addVector(look.xCoord * REACH_DISTANCE, look.yCoord * REACH_DISTANCE, look.zCoord * REACH_DISTANCE);
            MovingObjectPosition result = worldIn.rayTraceBlocks(eyePos, target, true, true, true);
            BlockPos pos;
            if(result != null && result.getBlockPos() != null) pos = result.getBlockPos().offset(result.sideHit);
            else pos = new BlockPos(target);
            if(this.maxDelay == 0) this.structure.placeBlocksInWorld(playerIn, worldIn, pos, true);
            else this.structure.placeBlocksInWorldDelay(playerIn, worldIn, pos, this.placeDelay, this.maxDelay, true);
        }
        return playerIn.capabilities.isCreativeMode ? itemStackIn : --itemStackIn.stackSize > 0 ? itemStackIn : null;
    }

    public List<BlockPos> getBlocks(EntityPlayer player, World world, BlockPos hitPos, boolean offset) {
        return this.structure.getPositions(player, world, hitPos, offset);
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getStructureRenderBoundingBox(EntityPlayer placer, World world, BlockPos pos) {
        return this.structure.getRenderBoundingBox(placer, world, pos);
    }

    public IBlockState getBlockState(Entity placer) {
        return this.structure.getBlock(placer);
    }
}
