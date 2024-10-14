package net.pokepandamon.strife3.items.custom;

import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class KingsCrownArmorRenderer extends GeoArmorRenderer<HybridMask> {
    public KingsCrownArmorRenderer(){
        super(new DefaultedItemGeoModel<>(Identifier.of(Strife3.MOD_ID, "kings_crown")));
    }
}
