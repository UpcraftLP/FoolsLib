package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBase extends Item {

    private int subItemCount = 0;

    public ItemBase(String name) {
        super();
        this.setUnlocalizedName(ModHelper.getActiveModID() + "." + name);
    }

    public void setSubItemCount(int count) {
        this.subItemCount = count;
        this.setHasSubtypes(count > 0);
    }

    public int getSubItemCount() {
        return this.subItemCount;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List items) {
        for(CreativeTabs tab1 : this.getCreativeTabs()) {
            if(tab1 == tab) {
                if (this.getSubItemCount() > 0) {
                    for ( int i = 0; i < this.getSubItemCount(); i++ )
                        items.add(new ItemStack(this, 1, i));
                }
                else {
                    super.getSubItems(item, tab, items);
                }
                return;
            }
        }

    }

}
