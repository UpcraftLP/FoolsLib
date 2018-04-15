package com.github.upcraftlp.foolslib.api.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//do NOT make the class client only, that will result in loading errors!
public interface ICustomModelProvider {

    @SideOnly(Side.CLIENT)
    void initModel(String modid);
}
