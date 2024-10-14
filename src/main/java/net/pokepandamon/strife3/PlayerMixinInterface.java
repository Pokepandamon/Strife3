package net.pokepandamon.strife3;

public interface PlayerMixinInterface {
    int steroidTimer();
    int morphineTimer();
    boolean onMorphine();
    boolean onSteroids();
    void morphineTick();
    void steroidsTick();
    void customPlayerTick();
    void useMorphine();
    void useSteroids();
    void drowningTick();
    void heavyArmorTick();
    boolean anyHeavyArmorCooldown();
    boolean fullHeavyArmorCooldown();
    void locationTick();

}
