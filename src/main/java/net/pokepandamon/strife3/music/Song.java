package net.pokepandamon.strife3.music;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;

public class Song {
    public static final Identifier BOSS_BATTLE_ID = Identifier.of(Strife3.MOD_ID, "song/boss_battle");
    public static SoundEvent BOSS_BATTLE = SoundEvent.of(BOSS_BATTLE_ID);

    public static void init(){
        Strife3.LOGGER.info("Registering " + Strife3.MOD_ID + " Songs");
        Registry.register(Registries.SOUND_EVENT, BOSS_BATTLE_ID, BOSS_BATTLE);
    }
}
