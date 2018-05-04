package com.github.upcraftlp.foolslib.api.world.structure;

import com.github.upcraftlp.foolslib.world.gen.WorldGeneratorStructure;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class StructureRegistry {

    private static final Map<ResourceLocation, Structure> REGISTRY = Maps.newConcurrentMap();
    private static final List<String> structures = new ArrayList<>();

    public static ImmutableMap<ResourceLocation, Structure> getStructureRegistryImmutable() {
        return ImmutableMap.copyOf(REGISTRY);
    }

    public static Set<ResourceLocation> getStructures() {
        return ImmutableSet.copyOf(REGISTRY.keySet());
    }

    public static Structure getStructure(ResourceLocation structure) {
        return REGISTRY.get(structure);
    }

    public static void registerSchematicWorldGen(String structure) {
        registerSchematicWorldGen(new ResourceLocation(structure));
    }

    public static void registerSchematicWorldGen(ResourceLocation structure) {
        registerStructure(structure, new StructureSchematic(structure));
        if(structureExists(structure)) GameRegistry.registerWorldGenerator(new WorldGeneratorStructure(structure), 100000);
    }

    public static boolean registerStructure(ResourceLocation registryName, Structure structure) {
        Objects.requireNonNull(registryName, "registry name of a structure cannot be null!");
        Objects.requireNonNull(structure);
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

    public static boolean structureExists(ResourceLocation structure) {
        return !REGISTRY.isEmpty() && REGISTRY.containsKey(structure);
    }
}
