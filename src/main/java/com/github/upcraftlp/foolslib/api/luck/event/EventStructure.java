package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.api.luck.ILuckyEvent;
import com.github.upcraftlp.foolslib.api.world.structure.Structure;
import com.github.upcraftlp.foolslib.api.world.structure.StructureRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EventStructure implements ILuckyEvent {

    private final ResourceLocation structure;

    public EventStructure(String structure) {
        this(new ResourceLocation(structure));
    }

    public EventStructure(ResourceLocation structure) {
        this.structure = structure;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        Structure structure = StructureRegistry.getStructure(this.structure);
        structure.placeBlocksInWorld(world, pos.add(-Math.round(structure.getWidth() / 2.0F), 0.0D, - Math.round(structure.getLength() / 2.0F)), false);
    }
}