package net.pokepandamon.strife3;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class WorldFlags extends PersistentState {
    public boolean depthsEntered;
    public boolean factoryEntered;
    public boolean deepOpticEntered;
    public boolean deepOpticLeft;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup){
        nbt.putBoolean("depthsEntered", depthsEntered);
        nbt.putBoolean("factoryEntered", factoryEntered);
        nbt.putBoolean("deepOpticEntered", deepOpticEntered);
        nbt.putBoolean("deepOpticLeft", deepOpticLeft);
        return nbt;
    }

    public static WorldFlags createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup){
        WorldFlags state = new WorldFlags();
        state.depthsEntered = tag.getBoolean("depthsEntered");
        state.factoryEntered = tag.getBoolean("factoryEntered");
        state.deepOpticEntered = tag.getBoolean("deepOpticEntered");
        state.deepOpticLeft = tag.getBoolean("deepOpticLeft");
        return state;
    }

    private static Type<WorldFlags> type = new Type<>(
            WorldFlags::new, // If there's no 'WorldFlags' yet create one
            WorldFlags::createFromNbt, // If there is a 'WorldFlags' NBT, parse it with 'createFromNbt'
            null // Supposed to be an 'DataFixTypes' enum, but we can just pass null
    );

    public static WorldFlags getServerState(MinecraftServer server) {
        // (Note: arbitrary choice to use 'World.OVERWORLD' instead of 'World.END' or 'World.NETHER'.  Any work)
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        // The first time the following 'getOrCreate' function is called, it creates a brand new 'WorldFlags' and
        // stores it inside the 'PersistentStateManager'. The subsequent calls to 'getOrCreate' pass in the saved
        // 'WorldFlags' NBT on disk to our function 'WorldFlags::createFromNbt'.
        WorldFlags state = persistentStateManager.getOrCreate(type, Strife3.MOD_ID);

        // If state is not marked dirty, when Minecraft closes, 'writeNbt' won't be called and therefore nothing will be saved.
        // Technically it's 'cleaner' if you only mark state as dirty when there was actually a change, but the vast majority
        // of mod writers are just going to be confused when their data isn't being saved, and so it's best just to 'markDirty' for them.
        // Besides, it's literally just setting a bool to true, and the only time there's a 'cost' is when the file is written to disk when
        // there were no actual change to any of the mods state (INCREDIBLY RARE).
        state.markDirty();

        return state;
    }
}
