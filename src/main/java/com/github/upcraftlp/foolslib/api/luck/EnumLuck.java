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

    //FIXME unify into one function
    //e^(-r^2), now what with the L?
    /*public static EnumLuck getRandomValue(int luck) {
        double r = RANDOM.nextDouble();
        double l = luck / 100.0D;
        Math.pow(Math.E, -Math.pow(r, 2));
        //return values()[(int) MathHelper.clamMath.round(Math.pow(r, l) * 4)];

    }*/
    public static EnumLuck getRandomValue(int luck) {
        double result = RANDOM.nextDouble();
        if(luck < -70) {                                                            //luck: -100 to -69
            if(result < 0.01D) return VERY_GOOD;                                    //1%
            if(result < 0.06D) return GOOD;                                         //5%
            if(result < 0.16D) return NEUTRAL;                                      //10%
            if(result < 0.51D) return BAD;                                          //35%
            return VERY_BAD;                                                        //49%
        }
        else if(luck < -20) {                                                       //luck: -70 to -20
            if(result < 0.05D) return VERY_GOOD;                                    //5%
            if(result < 0.15D) return GOOD;                                         //10%
            if(result < 0.30D) return VERY_BAD;                                     //15%
            if(result < 0.45D) return NEUTRAL;                                      //20%
            return BAD;                                                             //50%
        }
        else if(luck <= 20) {                                                       //luck: -20 to +20
            if(result < 0.10D) return VERY_BAD;                                     //10%
            if(result < 0.20D) return VERY_GOOD;                                    //10%
            if(result < 0.40D) return BAD;                                          //20%
            if(result < 0.60D) return GOOD;                                         //20%
            return NEUTRAL;                                                         //40%
        }
        else if(luck <= 70) {                                                       //luck +21 to +70
            if(result < 0.05D) return VERY_BAD;                                     //5%
            if(result < 0.15D) return BAD;                                          //10%
            if(result < 0.30D) return VERY_GOOD;                                    //15%
            if(result < 0.45D) return NEUTRAL;                                      //20%
            return GOOD;                                                            //50%
        }
        else {                                                                      //luck: +71 to +100
            if(result < 0.01D) return VERY_BAD;                                     //1%
            if(result < 0.06D) return BAD;                                          //5%
            if(result < 0.16D) return NEUTRAL;                                      //10%
            if(result < 0.51D) return GOOD;                                         //35%
            return VERY_GOOD;                                                       //49%
        }
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
