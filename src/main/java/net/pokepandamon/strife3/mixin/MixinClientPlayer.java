package net.pokepandamon.strife3.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.pokepandamon.strife3.PlayerMixinInterface;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.networking.SuperDrugRandomValueS2CPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.network.ClientPlayerEntity.class)
public abstract class MixinClientPlayer extends MixinEntityPlayer{
    @Shadow public int underwaterVisibilityTicks;

    protected MixinClientPlayer(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /*@Shadow
    public boolean isSubmergedIn(TagKey<Fluid> water){
        return false;
    }*/

    /*@Inject(method = "init", at=@At(value = "TAIL"))
    public void packetReception(CallbackInfo ci){
        ClientPlayNetworking.registerGlobalReceiver(SuperDrugRandomValueS2CPayload.ID, (payload, context) -> {
            Strife3.LOGGER.info("Packet Reception" + payload.superDrugRandomValue());
            ((PlayerMixinInterface) this).useSuperDrug(payload.superDrugRandomValue());
        });
    }*/

    public float getUnderwaterVisibility() {
        if (!this.isSubmergedIn(FluidTags.WATER)) {
            return 0.0F;
        } else {
            float f = 600.0F;
            float g = 100.0F;
            if ((float)this.underwaterVisibilityTicks >= 600.0F) {
                return 0.5F;
            } else {
                float h = MathHelper.clamp((float)this.underwaterVisibilityTicks / 100.0F, 0.0F, 1.0F);
                float i = (float)this.underwaterVisibilityTicks < 100.0F ? 0.0F : MathHelper.clamp(((float)this.underwaterVisibilityTicks - 100.0F) / 500.0F, 0.0F, 1.0F);
                return h * 0.6F + i * 0.39999998F;
            }
        }
    }
}
