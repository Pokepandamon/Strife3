package net.pokepandamon.strife3.mixin;

import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.pokepandamon.strife3.Strife3;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(TitleScreen.class)
public class MixinTitleScreen extends Screen {
	@Shadow @Final private static Logger LOGGER;
	@Unique private static ArrayList<CubeMapRenderer> panorama = new ArrayList<CubeMapRenderer>();
	@Shadow private float backgroundAlpha;
	@Unique private static RotatingCubeMapRenderer CUSTOM_ROTATING_PANORAMA_RENDERER;
	@Unique private static CubeMapRenderer chosenPanorama;

	protected MixinTitleScreen(Text title) {
		super(title);
	}

	/*//@Inject(at = @At("HEAD"), method = "loadTexturesAsync")
	@Redirect(method = "loadTexturesAsync", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/CubeMapRenderer;loadTexturesAsync(Lnet/minecraft/client/texture/TextureManager;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"))
    private static CompletableFuture<Void> loadTexturesAsync(CubeMapRenderer instance, TextureManager textureManager, Executor executor) {
		//LOGGER.info("I was here");
		return chosenPanorama.loadTexturesAsync(textureManager, executor);
	}*/

	public void renderPanoramaBackground(DrawContext context, float delta) {
		ROTATING_PANORAMA_RENDERER.render(context, this.width, this.height, this.backgroundAlpha, delta);
	}
}