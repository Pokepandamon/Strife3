package net.pokepandamon.strife3.effects;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pokepandamon.strife3.PlayerMixinInterface;
import net.pokepandamon.strife3.networking.PermissionLevelRequestS2CPayload;
import net.pokepandamon.strife3.networking.SuperDrugRandomValueS2CPayload;

public class SuperDrugEffect extends StatusEffect {
    private Integer ticksPassed = 0;

    public SuperDrugEffect(){
        super(StatusEffectCategory.BENEFICIAL, 0xe6e0700);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        //entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 15*20-2,2, false, false, false));
        //Strife3.LOGGER.info(ticksPassed.toString());
        /*if (ticksPassed == 1) {
            //Strife3.LOGGER.info("Regen Triggered");
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 15*20-2,2, false, false, false));
        }*/

        //if (ticksPassed == 299) {
            //Strife3.LOGGER.info("Ending Triggered");

        //}

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if(entity instanceof ServerPlayerEntity) {
            int randomChoice = entity.getWorld().getRandom().nextBetween(1, 5);
            if (randomChoice == 1) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 30 * 20, 2, false, false, false));
            } else if (randomChoice == 2) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 30 * 20, 2, false, false, false));
            } else if (randomChoice == 3) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 30 * 20, 2, false, false, false));
            } else {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 30 * 20, 2, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 30 * 20, 2, false, false, false));
            }
            if (entity instanceof ServerPlayerEntity) {
                ((PlayerMixinInterface) entity).useSuperDrug(randomChoice);
                ServerPlayNetworking.send(((ServerPlayerEntity) entity).server.getPlayerManager().getPlayer(entity.getUuid()), new SuperDrugRandomValueS2CPayload(randomChoice));
            }
        }
        super.onApplied(entity, amplifier);
    }

        /* Example
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0F);
        }

        return true;
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 50 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
     */
}
