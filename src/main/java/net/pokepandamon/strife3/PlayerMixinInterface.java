package net.pokepandamon.strife3;

public interface PlayerMixinInterface {
    int morphineTimer();
    boolean onMorphine();
    void morphineTick();
    void useMorphine();
    int resistanceDrugTimer();
    boolean onResistanceDrug();
    void resistanceDrugTick();
    void useResistanceDrug();
    int speedDrugTimer();
    boolean onSpeedDrug();
    void speedDrugTick();
    void useSpeedDrug();
    int strengthDrugTimer();
    boolean onStrengthDrug();
    void strengthDrugTick();
    void useStrengthDrug();
    int superDrugTimer();
    boolean onSuperDrug();
    void superDrugTick();
    void useSuperDrug(int superDrugRandomChoiceM);
    int superDrugRandomChoice();
    boolean onMedkit();
    void medkitTick();
    void customPlayerTick();
    void useMedkit();
    void drowningTick();
    void heavyArmorTick();
    boolean anyHeavyArmorCooldown();
    boolean fullHeavyArmorCooldown();
    void locationTick();
    String getCurrentZone();
    boolean inDeepOptic();

}
