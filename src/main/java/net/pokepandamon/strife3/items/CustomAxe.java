package net.pokepandamon.strife3.items;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAxe extends AxeItem {
    private ArrayList<String> toolTip = new ArrayList<String>();

    // Telling the tooltip setter which tooltips to fetch
    private static Map<String, ArrayList<String>> itemTooltips = new HashMap<String, ArrayList<String>> () {{
        put("crude_axe", new ArrayList<String>(){{ add("item.strife3.crude_axe.tooltip.1"); add("item.strife3.crude_axe.tooltip.2"); add("item.strife3.crude_axe.tooltip.3"); add("item.strife3.crude_axe.tooltip.4"); add("item.strife3.crude_axe.tooltip.5"); add("item.strife3.crude_axe.tooltip.6"); add("item.strife3.crude_axe.tooltip.7"); }});
        put("alloy_axe", new ArrayList<String>(){{ add("item.strife3.alloy_axe.tooltip.1"); add("item.strife3.alloy_axe.tooltip.2"); add("item.strife3.alloy_axe.tooltip.3"); add("item.strife3.alloy_axe.tooltip.4"); add("item.strife3.alloy_axe.tooltip.5"); add("item.strife3.alloy_axe.tooltip.6"); add("item.strife3.alloy_axe.tooltip.7"); add("item.strife3.alloy_axe.tooltip.8"); }});
        put("steel_axe", new ArrayList<String>(){{ add("item.strife3.steel_axe.tooltip.1"); add("item.strife3.steel_axe.tooltip.2"); add("item.strife3.steel_axe.tooltip.3"); add("item.strife3.steel_axe.tooltip.4"); add("item.strife3.steel_axe.tooltip.5"); add("item.strife3.steel_axe.tooltip.6"); }});
    }};

    public CustomAxe(String itemType, ToolMaterials toolMaterials, Settings settings) {
        super(toolMaterials, settings);
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
}
