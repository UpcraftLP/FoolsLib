package com.github.upcraftlp.foolslib.command;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.world.structure.StructureHelper;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

public class CommandLoadSchematic extends CommandBase {
    @Override
    public String getCommandName() {
        return "loadSchematic";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) { //loadSchematic <name> [<x> <y> <z>] [true|false]
        return "command." + FoolsLib.MODID + "." + getCommandName() + ".usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 1 && args.length != 4 && args.length != 5) {
            throw new WrongUsageException(this.getCommandUsage(sender));
        }
        ResourceLocation location = new ResourceLocation(args[0]);
        BlockPos pos = args.length >= 4 ? parseBlockPos(sender, args, 1, false) : sender.getPosition();
        boolean airReplaceBlocks = args.length != 5 || parseBoolean(args[4]);
        StructureHelper.loadStructure(location, sender.getEntityWorld(), pos, airReplaceBlocks);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return super.canCommandSenderUseCommand(sender) && FoolsConfig.enableStructureCommands;
    }
}
