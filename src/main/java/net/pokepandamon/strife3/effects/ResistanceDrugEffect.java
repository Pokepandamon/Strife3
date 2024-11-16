package net.pokepandamon.strife3.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.pokepandamon.strife3.PlayerMixinInterface;

public class ResistanceDrugEffect extends StatusEffect {
    private Integer ticksPassed = 0;

    public ResistanceDrugEffect(){
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
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 120*20,0, false, false, false));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 5*20, 0, false, false, false));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 120*20,1, false, false, false));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 120*20,1, false, false, false));
        if(entity instanceof PlayerEntity){
            ((PlayerMixinInterface)entity).useResistanceDrug();
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
