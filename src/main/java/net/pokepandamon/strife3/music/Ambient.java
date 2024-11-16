package net.pokepandamon.strife3.music;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;

public class Ambient {
    /*public static final Identifier ABOVE_WATER_ID = Identifier.of(Strife3.MOD_ID, "ambient_test");
    public static SoundEvent ABOVE_WATER = SoundEvent.of(ABOVE_WATER_ID);
    public static SoundEvent ABOVE_WATER_REGISTERED = Registry.register(Registries.SOUND_EVENT, ABOVE_WATER_ID, ABOVE_WATER);*/

    public static final SoundEvent SHALLOWS = registerSound("ambient_underwater_shallows");
    public static final SoundEvent LUSH = registerSound("ambient_underwater_lush");
    public static final SoundEvent DEEP = registerSound("ambient_underwater_deep");
    public static final SoundEvent DEEP_OPTIC_UNDERWATER = registerSound("ambient_underwater_deep_optic");
    public static final SoundEvent DEEP_OPTIC_ABOVE_WATER = registerSound("ambient_above_water_deep_optic");
    public static final SoundEvent ABOVE_WATER = registerSound("ambient_above_water");

    // actual registration of all the custom SoundEvents
    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(Strife3.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init(){
        Strife3.LOGGER.info("Registering " + Strife3.MOD_ID + " Ambient");

    }
}
