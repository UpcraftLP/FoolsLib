package com.github.upcraftlp.foolslib.api.block;

import com.github.upcraftlp.foolslib.api.item.ItemBlockSilverfish;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSilverfishBase extends BlockBase {

    protected net.minecraft.block.Block drop;

    public BlockSilverfishBase(net.minecraft.block.Block drop) {
        super(drop.getUnlocalizedName().substring(5) + "_silverfish", Material.clay);
        this.setUnlocalizedName(drop.getUnlocalizedName().substring(5));
        this.setStepSound(soundTypeStone);
        this.setHardness(2.0f);
        this.setResistance(30.0f);
        this.setDrop(drop);
    }

    public void setDrop(net.minecraft.block.Block drop) {
        this.drop = drop;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockSilverfish.class;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if ( !worldIn.isRemote && worldIn.getGameRules().getGameRuleBooleanValue("doTileDrops") ) {
            if ( chance > 0.0f ) {
                EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(this.drop, 1));
                worldIn.spawnEntityInWorld(item);
            }
            if ( worldIn.getDifficulty() != EnumDifficulty.PEACEFUL ) {
                EntitySilverfish entitysilverfish = new EntitySilverfish(worldIn);
                entitysilverfish.setLocationAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
                worldIn.spawnEntityInWorld(entitysilverfish);
                entitysilverfish.spawnExplosionParticle();
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this.drop);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if ( worldIn.isRemote || player.capabilities.isCreativeMode )
            return;
        player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 160));
    }
}
