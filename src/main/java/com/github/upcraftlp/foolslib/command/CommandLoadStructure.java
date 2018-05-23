package com.github.upcraftlp.foolslib.command;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.world.structure.Structure;
import com.github.upcraftlp.foolslib.api.world.structure.StructureRegistry;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CommandLoadStructure extends CommandBase {
    @Override
    public String getCommandName() {
        return "loadStructure";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) { //loadSchematic <name> [<x> <y> <z>] [true|false]
        return "commands." + FoolsLib.MODID + "." + getCommandName() + ".usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 1 && args.length != 4 && args.length != 5) {
            throw new WrongUsageException(this.getCommandUsage(sender));
        }
        ResourceLocation location = new ResourceLocation(args[0]);
        BlockPos pos = args.length >= 4 ? parseBlockPos(sender, args, 1, false) : sender.getPosition();
        boolean airReplaceBlocks = args.length != 5 || parseBoolean(args[4]);
        Structure structure = StructureRegistry.getStructure(location);
        if(structure != null) {
            structure.setIntegrity(1.0F);
            structure.placeBlocksInWorld(sender.getEntityWorld(), pos, airReplaceBlocks);
        }
        else throw new CommandException("commands." + FoolsLib.MODID + "." + getCommandName() + ".structure_not_found");
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return
                args.length == 1 ? getListOfStringsMatchingLastWord(args, StructureRegistry.getStructures()) :
                args.length > 0 && args.length < 5 ? func_175771_a(args, 1, pos) :
                args.length == 5 ? getListOfStringsMatchingLastWord(args, "true", "false") :
                        null;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return FoolsConfig.enableStructureCommand && super.canCommandSenderUseCommand(sender);
    }
}
