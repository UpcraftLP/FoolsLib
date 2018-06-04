package com.github.upcraftlp.foolslib.api.client;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

/**
 * Item Mesh definition that allows for lambdas without crashing in an obfuscated environment.
 * @see "https://github.com/micdoodle8/Galacticraft/issues/2740"
 * @see "https://github.com/micdoodle8/Galacticraft/commit/372d1ee12c01e44b7c430ec7f69c5f5983c6b752"
 */
public interface ItemMeshDefinitionCustom extends ItemMeshDefinition
{
    static ItemMeshDefinition create(ItemMeshDefinitionCustom lambda)
    {
        return lambda;
    }

    ModelResourceLocation getLocation(ItemStack stack);

    @Override
    default ModelResourceLocation getModelLocation(ItemStack stack)
    {
        return getLocation(stack);
    }
}
