package net.pokepandamon.strife3.items;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomPickaxe extends PickaxeItem {
    private ArrayList<String> toolTip = new ArrayList<String>();
    //private Identifier BASE_HEALTH_MODIFIER_ID = new Identifier("strife3", "items.custom");

    // Telling the tooltip setter which tooltips to fetch
    private static Map<String, ArrayList<String>> itemTooltips = new HashMap<String, ArrayList<String>> () {{
        put("alloy_pickaxe", new ArrayList<String>(){{ add("item.strife3.alloy_pickaxe.tooltip.1"); add("item.strife3.alloy_pickaxe.tooltip.2"); add("item.strife3.alloy_pickaxe.tooltip.3"); add("item.strife3.alloy_pickaxe.tooltip.4"); add("item.strife3.alloy_pickaxe.tooltip.5"); add("item.strife3.alloy_pickaxe.tooltip.6"); add("item.strife3.alloy_pickaxe.tooltip.7"); add("item.strife3.alloy_pickaxe.tooltip.8"); }});
    }};

    public CustomPickaxe(String itemType, ToolMaterials toolMaterials, Settings settings) {
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

        //super.appendTooltip(stack, context, tooltip, type);
        /*tooltip.add(Text.translatable("item.strife3.butcher_knife.tooltip.1"));
        tooltip.add(Text.translatable("item.strife3.butcher_knife.tooltip.2"));
        tooltip.add(Text.translatable("item.strife3.butcher_knife.tooltip.3"));
        tooltip.add(Text.translatable("item.strife3.butcher_knife.tooltip.4"));
        tooltip.add(Text.translatable("item.strife3.butcher_knife.tooltip.5"));
        tooltip.add(Text.translatable("item.strife3.butcher_knife.tooltip.6"));*/
    }
}
