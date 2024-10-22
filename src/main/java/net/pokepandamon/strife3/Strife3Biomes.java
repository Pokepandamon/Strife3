package net.pokepandamon.strife3;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class Strife3Biomes {
    public static final RegistryKey<Biome> SHALLOWS = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Strife3.MOD_ID, "shallows"));

    public static void bootstrap(Registerable<Biome> context){

    }
}
