package com.github.upcraftlp.foolslib.util;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.block.IHasItemBlock;
import com.github.upcraftlp.foolslib.api.model.ICustomModelProvider;
import com.github.upcraftlp.foolslib.api.util.EventBusSubscriber;
import com.github.upcraftlp.foolslib.api.util.ModHelper;
import com.github.upcraftlp.foolslib.api.util.RegistryCreate;
import com.github.upcraftlp.foolslib.api.util.UpdateChecker;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import com.google.common.collect.ObjectArrays;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AutoRegistry {

    private static final Method registerItem;
    private static final Method registerBlock;
    private static final GameData gameData;

    static {
        gameData = ReflectionHelper.getPrivateValue(GameData.class, null, "mainData");
        registerItem = ReflectionHelper.findMethod(GameData.class, gameData, new String[]{"registerItem"}, Item.class, String.class, int.class);
        registerBlock = ReflectionHelper.findMethod(GameData.class, gameData, new String[]{"registerBlock"}, Block.class, String.class, int.class);
    }

    public static void registerAll(FMLPreInitializationEvent event) {
        ASMDataTable dataTable = event.getAsmData();
        Set<ASMDataTable.ASMData> classesToLoad = dataTable.getAll(EventBusSubscriber.class.getCanonicalName());
        Side currentSide = event.getSide();
        classesToLoad.forEach(asmData -> {
            try {
                Class c = Class.forName(asmData.getClassName());
                Object instance = c.newInstance();
                EventBusSubscriber annotation = (EventBusSubscriber) c.getAnnotation(EventBusSubscriber.class);
                for(Side side : annotation.side()) {
                    if(side == currentSide) {
                        if(annotation.forge()) MinecraftForge.EVENT_BUS.register(instance);
                        if(annotation.terrainGen()) MinecraftForge.TERRAIN_GEN_BUS.register(instance);
                        if(annotation.oreGen()) MinecraftForge.ORE_GEN_BUS.register(instance);
                        if(annotation.fml()) FMLCommonHandler.instance().bus().register(instance);
                        if(FoolsConfig.isDebugMode) FoolsLib.getLogger().debug("subscribed class {} for file {}; Forge: {}, FMLCommonHandler: {}" + asmData.getClassName(), asmData.getCandidate().getModContainer().getName(), annotation.forge(), annotation.fml());
                    }
                }
            } catch (Exception e) {
                FoolsLib.getLogger().debug("unable to load class {} for Side {}", asmData.getClassName(), currentSide);
            }
        });
        Set<ASMDataTable.ASMData> data =  dataTable.getAll(RegistryCreate.class.getCanonicalName());
        data.forEach(asmData -> {
            try {
                String modid = (String) asmData.getAnnotationInfo().get("modid");
                ModHelper.setModid(modid);

                Class clazz = Class.forName(asmData.getClassName());
                Class type = ((RegistryCreate) clazz.getDeclaredAnnotation(RegistryCreate.class)).value();
                for(Field f : clazz.getDeclaredFields()) {
                    List<ItemStack> items = new ArrayList<>();
                    if(type.isAssignableFrom(f.getType())) {
                        if(type == Item.class) {
                            Item item = (Item) f.get(null);

                            registerItem.invoke(gameData, item, modid + ":" + f.getName().toLowerCase(Locale.ROOT), -1);
                            if(currentSide == Side.CLIENT) item.getSubItems(item, item.getCreativeTab(), items);
                        }
                        else if(type == Block.class) {
                            Block block = (Block) f.get(null);
                            Class<? extends ItemBlock> itemBlockClass = ItemBlock.class;
                            Object[] cArgs = new Object[0];
                            if(block instanceof IHasItemBlock) {
                                IHasItemBlock block1 = (IHasItemBlock) block;
                                itemBlockClass = block1.getItemBlockClass();
                                cArgs = block1.getAdditionalItemBlockConstructorArguments();
                            }
                            String name = modid + ":" + f.getName().toLowerCase(Locale.ROOT);
                            registerBlock.invoke(gameData, block, name, -1);

                            if(itemBlockClass != null) { //register itemblock
                                Class<?>[] ctorArgClasses = new Class<?>[cArgs.length + 1];
                                ctorArgClasses[0] = Block.class;
                                for (int idx = 1; idx < ctorArgClasses.length; idx++)
                                {
                                    ctorArgClasses[idx] = cArgs[idx-1].getClass();
                                }
                                Constructor<? extends ItemBlock> itemCtor = itemBlockClass.getConstructor(ctorArgClasses);
                                ItemBlock i = itemCtor.newInstance(ObjectArrays.concat(block, cArgs));
                                registerItem.invoke(gameData, i, name, -1);
                                GameData.getBlockItemMap().put(block, i);
                            }
                            if(currentSide == Side.CLIENT) block.getSubBlocks(Item.getItemFromBlock(block), block.getCreativeTabToDisplayOn(), items);
                        }
                        for (ItemStack stack : items) {
                            Item item = stack.getItem();
                            if(item instanceof ItemBlock && ((ItemBlock) item).block instanceof ICustomModelProvider) {
                                ((ICustomModelProvider) ((ItemBlock) item).block).initModel(modid);
                            }
                            else if(item instanceof ICustomModelProvider) {
                                ((ICustomModelProvider) item).initModel(modid);
                            }
                            else ModelLoader.setCustomModelResourceLocation(item, stack.getMetadata(), new ModelResourceLocation(Item.itemRegistry.getNameForObject(item).toString(), "inventory"));
                        }
                    }
                }
            } catch (Exception e) {
                FoolsLib.getLogger().error("exception registering stuff", e);
            }
        });
        ModHelper.setModid(null);
    }

    public static void notifyServerUpdates() {
        if(!FoolsConfig.enableUpdateChecker) return;
        for (String modid : UpdateChecker.MODS_TO_CHECK) {
            if (UpdateChecker.hasUpdate(modid)) {
                ForgeVersion.CheckResult result = UpdateChecker.getResult(modid);
                String modVersion = FMLCommonHandler.instance().findContainerFor(modid).getDisplayVersion();
                FoolsLib.getLogger().warn("There's an update available for {}, version {} (your version is {}), download it here: {}", modid, result.target, modVersion, result.url);
            }
        }
    }
}
