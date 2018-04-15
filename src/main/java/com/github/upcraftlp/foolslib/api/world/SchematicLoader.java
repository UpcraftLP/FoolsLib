package com.github.upcraftlp.foolslib.api.world;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.google.common.annotations.Beta;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Beta
public class SchematicLoader {

    private static final String SCHEMATIC_WIDTH = "Width";
    private static final String SCHEMATIC_HEIGHT = "Height";
    private static final String SCHEMATIC_LENGTH = "Length";
    private static final String SCHEMATIC_BLOCKS = "Blocks";
    private static final String SCHEMATIC_DATA = "Data";
    private static final String SCHEMATIC_TILE_ENTITIES = "TileEntities";
    private static final String SCHEMATIC_ENTITIES = "Entities";
    private static final String SCHEMATIC_KEY_MATERIALS = "Materials";
    private static final String SCHEMATIC_ALPHA_FORMAT = "Alpha";

    /**
     * load a schematic
     * @param world the world to laod in
     * @param pos the pos to load at
     * @param rotation the rotation of the structure
     * @param structure the location to load the structure from //TODO support loading from files?
     */
    public static void load(World world, BlockPos pos, TemplateRotation rotation, ResourceLocation structure) {
        InputStream inputStream = null;
        try {
            String domain = structure.getResourcePath();
            String path = structure.getResourcePath();
            if(!path.endsWith(".schematic")) path += ".schematic";
            inputStream = MinecraftServer.class.getResourceAsStream("/assets/" + domain + "/structures/" + path);
            NBTTagCompound schematicTag = CompressedStreamTools.readCompressed(inputStream);

            String format = schematicTag.getString(SCHEMATIC_KEY_MATERIALS);
            if(!format.equals(SCHEMATIC_ALPHA_FORMAT)) throw new UnsupportedEncodingException("schematic file is not in \"Alpha\" format!");

            short width = schematicTag.getShort(SCHEMATIC_WIDTH);
            short height = schematicTag.getShort(SCHEMATIC_HEIGHT);
            short length = schematicTag.getShort(SCHEMATIC_LENGTH);
            byte[] blocks = schematicTag.getByteArray(SCHEMATIC_BLOCKS);
            byte[] data = schematicTag.getByteArray(SCHEMATIC_DATA);
            NBTTagList entities = schematicTag.getTagList(SCHEMATIC_ENTITIES, Constants.NBT.TAG_LIST);
            NBTTagList tileEntities = schematicTag.getTagList(SCHEMATIC_TILE_ENTITIES, Constants.NBT.TAG_LIST);

            //FIXME implement!

        }
        catch (Exception e) {
            FoolsLib.getLogger().error("Error loading schematic {}", structure, e);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
