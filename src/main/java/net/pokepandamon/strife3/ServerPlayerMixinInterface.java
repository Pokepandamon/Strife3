package net.pokepandamon.strife3;

import java.io.IOException;

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
    void startWorldUpdater() throws IOException;
    void worldUpdaterTick();
    void testSchematics();
    void testTransformations(int schematicNumber);
}
