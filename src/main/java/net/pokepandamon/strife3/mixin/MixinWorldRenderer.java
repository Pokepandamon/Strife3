package net.pokepandamon.strife3.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldView;
import net.pokepandamon.strife3.datagen.Strife3Biomes;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Debug(export = true)
@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {
    @Shadow @Final private static Logger LOGGER;
    @Shadow private MinecraftClient client;

    @ModifyArg(method = "renderSky",at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 0), index = 1)
    public Identifier sunReplacement(Identifier id){
        ClientPlayerEntity player = this.client.player;
        if(!(player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.LUSH) || player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.SHALLOWS) || player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.DEPTHS))){
            return Identifier.ofVanilla("textures/environment/sun.png");
        }
        return Identifier.of("strife3","textures/environment/sun.png");
    }

    @ModifyArg(method = "renderSky",at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 1), index = 1)
    public Identifier moonReplacement(Identifier id){
        ClientPlayerEntity player = this.client.player;
        if(!(player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.LUSH) || player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.SHALLOWS) || player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.DEPTHS))){
            return Identifier.ofVanilla("textures/environment/sun.png");
        }
        return Identifier.of("strife3","textures/environment/moon_phases.png");
    }

    /*@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderStars()V"))
    public void butHa(WorldRenderer instance, Operation<Void> original){
        LOGGER.info("No stars, Vanya is dead");
    }*/

    @WrapOperation(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/VertexBuffer;draw(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/client/gl/ShaderProgram;)V"))
    public void butHa2(VertexBuffer instance, Matrix4f viewMatrix, Matrix4f projectionMatrix, ShaderProgram program, Operation<Void> original){
        //LOGGER.info("No stars? Vanya is asleep???");
        ClientPlayerEntity player = this.client.player;
        //LOGGER.info(String.valueOf(player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.LUSH)) + " | " + player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get() + " | " + Strife3Biomes.LUSH);
        //worldView.
        //BlockPos blockPos3 = worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos.add(k, 0, l));
        if(!(player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.LUSH) || player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.SHALLOWS) || player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).getKey().get().equals(Strife3Biomes.DEPTHS))){
            original.call(instance, viewMatrix, projectionMatrix, program);
        }
    }

    /*
            Biome biome = (Biome)worldView.getBiome(blockPos3).value();
            WorldView worldView = this.client.world;
            BlockPos blockPos3 = worldView.getTopPosition(Type.MOTION_BLOCKING, blockPos.add(k, 0, l));
     */

    /*@Shadow public abstract void tick();

    @Shadow @Final private static Logger LOGGER;*/

    /*@WrapOperation(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"))
    public Vec3d newSkyColor(ClientWorld instance, Vec3d cameraPos, float tickDelta, Operation<Vec3d> original){
        return new Vec3d(0, 0 ,0);
        //LOGGER.info(String.valueOf(original.call(instance, cameraPos, tickDelta)));
        //return original.call(instance,cameraPos, tickDelta);
    }*'

    /*@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BackgroundRenderer;applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZF)V"))
    public void blagh(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta){
        LOGGER.info(fogType.toString());
    }*/

    /*@WrapOperation(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/DimensionEffects;getFogColorOverride(FF)[F"))
    public float[] newSkyColorAgain(DimensionEffects instance, float skyAngle, float tickDelta, Operation<float[]> original){
        return new float[]{0F, 0F, 0F, 0F};
    }*/

    /*@ModifyArgs(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V"))
    void funtime(Args args){
        args.set(1, 0F);
        args.set(2, 0F);
        args.set(3, 0F);
        args.set(0, 0F);
    }*/
}
