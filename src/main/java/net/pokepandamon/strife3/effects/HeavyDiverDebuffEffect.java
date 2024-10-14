package net.pokepandamon.strife3.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.pokepandamon.strife3.Strife3;

public class HeavyDiverDebuffEffect extends StatusEffect {

    public HeavyDiverDebuffEffect(){
        super(StatusEffectCategory.HARMFUL, 0xe525252);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        //Strife3.LOGGER.info(((Integer) duration).toString());
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 50,0, false, false, false));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 50,0, false, false, false));
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
