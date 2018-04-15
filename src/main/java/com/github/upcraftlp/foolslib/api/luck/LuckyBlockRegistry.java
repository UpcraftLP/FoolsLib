package com.github.upcraftlp.foolslib.api.luck;

import com.github.upcraftlp.foolslib.api.luck.event.EventDrop;
import com.github.upcraftlp.foolslib.api.luck.event.EventNotFound;
import com.github.upcraftlp.foolslib.api.luck.event.LuckyEvent;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;

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
    private final Map<EnumLuck, List<LuckyEvent>> EVENT_REGISTRY = Maps.newEnumMap(EnumLuck.class);

    public LuckyBlockRegistry(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    public ResourceLocation getLuckyBlockName() {
        return registryName;
    }

    public void register(EnumLuck luck, LuckyEvent... events) {
        if(!EVENT_REGISTRY.containsKey(luck)) EVENT_REGISTRY.put(luck, new ArrayList<>());
        EVENT_REGISTRY.get(luck).addAll(Arrays.asList(events));
    }

    public void registerDrop(EnumLuck luck, ItemStack... stacks) {
        register(luck, new EventDrop(stacks));
    }

    public LuckyEvent getRandomEvent(int luck) {
        return getRandomEvent(EnumLuck.getRandomValue(luck));
    }

    public LuckyEvent getRandomEvent(EnumLuck luck) {
        List<LuckyEvent> eventList = this.EVENT_REGISTRY.get(luck);
        if(eventList == null || eventList.isEmpty()) return new EventNotFound();
        return eventList.get(RANDOM.nextInt(eventList.size()));
    }

}
