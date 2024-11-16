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
        put("divers_mask", new ArrayList<String>(){{ add("item.strife3.divers_mask.tooltip.1"); add("item.strife3.divers_mask.tooltip.2"); add("item.strife3.divers_mask.tooltip.3"); add("item.strife3.divers_mask.tooltip.4"); }});
        put("heavy_divers_mask", new ArrayList<String>(){{ add("item.strife3.heavy_divers_mask.tooltip.1"); add("item.strife3.heavy_divers_mask.tooltip.2"); add("item.strife3.heavy_divers_mask.tooltip.3"); add("item.strife3.heavy_divers_mask.tooltip.4"); add("item.strife3.heavy_divers_mask.tooltip.5"); add("item.strife3.heavy_divers_mask.tooltip.6"); add("item.strife3.heavy_divers_mask.tooltip.7"); add("item.strife3.heavy_divers_mask.tooltip.8"); add("item.strife3.heavy_divers_mask.tooltip.9"); add("item.strife3.heavy_divers_mask.tooltip.10"); add("item.strife3.heavy_divers_mask.tooltip.11"); add("item.strife3.heavy_divers_mask.tooltip.12"); }});
        put("heavy_divers_chestplate", new ArrayList<String>(){{ add("item.strife3.heavy_divers_chestplate.tooltip.1"); add("item.strife3.heavy_divers_chestplate.tooltip.2"); add("item.strife3.heavy_divers_chestplate.tooltip.3"); add("item.strife3.heavy_divers_chestplate.tooltip.4"); add("item.strife3.heavy_divers_chestplate.tooltip.5"); add("item.strife3.heavy_divers_chestplate.tooltip.6"); add("item.strife3.heavy_divers_chestplate.tooltip.7"); add("item.strife3.heavy_divers_chestplate.tooltip.8"); add("item.strife3.heavy_divers_chestplate.tooltip.9"); }});
        put("heavy_divers_leggings", new ArrayList<String>(){{ add("item.strife3.heavy_divers_leggings.tooltip.1"); add("item.strife3.heavy_divers_leggings.tooltip.2"); add("item.strife3.heavy_divers_leggings.tooltip.3"); add("item.strife3.heavy_divers_leggings.tooltip.4"); add("item.strife3.heavy_divers_leggings.tooltip.5"); add("item.strife3.heavy_divers_leggings.tooltip.6"); add("item.strife3.heavy_divers_leggings.tooltip.7"); add("item.strife3.heavy_divers_leggings.tooltip.8"); add("item.strife3.heavy_divers_leggings.tooltip.9"); }});
        put("heavy_divers_boots", new ArrayList<String>(){{ add("item.strife3.heavy_divers_boots.tooltip.1"); add("item.strife3.heavy_divers_boots.tooltip.2"); add("item.strife3.heavy_divers_boots.tooltip.3"); add("item.strife3.heavy_divers_boots.tooltip.4"); add("item.strife3.heavy_divers_boots.tooltip.5"); add("item.strife3.heavy_divers_boots.tooltip.6"); add("item.strife3.heavy_divers_boots.tooltip.7"); add("item.strife3.heavy_divers_boots.tooltip.8"); add("item.strife3.heavy_divers_boots.tooltip.9"); }});
        put("juggernaut", new ArrayList<String>(){{ add("item.strife3.juggernaut.tooltip.1"); add("item.strife3.juggernaut.tooltip.2"); add("item.strife3.juggernaut.tooltip.3"); add("item.strife3.juggernaut.tooltip.4"); }});
        put("kings_crown", new ArrayList<String>(){{ add("item.strife3.kings_crown.tooltip.1"); add("item.strife3.kings_crown.tooltip.2"); add("item.strife3.kings_crown.tooltip.3"); add("item.strife3.kings_crown.tooltip.4"); add("item.strife3.kings_crown.tooltip.5"); add("item.strife3.kings_crown.tooltip.6"); }});
        put("night_vision_goggles", new ArrayList<String>(){{ add("item.strife3.night_vision_goggles.tooltip.1"); add("item.strife3.night_vision_goggles.tooltip.2"); add("item.strife3.night_vision_goggles.tooltip.3"); }});
    }};

    public CustomArmor(String itemType, RegistryEntry<ArmorMaterial> armorMaterial, ArmorItem.Type type, Settings settings) {
        super(armorMaterial, type,settings);
        this.toolTip = itemTooltips.get(itemType);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        try{
            for(String line : toolTip){
                tooltip.add(Text.translatable(line));
            }
        } catch (Exception e) {
            tooltip.add(Text.of("Not yet"));
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
