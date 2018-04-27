package com.github.upcraftlp.foolslib.api.world.structure;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class StructureRegistry {
    private static final Map<ResourceLocation, Structure> REGISTRY = Maps.newConcurrentMap();
    private static final List<String> structures = new ArrayList<>();

    public static ImmutableMap<ResourceLocation, Structure> getStructureRegistry() {
        return ImmutableMap.copyOf(REGISTRY);
    }

    public static Set<ResourceLocation> getStructures() {
        return ImmutableSet.copyOf(REGISTRY.keySet());
    }

    public static Structure getStructure(ResourceLocation structure) {
        return REGISTRY.get(structure);
    }

    public static boolean registerStructure(ResourceLocation registryName, Structure structure) {
        if(!REGISTRY.containsKey(registryName)) {
            REGISTRY.put(registryName, structure);
            String structureName = registryName.toString().toLowerCase(Locale.ROOT);
            if(structures.isEmpty()) structures.add(structureName);
            else for(int i = 0; i < structures.size(); i++) { //insert sorted
                String s = structures.get(i);
                if(s.compareTo(structureName) <= 0) structures.add(i, structureName);
            }
            return true;
        }
        return false;
    }

    public static boolean registerStructure(String name, Structure structure) {
        return registerStructure(new ResourceLocation(name), structure);
    }
}
