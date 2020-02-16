package net.rinsuki.mcmods.warpto;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandRemoveWarpPoint extends CommandBaseWithTabCompletion {

    @Override
    public String getName() {
        return "rmwarpoint";
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "/rmwarppoint <name> : 指定されたワープポイントを削除します";
    }

    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args)
            throws CommandException {
        if (args.length != 1) {
            throw new CommandException(this.getUsage(sender));
        }
        final WarpPointWorldSavedData data = WarpPointWorldSavedData.get(sender.getEntityWorld());
        data.remove(args[0]);
        sender.sendMessage(new TextComponentString("ワープポイント " + args[0] + " を削除しました"));
    }
}