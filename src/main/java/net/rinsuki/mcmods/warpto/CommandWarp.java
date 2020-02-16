package net.rinsuki.mcmods.warpto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandWarp extends CommandBaseWithTabCompletion {
    @Override
    public String getName() {
        return "warp";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/warp <name> : 指定されたポイントへジャンプ";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            BlockPos targetPos) {

        WarpPointWorldSavedData data = WarpPointWorldSavedData.get(sender.getEntityWorld());

        List<String> list = new ArrayList<String>();

        if (args.length >= 2)
            return list;

        String prefix = args.length == 0 ? "" : args[0];

        Set<String> s = data.list();

        for (String k : s) {
            if (k.startsWith(prefix))
                list.add(k);
        }

        return list;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException(this.getUsage(sender));
        }
        WarpPointWorldSavedData data = WarpPointWorldSavedData.get(sender.getEntityWorld());
        WarpPoint point = data.get(args[0]);
        if (point == null) {
            throw new CommandException("指定されたワープポイントがありません");
        }

        WorldServer world = server.getWorld(point.dimensionID);
        if (world == null) {
            throw new CommandException(String.format("ワープ先のdimension %d が見つかりませんでした", point.dimensionID));
        }

        Entity entity = sender.getCommandSenderEntity();
        if (!(entity instanceof EntityPlayerMP)) {
            throw new CommandException("プレーヤーでないと転送できません");
        }
        EntityPlayerMP player = (EntityPlayerMP) entity;

        player.dismountRidingEntity();

        if (point.dimensionID != player.dimension) { // dimension warp
            player.changeDimension(point.dimensionID, new CustomTeleporter(new BlockPos(point.x, point.y, point.z)));
        } else {
            player.connection.setPlayerLocation(point.x, point.y, point.z, 0, 0);
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}