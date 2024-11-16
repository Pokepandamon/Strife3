package net.pokepandamon.strife3.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.pokepandamon.strife3.Strife3;
import org.apache.http.util.Args;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.IntSupplier;

@Debug(export = true)
@Mixin(SplashOverlay.class)
public class MixinSpashOverlay {
    @Unique
    private int MONOCHROME_BLACK;
    @Unique
    private float f = 0.0F;
    /*@ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;registerTexture(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/texture/AbstractTexture;)V"))
    private static Identifier newLogo(Identifier id){

        return Identifier.of(Strife3.MOD_ID, "textures/gui/title/mojangstudios.png");
    }*/

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIFFIIII)V", ordinal = 0), index = 0)
    private Identifier newLogo1(Identifier texture){
        return Identifier.of(Strife3.MOD_ID, "textures/gui/title/mojangstudios-test.png");
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIFFIIII)V", ordinal = 1), index = 0)
    private Identifier newLogo2(Identifier texture){
        return Identifier.of(Strife3.MOD_ID, "textures/gui/title/mojangstudios-test.png");
    }

    /*@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V"), index = 5)
    private int newOverlay(int color){
        return ColorHelper.Argb.getArgb(0, 0,0);
    }
     */

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SplashOverlay;withAlpha(II)I"), index = 1)
    private int backgroundColor(int color){
        return 1;
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"))
    private int modifyColor(IntSupplier instance, Operation<Integer> original){
        return MONOCHROME_BLACK;
    }

    /*@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;setShaderColor(FFFF)V"))
    private void setColor(org.spongepowered.asm.mixin.injection.invoke.arg.Args args){
        args.set(0,0F);
        args.set(1,0F);
        args.set(2,0F);
        args.set(3,0F);
    }*/
    /*@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIFFIIII)V", ordinal = 1))
    private void afterRenderLogo(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci, @Local(ordinal = 3) float alpha){
        this.f = Math.min(alpha, this.f + 0.2F);
        context.setShaderColor(1F, 1F, 1F, this.f);
    }*/
}
