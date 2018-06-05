package com.github.upcraftlp.foolslib.proxy;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.block.tile.TileEntityLuckyBlock;
import com.github.upcraftlp.foolslib.api.luck.LuckyHelper;
import com.github.upcraftlp.foolslib.api.recipe.LuckRecipe;
import com.github.upcraftlp.foolslib.api.util.ModHelper;
import com.github.upcraftlp.foolslib.api.util.UpdateChecker;
import com.github.upcraftlp.foolslib.command.CommandLoadStructure;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import com.github.upcraftlp.foolslib.init.FoolsBlocks;
import com.github.upcraftlp.foolslib.init.FoolsItems;
import com.github.upcraftlp.foolslib.util.AutoRegistry;
import com.github.upcraftlp.foolslib.util.StructureUnloader;
import com.github.upcraftlp.foolslib.world.gen.WorldGeneratorLuckyBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        try {
            ReflectionHelper.findMethod(ModHelper.class, null, new String[]{"setup"}, FMLPreInitializationEvent.class).invoke(null, event);
            AutoRegistry.registerAll(event);
        } catch (Exception e) {
            FoolsLib.getLogger().error("unable to initialize FoolsLib API", e);
        }
        FoolsConfig.init();
        UpdateChecker.registerMod(FoolsLib.MODID);
        GameRegistry.registerWorldGenerator(new WorldGeneratorLuckyBlocks(FoolsBlocks.LUCKY_BLOCK), 100000); //reminder: higher weight means the generator is run later
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityLuckyBlock.class, FoolsLib.LUCKY_BLOCK.toString());
        LuckyHelper.registerDefaultDrops(FoolsLib.LUCKY_BLOCK);
        LuckyHelper.registerDefaultDrops(FoolsLib.LUCKY_BOW);
        GameRegistry.addRecipe(new LuckRecipe(FoolsLib.LUCKY_BLOCK));
        LuckyHelper.registerDefaultRecipes(FoolsLib.LUCKY_BLOCK);
        GameRegistry.addShapedRecipe(new ItemStack(FoolsBlocks.LUCKY_BLOCK),
                "III",
                "IDI",
                "III",
                'I', Items.iron_ingot,
                'D', Blocks.dispenser);
        GameRegistry.addShapelessRecipe(new ItemStack(FoolsItems.LUCKY_BOW), new ItemStack(Items.bow), new ItemStack(FoolsBlocks.LUCKY_BLOCK, 1, OreDictionary.WILDCARD_VALUE));
        //TODO bow upgrade recipe!
    }

    public void postInit(FMLPostInitializationEvent event) {
        try {
            ReflectionHelper.findMethod(StructureUnloader.class, null, new String[]{"init"}).invoke(null);
        } catch (Exception e) {
            FoolsLib.getLogger().error("unable to finish initialization of FoolsLib API", e);
        }
    }

    public void serverStarting(FMLServerStartingEvent event) {
        AutoRegistry.notifyServerUpdates();
        event.registerServerCommand(new CommandLoadStructure());
    }

    public void serverStopping(FMLServerStoppingEvent event) {

    }

    public void onIMCMessage(FMLInterModComms.IMCEvent event) {

    }
}
