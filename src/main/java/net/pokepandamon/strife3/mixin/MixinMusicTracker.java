package net.pokepandamon.strife3.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvent;
import net.pokepandamon.strife3.music.Song;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public class MixinMusicTracker {
	@Unique private boolean playedMusic;

	@Inject(method = "play", at = @At("HEAD"), cancellable = true)
	public void onPlay(MusicSound type, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		// Check if the current screen is the TitleScreen and if the music type is MENU
		if (type == MusicType.MENU && (client.currentScreen instanceof net.minecraft.client.gui.screen.TitleScreen || client.currentScreen instanceof SelectWorldScreen || client.currentScreen instanceof MultiplayerScreen || client.currentScreen instanceof OptionsScreen || client.currentScreen instanceof CreateWorldScreen)) {
			// Prevent default menu music from playing
			ci.cancel();

			// Play custom music
			if(!playedMusic) {
				SoundEvent customSoundEvent = Song.DEEP_OPTIC;
				client.getSoundManager().play(PositionedSoundInstance.music(customSoundEvent));
				playedMusic = true;
			}
		}
	}

	//@Unique public int timeLeft = 0;

	/*@Inject(method = "tick", at = @At("HEAD"))
	public void playCustomTitleMusic(CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		// Check if the current screen is the TitleScreen
		SoundEvent customSoundEvent = Song.BOSS_BATTLE;
		if (client.currentScreen instanceof net.minecraft.client.gui.screen.TitleScreen) {
			// If the default music is already playing, stop it
			if (client.getMusicTracker().isPlayingType(MusicType.MENU)) {
				client.getMusicTracker().stop();

			}

			if(timeLeft == 0) {
				// Play custom music instead
				//client.getSoundManager().play(PositionedSoundInstance.music(customSoundEvent));
				timeLeft = 3600;
			}
			timeLeft--;
		}
	}*/
}