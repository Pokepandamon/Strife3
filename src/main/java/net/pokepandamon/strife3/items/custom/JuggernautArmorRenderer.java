package net.pokepandamon.strife3.items.custom;

import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class JuggernautArmorRenderer extends GeoArmorRenderer<DiversMask> {
    public JuggernautArmorRenderer(){
        super(new DefaultedItemGeoModel<>(Identifier.of(Strife3.MOD_ID, "juggernaut")));
    }
}
