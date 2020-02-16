package net.rinsuki.mcmods.warpto;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class WarpPoint {
    public int dimensionID;
    public int x;
    public int y;
    public int z;

    WarpPoint(NBTTagCompound t) {
        this.dimensionID = t.getInteger("dimensionID");
        this.x = t.getInteger("x");
        this.y = t.getInteger("y");
        this.z = t.getInteger("z");
    }

    WarpPoint(int dimensionID, BlockPos pos) {
        this.dimensionID = dimensionID;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public NBTTagCompound getAsNBTTagCompound() {
        NBTTagCompound t = new NBTTagCompound();
        t.setInteger("dimensionID", this.dimensionID);
        t.setInteger("x", this.x);
        t.setInteger("y", this.y);
        t.setInteger("z", this.z);
        return t;
    }
}