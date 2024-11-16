package net.pokepandamon.strife3.music;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;

public class Song {
    public static final SoundEvent SHALLOWS = registerSound("song_shallows");
    public static final SoundEvent LUSH = registerSound("song_lush");
    public static final SoundEvent DEEP = registerSound("song_deep");
    public static final SoundEvent VICTORY1 = registerSound("song_victory1");
    public static final SoundEvent VICTORY2 = registerSound("song_victory2");
    public static final SoundEvent DEEP_OPTIC = registerSound("song_deep_optic");
    public static final SoundEvent BOSS_BATTLE = registerSound("song_boss_battle");

    // actual registration of all the custom SoundEvents
    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(Strife3.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init(){
        Strife3.LOGGER.info("Registering " + Strife3.MOD_ID + " Songs");
    }
}
