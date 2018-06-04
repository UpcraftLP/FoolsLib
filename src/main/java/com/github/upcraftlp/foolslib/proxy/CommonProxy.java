package com.github.upcraftlp.foolslib.proxy;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.block.tile.TileEntityLuckyBlock;
import com.github.upcraftlp.foolslib.api.luck.LuckyHelper;
import com.github.upcraftlp.foolslib.api.net.NetworkHandler;
import com.github.upcraftlp.foolslib.api.recipe.LuckRecipe;
import com.github.upcraftlp.foolslib.api.util.ModHelper;
import com.github.upcraftlp.foolslib.api.util.UpdateChecker;
import com.github.upcraftlp.foolslib.command.CommandLoadStructure;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import com.github.upcraftlp.foolslib.init.FoolsBlocks;
import com.github.upcraftlp.foolslib.util.AutoRegistry;
import com.github.upcraftlp.foolslib.util.StructureUnloader;
import com.github.upcraftlp.foolslib.world.gen.WorldGeneratorLuckyBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
        NetworkHandler.init();
        GameRegistry.registerWorldGenerator(new WorldGeneratorLuckyBlocks(FoolsBlocks.LUCKY_BLOCK), 100000); //reminder: higher weight means the generator is run later
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityLuckyBlock.class, FoolsLib.LUCKY_BLOCK.toString());
        LuckyHelper.registerDefaultDrops(FoolsLib.LUCKY_BLOCK);
        LuckyHelper.registerDefaultDrops(FoolsLib.LUCKY_BOW);
        GameRegistry.addRecipe(new LuckRecipe(FoolsLib.LUCKY_BLOCK));
        LuckyHelper.registerDefaultRecipes(FoolsLib.LUCKY_BLOCK);
        //TODO bow recipe!
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
