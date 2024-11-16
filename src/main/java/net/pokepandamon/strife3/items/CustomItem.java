package net.pokepandamon.strife3.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItem extends Item {
    public CustomItem(String itemType, Settings settings) {
        super(settings);
        this.toolTip = itemTooltips.get(itemType);
    }

    private ArrayList<String> toolTip = new ArrayList<String>();
    //private Identifier BASE_HEALTH_MODIFIER_ID = new Identifier("strife3", "items.custom");

    // Telling the tooltip setter which tooltips to fetch
    private static Map<String, ArrayList<String>> itemTooltips = new HashMap<String, ArrayList<String>>() {{
        put("data_core", new ArrayList<String>(){{ add("item.strife3.data_core.tooltip.1"); add("item.strife3.data_core.tooltip.2"); add("item.strife3.data_core.tooltip.3"); add("item.strife3.data_core.tooltip.4");  add("item.strife3.data_core.tooltip.5");}});
        put("morphine", new ArrayList<String>(){{ add("item.strife3.morphine.tooltip.1"); add("item.strife3.morphine.tooltip.2"); add("item.strife3.morphine.tooltip.3"); add("item.strife3.morphine.tooltip.4"); add("item.strife3.morphine.tooltip.5"); add("item.strife3.morphine.tooltip.6"); add("item.strife3.morphine.tooltip.7"); add("item.strife3.morphine.tooltip.8"); add("item.strife3.morphine.tooltip.9"); }});
        put("medkit", new ArrayList<String>(){{ add("item.strife3.medkit.tooltip.1"); add("item.strife3.medkit.tooltip.2"); }});
        put("ether_tablet", new ArrayList<String>(){{ add("item.strife3.ether_tablet.tooltip.1"); add("item.strife3.ether_tablet.tooltip.2"); add("item.strife3.ether_tablet.tooltip.3"); add("item.strife3.ether_tablet.tooltip.4"); add("item.strife3.ether_tablet.tooltip.5"); add("item.strife3.ether_tablet.tooltip.6"); add("item.strife3.ether_tablet.tooltip.7"); add("item.strife3.ether_tablet.tooltip.8"); add("item.strife3.ether_tablet.tooltip.9"); add("item.strife3.ether_tablet.tooltip.10"); }});
        put("resistance_drug", new ArrayList<String>(){{ add("item.strife3.resistance_drug.tooltip.1"); add("item.strife3.resistance_drug.tooltip.2"); add("item.strife3.resistance_drug.tooltip.3"); add("item.strife3.resistance_drug.tooltip.4"); add("item.strife3.resistance_drug.tooltip.5"); add("item.strife3.resistance_drug.tooltip.6"); add("item.strife3.resistance_drug.tooltip.7"); add("item.strife3.resistance_drug.tooltip.8"); add("item.strife3.resistance_drug.tooltip.9"); }});
        put("speed_drug", new ArrayList<String>(){{ add("item.strife3.speed_drug.tooltip.1"); add("item.strife3.speed_drug.tooltip.2"); add("item.strife3.speed_drug.tooltip.3"); add("item.strife3.speed_drug.tooltip.4"); add("item.strife3.speed_drug.tooltip.5"); add("item.strife3.speed_drug.tooltip.6"); add("item.strife3.speed_drug.tooltip.7"); add("item.strife3.speed_drug.tooltip.8"); add("item.strife3.speed_drug.tooltip.9"); }});
        put("strength_drug", new ArrayList<String>(){{ add("item.strife3.strength_drug.tooltip.1"); add("item.strife3.strength_drug.tooltip.2"); add("item.strife3.strength_drug.tooltip.3"); add("item.strife3.strength_drug.tooltip.4"); add("item.strife3.strength_drug.tooltip.5"); add("item.strife3.strength_drug.tooltip.6"); add("item.strife3.strength_drug.tooltip.7"); add("item.strife3.strength_drug.tooltip.8"); add("item.strife3.strength_drug.tooltip.9"); }});
        put("super_drug", new ArrayList<String>(){{ add("item.strife3.super_drug.tooltip.1"); add("item.strife3.super_drug.tooltip.2"); add("item.strife3.super_drug.tooltip.3"); add("item.strife3.super_drug.tooltip.4"); add("item.strife3.super_drug.tooltip.5"); add("item.strife3.super_drug.tooltip.6"); add("item.strife3.super_drug.tooltip.7"); add("item.strife3.super_drug.tooltip.8"); add("item.strife3.super_drug.tooltip.9"); }});
    }};

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
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.tutorial.custom_item.tooltip"));
    }*/
}
