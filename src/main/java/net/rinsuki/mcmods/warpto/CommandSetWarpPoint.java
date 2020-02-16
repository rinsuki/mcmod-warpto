package net.rinsuki.mcmods.warpto;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandSetWarpPoint extends CommandBaseWithTabCompletion {

    @Override
    public String getName() {
        return "setwarppoint";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setwarppoint <name> : コマンド実行者の座標を指定された名前でワープポイントとして登録します。";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException(this.getUsage(sender));
        }
        WarpPointWorldSavedData data = WarpPointWorldSavedData.get(sender.getEntityWorld());
        WarpPoint point = new WarpPoint(sender.getEntityWorld().provider.getDimension(), sender.getPosition());
        data.set(args[0], point);

        sender.sendMessage(new TextComponentString(String.format("ワープポイント (dim=%d, pos=%d,%d,%d) を %s として登録しました",
                point.dimensionID, point.x, point.y, point.z, args[0])));
    }
}