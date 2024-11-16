package net.pokepandamon.strife3.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.pokepandamon.strife3.PlayerMixinInterface;

public class StrengthDrugEffect extends StatusEffect {

    private Integer ticksPassed = 0;

    public StrengthDrugEffect(){
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
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 30*20,0, false, false, false));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30*20,0, false, false, false));
        //}

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60*20-2,0, false, false, false));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60*20-2,0, false, false, false));
        if(entity instanceof PlayerEntity){
            ((PlayerMixinInterface)entity).useStrengthDrug();
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
