package com.github.upcraftlp.foolslib.api.luck;

import com.github.upcraftlp.foolslib.api.luck.event.EventDrop;
import com.github.upcraftlp.foolslib.api.luck.event.EventNotFound;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LuckyBlockRegistry {

    /** static part **/

    private static final Map<ResourceLocation, LuckyBlockRegistry> REGISTRY = Maps.newConcurrentMap();
    private static final Random RANDOM = new Random();

    public static LuckyBlockRegistry getRegistryFor(ResourceLocation registryName) {
        if(!REGISTRY.containsKey(registryName)) {
            REGISTRY.put(registryName, new LuckyBlockRegistry(registryName));
        }
        return REGISTRY.get(registryName);
    }

    /** registry part **/

    private final ResourceLocation registryName;
    private final Map<EnumLuck, List<ILuckyEvent>> EVENT_REGISTRY = Maps.newEnumMap(EnumLuck.class);

    public LuckyBlockRegistry(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    public ResourceLocation getLuckyBlockName() {
        return registryName;
    }

    public void register(EnumLuck luck, ILuckyEvent... events) {
        if(!EVENT_REGISTRY.containsKey(luck)) EVENT_REGISTRY.put(luck, new ArrayList<>());
        EVENT_REGISTRY.get(luck).addAll(Arrays.asList(events));
    }

    public void registerDrop(EnumLuck luck, ItemStack... stacks) {
        register(luck, new EventDrop(stacks));
    }

    public ILuckyEvent getRandomEvent(int luck) {
        return getRandomEvent(EnumLuck.getRandomValue(luck));
    }

    public ILuckyEvent getRandomEvent(EnumLuck luck) {
        List<ILuckyEvent> eventList = this.EVENT_REGISTRY.get(luck);
        if(eventList == null || eventList.isEmpty()) return new EventNotFound();
        return eventList.get(RANDOM.nextInt(eventList.size()));
    }

}
