package net.pokepandamon.strife3.items.custom;

import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.items.CustomArmor;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DiversMaskArmorRenderer extends GeoArmorRenderer<DiversMask> {
    public DiversMaskArmorRenderer(){
        super(new DefaultedItemGeoModel<>(Identifier.of(Strife3.MOD_ID, "divers_mask")));
    }
}
