package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.client.ItemMeshDefinitionCustom;
import com.github.upcraftlp.foolslib.api.luck.EnumLuck;
import com.github.upcraftlp.foolslib.api.model.ICustomModelProvider;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBlockLucky extends ItemBlock implements ICustomModelProvider {

    public ItemBlockLucky(Block block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    private ModelResourceLocation itemModel;

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        int luck = stack.getMetadata();
        if(luck > 100) luck = 100 - luck;
        luck = MathHelper.clamp_int(luck, -100, 100);
        tooltip.add(I18n.format("desc.foolslib.luck", EnumLuck.getFormatting(luck).toString() + luck));
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return stack.getMetadata() != 0 ? 1 : 64;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel(String modid) {
        String modelName = itemRegistry.getNameForObject(this).toString();
        itemModel = new ModelResourceLocation(modelName, "inventory");
        ModelBakery.addVariantName(this, modelName);
        ModelLoader.setCustomMeshDefinition(this, ItemMeshDefinitionCustom.create(stack -> itemModel));
    }

}
