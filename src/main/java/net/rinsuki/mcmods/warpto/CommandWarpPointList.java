package net.rinsuki.mcmods.warpto;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandWarpPointList extends CommandBase {
    @Override
    public String getName() {
        return "warppointlist";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/warppointlist : ワープポイント一覧を表示します";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentString("ワープポイント一覧:"));
        WarpPointWorldSavedData data = WarpPointWorldSavedData.get(sender.getEntityWorld());
        for (String key : data.list()) {
            WarpPoint p = data.get(key);
            sender.sendMessage(new TextComponentString(
                    String.format("%s - dim=%d, pos=%d,%d,%d", key, p.dimensionID, p.x, p.y, p.z)));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}