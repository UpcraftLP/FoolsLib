package com.github.upcraftlp.foolslib.api.block;

import com.github.upcraftlp.foolslib.api.block.tile.TileEntityLuckyBlock;
import com.github.upcraftlp.foolslib.api.item.ItemBlockLucky;
import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLuckyBase extends BlockBase {

    private final ResourceLocation blockName;

    public BlockLuckyBase(String name) {
        this(new ResourceLocation(ModHelper.getActiveModID(), name));
    }

    public BlockLuckyBase(ResourceLocation name) {
        super(name.getResourcePath(), Material.clay);
        this.blockName = name;
        this.setResistance(6000000.0F);
        this.setHardness(0.2F);
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockLucky.class;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if(worldIn.isRemote) return;
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileEntityLuckyBlock) {
            TileEntityLuckyBlock teLucky = (TileEntityLuckyBlock) te;
            if(worldIn.isBlockPowered(pos)) {
                teLucky.setLuckyBlock(this.blockName);
                teLucky.activate(null);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if(!worldIn.isRemote && worldIn.isBlockPowered(pos)) {
            TileEntity te = worldIn.getTileEntity(pos);
            if(te instanceof TileEntityLuckyBlock) {
                TileEntityLuckyBlock teLucky = (TileEntityLuckyBlock) te;
                if(worldIn.isBlockPowered(pos)) {
                    teLucky.setLuckyBlock(this.blockName);
                    teLucky.activate(null);
                }
            }
        }
    }

    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if(!world.isRemote && !player.capabilities.isCreativeMode) {
            TileEntity te = world.getTileEntity(pos);
            if(te instanceof TileEntityLuckyBlock) {
                ((TileEntityLuckyBlock) te).activate((EntityPlayerMP) player);
            }
        }
        return super.removedByPlayer(world, pos, player, willHarvest);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    public ResourceLocation getBlockName() {
        return blockName;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityLuckyBlock(this.blockName);
    }

    @Override
    public int getDamageValue(World worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileEntityLuckyBlock) return ((TileEntityLuckyBlock) te).getLuck();
        else return super.getDamageValue(worldIn, pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if(stack.getItem() == Item.getItemFromBlock(this)) {
            TileEntity te = worldIn.getTileEntity(pos);
            if(te instanceof TileEntityLuckyBlock) {
                int luck = stack.getMetadata();
                if(luck > 100) luck = 100 - luck;
                ((TileEntityLuckyBlock) te).setLuck(luck);
            }
        }
    }

}
