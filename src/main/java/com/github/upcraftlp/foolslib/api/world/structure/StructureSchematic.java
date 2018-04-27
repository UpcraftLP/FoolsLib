package com.github.upcraftlp.foolslib.api.world.structure;

import com.github.upcraftlp.foolslib.FoolsLib;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.io.InputStream;
import java.util.Objects;

public class StructureSchematic extends Structure {

    private static final String SCHEMATIC_WIDTH = "Width";
    private static final String SCHEMATIC_HEIGHT = "Height";
    private static final String SCHEMATIC_LENGTH = "Length";
    private static final String SCHEMATIC_BLOCKS = "Blocks";
    private static final String SCHEMATIC_DATA = "Data";
    private static final String SCHEMATIC_TILE_ENTITIES = "TileEntities";
    private static final String SCHEMATIC_ENTITIES = "Entities";
    private static final String SCHEMATIC_KEY_MATERIALS = "Materials";
    private static final String SCHEMATIC_ALPHA_FORMAT = "Alpha";

    private static final String KEY_POS_X = "x";
    private static final String KEY_POS_Y = "y";
    private static final String KEY_POS_Z = "z";

    private short width, height, length;
    private byte[] blocks, blockData;
    private NBTTagList tileEntities, entities;

    public StructureSchematic(ResourceLocation structure) {
        super(structure);
    }

    public StructureSchematic(String structure) {
        super(structure);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    protected void loadStructure() {
        try (InputStream stream = MinecraftServer.class.getResourceAsStream("/assets/" + this.structure.getResourceDomain() + "/structures/" + this.structure.getResourcePath())) {
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(stream);
            String format = nbt.getString(SCHEMATIC_KEY_MATERIALS);
            if(!format.equals(SCHEMATIC_ALPHA_FORMAT)) {
                FoolsLib.getLogger().error("Found Schematic file in {} format, which is not supported. Schematic files MUST be in \"Alpha\" format! Report this here: {}", format, FoolsLib.ISSUE_TRACKER);
                this.isLoaded = false;
                return;
            }
            this.length = nbt.getShort(SCHEMATIC_LENGTH);
            this.width = nbt.getShort(SCHEMATIC_WIDTH);
            this.height = nbt.getShort(SCHEMATIC_HEIGHT);
            this.blocks = nbt.getByteArray(SCHEMATIC_BLOCKS);
            this.blockData = nbt.getByteArray(SCHEMATIC_DATA);
            this.tileEntities = nbt.getTagList(SCHEMATIC_TILE_ENTITIES, Constants.NBT.TAG_COMPOUND);
            this.entities = nbt.getTagList(SCHEMATIC_ENTITIES, Constants.NBT.TAG_COMPOUND);
            this.isLoaded = true;
        } catch(Exception e) {
            FoolsLib.getLogger().error("Exception loading schematic structure " + Objects.toString(this.structure) + "!", e);
            this.isLoaded = false;
        }
    }

    @Override
    protected void unloadStructure() {
        this.blocks = new byte[0];
        this.blockData = new byte[0];
        this.width = this.height = this.length = 0;
        this.tileEntities = this.entities = new NBTTagList();
        this.isLoaded = false;
    }

    @Override
    public String getStructureType() {
        return "schematic";
    }

    @Override
    protected void doPlaceBlocks(World world, BlockPos startPos, boolean airReplaceBlocks) {
        //blocks
        for(int y = 0; y < this.height; y++) {
            for(int z = 0; z < this.length; z++) {
                for(int x = 0; x < this.width; x++) {
                    int index = (y * this.length + z) * this.width + x;
                    Block block = Block.getBlockById(blocks[index]);
                    if(!airReplaceBlocks && block == Blocks.air) continue;

                    //TODO integrity check for damaged structures?
                    IBlockState blockState = block.getStateFromMeta(blockData[index]);


                    world.setBlockState(startPos.add(x, y, z), blockState, 2);

                    //TODO rotation
                    //block.rotateBlock();
                    //rotate TE!
                }
            }
        }

        //TileEntities
        for(int i = 0; i < tileEntities.tagCount(); i++) {
            NBTTagCompound teData = tileEntities.getCompoundTagAt(i);
            TileEntity te = TileEntity.createAndLoadEntity(teData);
            if(te != null) {
                BlockPos pos = startPos.add(teData.getInteger(KEY_POS_X), teData.getInteger(KEY_POS_Y), teData.getInteger(KEY_POS_Z));
                world.removeTileEntity(pos);
                te.setPos(pos);
                te.setWorldObj(world);
                world.setTileEntity(pos, te);
            }
        }

        //Entities
        for(int i = 0; i < entities.tagCount(); i++) {
            Entity entity = EntityList.createEntityFromNBT(entities.getCompoundTagAt(i), world);
            world.spawnEntityInWorld(entity);
        }
    }

    @Override
    protected void doPlaceBlocksDelay(World world, BlockPos startPos, boolean airReplaceBlocks, int delayTicks) {
        new Thread(){
            @Override
            public void run() {
                for(int y = 0; y < height; y++) {
                    for(int z = 0; z < length; z++) {
                        for(int x = 0; x < width; x++) {
                            try {
                                this.wait(50 * delayTicks); //delay
                            } catch(InterruptedException e) {
                                //ignore
                            }
                            int index = (y * length + z) * width + x;
                            Block block = Block.getBlockById(blocks[index]);
                            if(!airReplaceBlocks && block == Blocks.air) continue;

                            //TODO integrity check for damaged structures?
                            IBlockState blockState = block.getStateFromMeta(blockData[index]);
                            BlockPos targetPos = startPos.add(x, y, z);
                            MinecraftServer.getServer().addScheduledTask(() -> {
                                //TODO rotation
                                //block.rotateBlock();
                                //rotate TE!
                                world.setBlockState(targetPos, blockState, 2);
                            });
                        }
                    }
                }
            }
        }.start();
    }
}
