package net.pokepandamon.strife3.items;

import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomArmor extends ArmorItem{
    private ArrayList<String> toolTip = new ArrayList<String>();
    //private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this)

    // Telling the tooltip setter which tooltips to fetch
    private static Map<String, ArrayList<String>> itemTooltips = new HashMap<String, ArrayList<String>> () {{
        put("divers_mask", new ArrayList<String>(){{ add("item.strife3.divers_mask.tooltip.1"); add("item.strife3.divers_mask.tooltip.2"); add("item.strife3.divers_mask.tooltip.3"); add("item.strife3.divers_mask.tooltip.4");}});
    }};

    public CustomArmor(String itemType, RegistryEntry<ArmorMaterial> armorMaterial, ArmorItem.Type type, Settings settings) {
        super(armorMaterial, type,settings);
        this.toolTip = itemTooltips.get(itemType);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        for(String line : toolTip){
            tooltip.add(Text.translatable(line));
        }
    }

    /*@Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }*/
}
