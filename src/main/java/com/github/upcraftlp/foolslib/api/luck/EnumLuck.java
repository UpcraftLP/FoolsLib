package com.github.upcraftlp.foolslib.api.luck;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public enum EnumLuck {
    VERY_GOOD,
    GOOD,
    NEUTRAL,
    BAD,
    VERY_BAD;

    private static final Random RANDOM = new Random();

    public static EnumLuck getRandomValue(int luck) {
        return values()[RANDOM.nextInt(values().length)];
        //FIXME implement distribution rules.
        //gaussian function?
    }

    public static EnumLuck trueValue(int luck) {
        if(luck < -70) return VERY_BAD;
        if(luck < -20) return BAD;
        if(luck <= 20) return NEUTRAL;
        if(luck <= 70) return GOOD;
        return VERY_GOOD;
    }

    @SideOnly(Side.CLIENT)
    public static EnumChatFormatting getFormatting(EnumLuck luck) {
        switch (luck) {
            case VERY_GOOD: return EnumChatFormatting.AQUA;
            case GOOD:      return EnumChatFormatting.GREEN;
   default: case NEUTRAL:   return EnumChatFormatting.YELLOW;
            case BAD:       return EnumChatFormatting.LIGHT_PURPLE;
            case VERY_BAD:  return EnumChatFormatting.RED;
        }
    }

    @SideOnly(Side.CLIENT)
    public static EnumChatFormatting getFormatting(int luck) {
        return getFormatting(trueValue(luck));
    }
}
