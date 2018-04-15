package com.github.upcraftlp.foolslib.proxy;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.block.tile.TileEntityLuckyBlock;
import com.github.upcraftlp.foolslib.api.luck.LuckyHelper;
import com.github.upcraftlp.foolslib.api.util.ModHelper;
import com.github.upcraftlp.foolslib.api.util.UpdateChecker;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import com.github.upcraftlp.foolslib.init.FoolsBlocks;
import com.github.upcraftlp.foolslib.net.NetworkHandler;
import com.github.upcraftlp.foolslib.util.AutoRegistry;
import com.github.upcraftlp.foolslib.world.gen.WorldGeneratorLuckyBlocks;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        FoolsConfig.init();
        UpdateChecker.registerMod(FoolsLib.MODID);
        try {
            ModHelper.setModid(FoolsLib.MODID);
            ReflectionHelper.findMethod(ModHelper.class, null, new String[]{"setup"}, FMLPreInitializationEvent.class).invoke(null, event);
            AutoRegistry.registerAll(event);
        } catch (Exception e) {
            FoolsLib.getLogger().error("unable to initialize Fools API", e);
        }
        NetworkHandler.init();
        GameRegistry.registerWorldGenerator(new WorldGeneratorLuckyBlocks(FoolsBlocks.LUCKY_BLOCK), 100000); //reminder: higher weight means the generator is run later
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityLuckyBlock.class, FoolsLib.LUCKY_BLOCK.toString());

        LuckyHelper.registerDefaultDrops(FoolsLib.LUCKY_BLOCK);
        LuckyHelper.registerDefaultDrops(FoolsLib.LUCKY_BOW);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {
        AutoRegistry.notifyServerUpdates();
    }

    public void serverStopping(FMLServerStoppingEvent event) {

    }

    public void onIMCMessage(FMLInterModComms.IMCEvent event) {

    }
}
