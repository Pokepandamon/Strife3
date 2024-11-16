package net.pokepandamon.strife3.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.LoadingDisplay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.DataCommand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.pokepandamon.strife3.Strife3;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Debug(export = true)
@Mixin(Screen.class)
public class MixinScreen{
	//@Shadow @Final private static Logger LOGGER;

	@WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/client/gui/CubeMapRenderer;)Lnet/minecraft/client/gui/RotatingCubeMapRenderer;"))
	private static RotatingCubeMapRenderer panorama(CubeMapRenderer cubeMapRenderer, Operation<RotatingCubeMapRenderer> original){
		ArrayList<CubeMapRenderer> panorama = new ArrayList<CubeMapRenderer>();
		panorama.add(new CubeMapRenderer(Identifier.of(Strife3.MOD_ID,"textures/gui/title/background/deep_optic_panorama")));
		panorama.add(new CubeMapRenderer(Identifier.of(Strife3.MOD_ID,"textures/gui/title/background/depths_panorama")));
		panorama.add(new CubeMapRenderer(Identifier.of(Strife3.MOD_ID,"textures/gui/title/background/caves_panorama")));
		panorama.add(new CubeMapRenderer(Identifier.of(Strife3.MOD_ID,"textures/gui/title/background/lush_panorama")));
		return original.call(panorama.get(Random.create().nextBetween(0, panorama.size()-1)));
		//return new RotatingCubeMapRenderer(new CubeMapRenderer(Identifier.of(Strife3.MOD_ID,"textures/gui/title/background/deep_optic_panorama")));
	}

	//@Shadow public static RotatingCubeMapRenderer ROTATING_PANORAMA_RENDERER;

	/*@Shadow static final Identifier MENU_BACKGROUND_TEXTURE;

	@Shadow static final Identifier HEADER_SEPARATOR_TEXTURE;

	@Shadow static final Identifier FOOTER_SEPARATOR_TEXTURE;

	@Shadow static final Identifier INWORLD_MENU_BACKGROUND_TEXTURE;

	@Shadow static final Identifier INWORLD_HEADER_SEPARATOR_TEXTURE;

	@Shadow static final Identifier INWORLD_FOOTER_SEPARATOR_TEXTURE;

	@Shadow static final long SCREEN_INIT_NARRATION_DELAY;

	@Shadow static final long NARRATOR_MODE_CHANGE_DELAY;*/

	/*@ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/RotatingCubeMapRenderer;<init>(Lnet/minecraft/client/gui/CubeMapRenderer;)V"))
                                        private static CubeMapRenderer butWhy(CubeMapRenderer cubeMapRenderer){
                                            return new CubeMapRenderer(Identifier.of(Strife3.MOD_ID,"textures/gui/title/background/deep_optic_panorama"));
                                        }*/
	/*static{
		ROTATING_PANORAMA_RENDERER = new RotatingCubeMapRenderer(new CubeMapRenderer(Identifier.of(Strife3.MOD_ID,"textures/gui/title/background/deep_optic_panorama")));
		MENU_BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/gui/menu_background.png");
		HEADER_SEPARATOR_TEXTURE = Identifier.ofVanilla("textures/gui/header_separator.png");
		FOOTER_SEPARATOR_TEXTURE = Identifier.ofVanilla("textures/gui/footer_separator.png");
		INWORLD_MENU_BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/gui/inworld_menu_background.png");
		INWORLD_HEADER_SEPARATOR_TEXTURE = Identifier.ofVanilla("textures/gui/inworld_header_separator.png");
		INWORLD_FOOTER_SEPARATOR_TEXTURE = Identifier.ofVanilla("textures/gui/inworld_footer_separator.png");
		SCREEN_INIT_NARRATION_DELAY = TimeUnit.SECONDS.toMillis(2L);
		NARRATOR_MODE_CHANGE_DELAY = SCREEN_INIT_NARRATION_DELAY;
	}*/

	/*@Shadow @Final private static Logger LOGGER;

	@Inject(method = "<clinit>",at = @At(value = "HEAD"))
	private static void gizmo(CallbackInfo ci){
		//LOGGER.info("I was here");
		ROTATING_PANORAMA_RENDERER = new RotatingCubeMapRenderer(new CubeMapRenderer(Identifier.of(Strife3.MOD_ID,"textures/gui/title/background/deep_optic_panorama")));
	}*/

}