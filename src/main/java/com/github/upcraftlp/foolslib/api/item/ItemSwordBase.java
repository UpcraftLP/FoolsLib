package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.item.ItemSword;

public class ItemSwordBase extends ItemSword {

    public ItemSwordBase(String name, ToolMaterial material) {
        super(material);
        this.setUnlocalizedName(ModHelper.getActiveModid() + "." + name);
        this.setFull3D();
    }

}
