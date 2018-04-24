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

public class StructureHelper {

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

    private static final String KEY_TILEENTITY_ID = "id";


    public static void loadStructure(String structure, World world, BlockPos start, boolean airReplaceBlocks) {
        loadStructure(new ResourceLocation(structure), world, start, airReplaceBlocks);
    }

    public static void loadStructure(ResourceLocation structure, World world, BlockPos start, boolean airReplaceBlocks) { //TODO schedule on separate thread!
        String id = structure.getResourceDomain();
        String path = structure.getResourcePath();
        if(!path.endsWith(".schematic")) path += ".schematic";
        try (InputStream stream = MinecraftServer.class.getResourceAsStream("/assets/" + id + "/structures/" + path)) {
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(stream);


            String format = nbt.getString(SCHEMATIC_KEY_MATERIALS);
            if(!format.equals(SCHEMATIC_ALPHA_FORMAT)) {
                FoolsLib.getLogger().error("Found Schematic file in {} format, which is not supported. Schematic files MUST be in \"Alpha\" format!");
                return; //TODO error?
            }

            short structureLength = nbt.getShort(SCHEMATIC_LENGTH);
            short structureWidth = nbt.getShort(SCHEMATIC_WIDTH);
            short structureHeight = nbt.getShort(SCHEMATIC_HEIGHT);

            byte[] blocks = nbt.getByteArray(SCHEMATIC_BLOCKS);
            byte[] blockData = nbt.getByteArray(SCHEMATIC_DATA);

            for(int y = 0; y < structureHeight; y++) {
                for(int z = 0; z < structureLength; z++) {
                    for(int x = 0; x < structureWidth; x++) {
                        int index = (y * structureLength + z) * structureWidth + x;
                        Block block = Block.getBlockById(blocks[index]);
                        if(!airReplaceBlocks && block == Blocks.air) continue;

                        //TODO integrity check for damaged structures?
                        IBlockState blockState = block.getStateFromMeta(blockData[index]);


                        world.setBlockState(start.add(x, y, z), blockState, 2);

                        //TODO rotation
                        //block.rotateBlock();
                        //rotate TE!
                    }
                }
            }

            NBTTagList tileEntities = nbt.getTagList(SCHEMATIC_TILE_ENTITIES, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < tileEntities.tagCount(); i++) {
                NBTTagCompound teData = tileEntities.getCompoundTagAt(i);
                TileEntity te = TileEntity.createAndLoadEntity(teData);
                if(te != null) {
                    BlockPos pos = start.add(teData.getInteger(KEY_POS_X), teData.getInteger(KEY_POS_Y), teData.getInteger(KEY_POS_Z));
                    world.removeTileEntity(pos);
                    te.setPos(pos);
                    te.setWorldObj(world);
                    world.setTileEntity(pos, te);
                }
            }

            NBTTagList entities = nbt.getTagList(SCHEMATIC_ENTITIES, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < entities.tagCount(); i++) {
                Entity entity = EntityList.createEntityFromNBT(entities.getCompoundTagAt(i), world);
                world.spawnEntityInWorld(entity);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
