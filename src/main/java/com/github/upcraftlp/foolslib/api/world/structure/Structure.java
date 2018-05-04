package com.github.upcraftlp.foolslib.api.world.structure;

import com.github.upcraftlp.foolslib.FoolsLib;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class Structure {

    @Nullable
    protected ResourceLocation structure;
    protected float integrity;
    protected boolean isLoaded;

    public Structure(ResourceLocation structure) {
        this.structure = structure;
        if(!StringUtils.isNullOrEmpty(this.getStructureType())) {
            String path = structure.getResourcePath();
            if(!path.endsWith(this.getStructureType())) {
                this.structure = new ResourceLocation(structure.getResourceDomain(), structure.getResourcePath() + "." + this.getStructureType());
            }
        }
    }

    public Structure(String structure) {
        this(new ResourceLocation(structure));
    }

    @Nullable
    public ResourceLocation getStructureLocation() {
        return structure;
    }

    public float getIntegrity() {
        return integrity;
    }

    public void setIntegrity(float integrity) {
        this.integrity = integrity;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getLength();

    public AxisAlignedBB getStructureBoundingBox() {
        return new AxisAlignedBB(0, 0, 0, this.getWidth(), this.getHeight(), this.getLength());
    }

    protected abstract void loadStructure();

    protected abstract void unloadStructure();

    /**
     * @param airReplaceBlocks if structure air blocks should replace blocks already in the world
     */
    public void placeBlocksInWorld(World world, BlockPos startPos, boolean airReplaceBlocks) {
        this.loadStructure();
        if(this.isLoaded) {
            if(this.getWidth() > 16 || this.getWidth() > 16 || this.getHeight() > 64) { //if > 1 chunk
                this.doPlaceBlocksDelay(world, startPos, airReplaceBlocks, 1);
                this.unloadStructure();
            }
            else this.doPlaceBlocks(world, startPos, airReplaceBlocks);
        }
        else FoolsLib.getLogger().error("Unable to load structure {}", this.structure);
    }

    protected abstract void doPlaceBlocks(World world, BlockPos startPos, boolean airReplaceBlocks);

    protected abstract void doPlaceBlocksDelay(World world, BlockPos startPos, boolean airReplaceBlocks, int delay);

    @Nullable
    public String getStructureType() {
        return null;
    }

}
