package net.pokepandamon.strife3;

public interface ServerPlayerMixinInterface {
    void customServerPlayerTick();
    void locationTick();
    boolean getClientAdminValue();
    void setClientAdminValue(boolean newClientAdminValue);
    boolean inDeepOptic();
}
