package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.entity.EntityLuckyArrow;
import com.github.upcraftlp.foolslib.api.luck.EnumLuck;
import com.github.upcraftlp.foolslib.api.luck.event.LuckyEventProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemLuckyBowBase extends ItemBowBase implements LuckyEventProvider {

    protected ResourceLocation luckyBlock;

    public ItemLuckyBowBase(String name, ResourceLocation luckyBlock) {
        super(name);
        this.setLuckyBlock(luckyBlock);
    }

    @Override
    public void setLuckyBlock(ResourceLocation luckyBlock) {
        this.luckyBlock = luckyBlock;
    }

    @Override
    public ResourceLocation getLuckyBlock() {
        return null;
    }

    @Override
    public void setLuck(int luck) {
        //NO-OP
    }

    @Override
    public int getLuck() {
        return 0;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
        if(worldIn.isRemote) return;
        int charge = this.getMaxItemUseDuration(stack) - timeLeft;
        net.minecraftforge.event.entity.player.ArrowLooseEvent event = new net.minecraftforge.event.entity.player.ArrowLooseEvent(playerIn, stack, charge);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
        charge = event.charge;

        boolean flag = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || playerIn.inventory.hasItem(Items.arrow))
        {
            float strength = (float)charge / 20.0F;
            strength = (strength * strength + strength * 2.0F) / 3.0F;
            if ((double)strength < 0.1D) return;
            if (strength > 1.0F)
            {
                strength = 1.0F;
            }
            EntityLuckyArrow arrow = new EntityLuckyArrow(worldIn, playerIn, strength * 2.0F);
            arrow.setLuckyBlock(this.luckyBlock);
            arrow.setIsCritical(strength == 1.0F);
            int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            if (power > 0) arrow.setDamage(arrow.getDamage() + (double)power * 0.5D + 0.5D);
            int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            if (punch > 0) arrow.setKnockbackStrength(punch);
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) arrow.setFire(100);
            stack.damageItem(1, playerIn);
            worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + strength * 0.5F);
            if (!flag) playerIn.inventory.consumeInventoryItem(Items.arrow);
            arrow.canBePickedUp = 2;
            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            worldIn.spawnEntityInWorld(arrow);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        int luck = MathHelper.clamp_int(nbt.getInteger("foolslib_luck"), -100, 100);
        tooltip.add(I18n.format("desc.foolslib.luck", EnumLuck.getFormatting(luck).toString() + luck));
    }
}
