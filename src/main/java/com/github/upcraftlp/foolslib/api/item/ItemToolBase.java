package com.github.upcraftlp.foolslib.api.item;

import com.github.upcraftlp.foolslib.api.util.ModHelper;
import net.minecraft.item.ItemTool;

import java.util.Collections;

public class ItemToolBase extends ItemTool {

    public ItemToolBase(String name, float attackDamageIn, ToolMaterial materialIn) {
        super(attackDamageIn, materialIn, Collections.emptySet());
        this.setFull3D();
        this.setUnlocalizedName(ModHelper.getActiveModID() + "." + name);
    }

}
