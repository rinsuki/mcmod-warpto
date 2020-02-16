package net.rinsuki.mcmods.warpto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public abstract class CommandBaseWithTabCompletion extends net.minecraft.command.CommandBase {

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
}