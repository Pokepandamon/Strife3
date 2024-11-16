package net.pokepandamon.strife3.music;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.Sound;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;

public class MasterMusic {
    public static void init(){
        Strife3.LOGGER.info("Registering " + Strife3.MOD_ID + " Sounds");
        Ambient.init();
        Song.init();
        SoundEffects.init();
    }
}
