package net.pokepandamon.strife3;

public interface ServerPlayerMixinInterface {
    void customServerPlayerTick();
    void locationTick();
    boolean getClientAdminValue();
    void setClientAdminValue(boolean newClientAdminValue);
    void soundTick();
    void songTick();
    void ambientTick();
    void armorDeEquipTick();
    void setSongTimer(int newTimer);
}
