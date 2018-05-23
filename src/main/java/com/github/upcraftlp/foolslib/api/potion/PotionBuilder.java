package com.github.upcraftlp.foolslib.api.potion;

import com.github.upcraftlp.foolslib.api.util.CollectionUtils;
import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;

import java.util.List;

public class PotionBuilder {

    public static final List<String> POTION_NAMES = Lists.newArrayList(
            "of the Ender",
            "of baasti",
            "of the great Benny",
            "of the Bomb",
            "of the Bush",
            "of the almighty Emperor",
            "of Death",
            "of Light",
            "of Darkness",
            "002",
            "016",
            "of Nothing",
            "of the Sherwood Forest"
    );

    private NBTTagList potionList;
    private boolean splash;

    public PotionBuilder() {
        this.clear();
    }

    public PotionBuilder clear() {
        this.splash = false;
        this.potionList = new NBTTagList();
        return this;
    }

    public PotionBuilder addEffect(PotionEffect effect) {
        potionList.appendTag(effect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
        return this;
    }

    public PotionBuilder setSplashPotion() {
        this.splash = true;
        return this;
    }

    public ItemStack build() {
        ItemStack stack = new ItemStack(Items.potionitem, 1, this.splash ? 16384 : 0);
        stack.setTagInfo("CustomPotionEffects", potionList);
        stack.setStackDisplayName("Potion " + CollectionUtils.getRandomElementFromList(POTION_NAMES));
        return stack;
    }

    public ItemStack buildAndClear() {
        ItemStack ret = this.build();
        this.clear();
        return ret;
    }

}
