package net.pokepandamon.strife3.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getWaterFogColor()I"))
    private static int waterFogColor(Biome instance, Operation<Integer> original){
        //.getRegistryManager().get(RegistryKeys.BIOME).getEntry(<Biome>).getIdAsString();
        //Lush
        if(instance.toString().equals("net.minecraft.world.biome.Biome@6818b365")){
            return 11812255;
        } //Depths
        else if (instance.toString().equals("net.minecraft.world.biome.Biome@5be409eb")){
            return 0;
        }
        return original.call(instance);
    }

    @Inject(method = "render", at = @At(value = "HEAD"))
    private static void letsDoIt(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci){
        skyDarkness = 0.0F;
    }/*

    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    private static float fixRed(float delta, float start, float end, Operation<Float> original){
        return end;
    }*/

    /*@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getWaterFogColor()I"))
    private static void whatBiome(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci){
        Strife3.LOGGER.info(((Biome) world.getBiome(BlockPos.ofFloored(camera.getPos())).value()).toString());
    }*/
}
