package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.model.ICustomModelProvider;
import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBowBase extends ItemBow implements ICustomModelProvider {

    @SideOnly(Side.CLIENT)
    private ModelResourceLocation[] pullingStates;

    private String name;

    /**
     * how many ticks it takes to reach the final pulling state
     */
    protected int maxPullTime = 18;

    public ItemBowBase(String name) {
        String modid = ModHelper.getActiveModID();
        this.setUnlocalizedName(modid + "." + name);
        this.name = name;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
        if(player.getItemInUse() != null) {
            float fraction = MathHelper.clamp_float((this.getMaxItemUseDuration(stack) - useRemaining) / ((float) maxPullTime), 0.0F, 1.0F);
            return this.pullingStates[(int) (fraction * (this.pullingStates.length - 1))];
        }
        else return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel(String modid) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(modid, this.name + "/" + this.name), "inventory"));

        this.pullingStates = new ModelResourceLocation[3];
        String[] names = new String[this.pullingStates.length + 1];
        names[0] = modid + ":" + this.name + "/" + this.name;
        for(int i = 0; i < this.pullingStates.length; i++) {
            String variantName = modid + ":" + this.name + "/" + this.name + "_pulling_" + i;
            names[i + 1] = variantName;
            this.pullingStates[i] = new ModelResourceLocation(variantName, "inventory");
        }
        ModelBakery.addVariantName(this, names);
    }
}
