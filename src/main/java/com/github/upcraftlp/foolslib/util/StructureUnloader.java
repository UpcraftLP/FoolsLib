package com.github.upcraftlp.foolslib.util;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.world.structure.Structure;
import com.github.upcraftlp.foolslib.api.world.structure.StructureRegistry;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import com.github.upcraftlp.foolslib.proxy.CommonProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unused")
public final class StructureUnloader {

    private static final Timer timer = new Timer();

    /**
     * internal use ONLY, called via reflection in {@link CommonProxy#postInit(FMLPostInitializationEvent)}
     */
    private static void init() {
        timer.scheduleAtFixedRate(new StructureUnloadTask(), 10, 3*1000*10); //every 2 minutes
    }

    @SuppressWarnings("unchecked")
    private static final class StructureUnloadTask extends TimerTask {

        private static final List<Structure> STRUCTURES;
        static {
            Field f = ReflectionHelper.findField(StructureRegistry.class, "loadedStructures");
            List<Structure> tempStructureList;
            try {
                tempStructureList = (List<Structure>) f.get(null);
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if(tempStructureList == null) throw new RuntimeException("Error initializing Structure Unloader!");
            STRUCTURES = tempStructureList;
            f.setAccessible(false);
        }

        @Override
        public void run() {
            synchronized(StructureRegistry.class) {
                Iterator<Structure> iterator = STRUCTURES.iterator();
                while(iterator.hasNext()) {
                    Structure structure = iterator.next();
                    if(structure.canUnload()) {
                        structure.unloadStructure();
                        iterator.remove();
                        if(FoolsConfig.isDebugMode) FoolsLib.getLogger().info("unloaded structure {}", structure.getStructureLocation());
                    }
                }
            }
        }
    }

}
