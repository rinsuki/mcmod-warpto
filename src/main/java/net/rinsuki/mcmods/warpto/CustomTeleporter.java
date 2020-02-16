package net.rinsuki.mcmods.warpto;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class CustomTeleporter implements ITeleporter {

    private BlockPos pos;

    CustomTeleporter(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        if (!(entity instanceof EntityPlayerMP)) {
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) entity;
        player.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
    }
}