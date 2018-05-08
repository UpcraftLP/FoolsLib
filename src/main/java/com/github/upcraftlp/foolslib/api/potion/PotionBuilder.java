package com.github.upcraftlp.foolslib.api.potion;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class PotionBuilder {

    private Potion potion;
    private boolean levelTwo, extended, splash;

    public PotionBuilder(Potion potion) {
        this.potion = potion;
    }

    public PotionBuilder setLevelTwo() {
        this.levelTwo = true;
        return this;
    }

    public PotionBuilder setExtended() {
        this.extended = true;
        return this;
    }

    public PotionBuilder setSplash() {
        this.splash = true;
        return this;
    }

    public ItemStack build() {
        int meta = 0;
        if(this.potion != null) {
            meta = potion.getId();
            if(this.splash) meta |= 16384; //bit 14
            else meta |= 8192;

            if(this.levelTwo) meta |= 32; //bit 5
            if(this.extended) meta |= 64; // bit 6

        }
        return new ItemStack(Items.potionitem, 1, meta);
    }

}
