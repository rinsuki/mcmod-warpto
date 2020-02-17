package net.rinsuki.mcmods.warpto;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = WarptoMod.MODID, name = WarptoMod.NAME, version = WarptoMod.VERSION)
public class WarptoMod {
    public static final String MODID = "warpto";
    public static final String NAME = "Warp Command";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        System.out.println("SERVER STARTING IS FIRED!");
        event.registerServerCommand(new CommandWarp());
        event.registerServerCommand(new CommandWarpPointList());
        event.registerServerCommand(new CommandWarpOther());
        event.registerServerCommand(new CommandSetWarpPoint());
        event.registerServerCommand(new CommandRemoveWarpPoint());
    }
}