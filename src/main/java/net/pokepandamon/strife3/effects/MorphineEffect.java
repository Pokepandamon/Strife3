package net.pokepandamon.strife3.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.pokepandamon.strife3.Strife3;

public class MorphineEffect extends StatusEffect {
    private static StatusEffectInstance regen = new StatusEffectInstance(StatusEffects.REGENERATION, 15*20-2,2, false, false);
    private static StatusEffectInstance speed = new StatusEffectInstance(StatusEffects.SPEED, 2*20, 255, false, false);
    private static StatusEffectInstance blindness = new StatusEffectInstance(StatusEffects.BLINDNESS, 2*20,255, false, false);
    private static StatusEffectInstance poison = new StatusEffectInstance(StatusEffects.POISON, 2*20,1, false, false);
    private static StatusEffectInstance slowness = new StatusEffectInstance(StatusEffects.SLOWNESS, 2*20,255, false, false);

    private Integer ticksPassed = 0;

    public MorphineEffect(){
        super(StatusEffectCategory.BENEFICIAL, 0xe6e0700);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        Strife3.LOGGER.info(((Integer) duration).toString());
        ticksPassed = 300 - duration;
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        //Strife3.LOGGER.info(ticksPassed.toString());
        if (ticksPassed == 1) {
            Strife3.LOGGER.info("Regen Triggered");
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 15*20-2,2, false, false));
        }

        if (ticksPassed == 299) {
            Strife3.LOGGER.info("Ending Triggered");
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 2*20,255, false, false));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2*20, 255, false, false));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 2*20,1, false, false));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 2*20,255, false, false));
        }

        return super.applyUpdateEffect(entity, amplifier);
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
