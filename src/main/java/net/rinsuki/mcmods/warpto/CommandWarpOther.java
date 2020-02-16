package net.rinsuki.mcmods.warpto;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandWarpOther extends CommandBaseWithTabCompletion {

    @Override
    public String getName() {
        return "warpother";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/warpother <name> <player> : 指定したプレーヤーを指定されたワープポイントにワープさせます。";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            BlockPos targetPos) {
        if (args.length == 2) {
            return Arrays.asList(server.getPlayerList().getOnlinePlayerNames());
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 2) {
            throw new CommandException(this.getUsage(sender));
        }

        WarpPointWorldSavedData data = WarpPointWorldSavedData.get(sender.getEntityWorld());
        WarpPoint point = data.get(args[0]);
        if (point == null) {
            throw new CommandException("指定されたワープポイントがありません");
        }

        EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[1]);
        if (player == null) {
            throw new CommandException("指定されたプレーヤーは存在しません");
        }

        WorldServer world = server.getWorld(point.dimensionID);
        if (world == null) {
            throw new CommandException(String.format("ワープ先のdimension %d が見つかりませんでした", point.dimensionID));
        }

        player.dismountRidingEntity();

        if (point.dimensionID != player.dimension) { // dimension warp
            player.changeDimension(point.dimensionID, new CustomTeleporter(new BlockPos(point.x, point.y, point.z)));
        } else {
            player.connection.setPlayerLocation(point.x, point.y, point.z, 0, 0);
        }
    }
}