package net.pokepandamon.strife3.music;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;

public class SoundEffects {
    /*public static final Identifier ABOVE_WATER_ID = Identifier.of(Strife3.MOD_ID, "ambient_test");
    public static SoundEvent ABOVE_WATER = SoundEvent.of(ABOVE_WATER_ID);
    public static SoundEvent ABOVE_WATER_REGISTERED = Registry.register(Registries.SOUND_EVENT, ABOVE_WATER_ID, ABOVE_WATER);*/

    public static final SoundEvent ETHER_TABLET = registerSound("ether_tablet");
    public static final SoundEvent ELEVATOR = registerSound("elevator");

    // actual registration of all the custom SoundEvents
    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(Strife3.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init(){
        Strife3.LOGGER.info("Registering " + Strife3.MOD_ID + " Sound Effects");

    }
}
