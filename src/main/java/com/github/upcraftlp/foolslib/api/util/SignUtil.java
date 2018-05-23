package com.github.upcraftlp.foolslib.api.util;

import com.github.upcraftlp.foolslib.FoolsLib;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SignUtil {

    public static void setSignText(World world, BlockPos pos, String... lines) {
        IChatComponent[] components = new IChatComponent[lines.length];
        for(int i = 0; i < lines.length; i++)
            try {
                ICommandSender dummyCommandSender = new ICommandSender() {
                    @Override
                    public String getCommandSenderName() {
                        return "Sign";
                    }

                    @Override
                    public IChatComponent getDisplayName() {
                        return new ChatComponentText(this.getCommandSenderName());
                    }

                    @Override
                    public void addChatMessage(IChatComponent component) {
                    }

                    @Override
                    public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
                        return true;
                    }

                    @Override
                    public BlockPos getPosition() {
                        return pos;
                    }

                    @Override
                    public Vec3 getPositionVector() {
                        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
                    }

                    @Override
                    public World getEntityWorld() {
                        return world;
                    }

                    @Override
                    public Entity getCommandSenderEntity() {
                        return null;
                    }

                    @Override
                    public boolean sendCommandFeedback() {
                        return false;
                    }

                    @Override
                    public void setCommandStat(CommandResultStats.Type type, int amount) {
                    }
                };
                components[i] = ChatComponentProcessor.processComponent(dummyCommandSender, new ChatComponentText(lines[i]), null);
            } catch(CommandException e) {
                FoolsLib.getLogger().error("error parsing sign text!", e);
            }
        setSignText(world, pos, components);
    }

    public static void setSignText(World world, BlockPos pos, IChatComponent... lines) {
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TileEntitySign) {
            NBTTagCompound nbt = new NBTTagCompound();
            te.writeToNBT(nbt);
            for(int i = 0; i < Math.min(4, lines.length); i++)
                nbt.setString("Text" + (i + 1), lines[i].getFormattedText());
            te.readFromNBT(nbt);
            te.markDirty();
        }
    }

}
