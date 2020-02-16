package net.rinsuki.mcmods.warpto;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class WarpPointWorldSavedData extends WorldSavedData {
    public static final String DATA_NAME = "warpto.WarpPoint";

    private HashMap<String, WarpPoint> points = new HashMap<String, WarpPoint>();

    public WarpPointWorldSavedData() {
        super(DATA_NAME);
    }

    public WarpPointWorldSavedData(String name) {
        super(name);
    }

    public static WarpPointWorldSavedData get(World world) {
        MapStorage storage = world.getMapStorage();
        WarpPointWorldSavedData instance = (WarpPointWorldSavedData) storage
                .getOrLoadData(WarpPointWorldSavedData.class, DATA_NAME);
        if (instance == null) {
            instance = new WarpPointWorldSavedData();
            storage.setData(DATA_NAME, instance);
        }

        return instance;
    }

    public WarpPoint get(String name) {
        return this.points.get(name);
    }

    public Set<String> list() {
        return this.points.keySet();
    }

    public void set(String name, WarpPoint point) {
        this.points.put(name, point);
        this.markDirty();
    }

    public void remove(String name) {
        this.points.remove(name);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.points = new HashMap<String, WarpPoint>();

        NBTTagCompound t = nbt.getCompoundTag("points");

        for (String k : t.getKeySet()) {
            this.points.put(k, new WarpPoint(t.getCompoundTag(k)));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound t = new NBTTagCompound();

        for (String k : this.points.keySet()) {
            t.setTag(k, points.get(k).getAsNBTTagCompound());
        }

        compound.setTag("points", t);
        return compound;
    }
}