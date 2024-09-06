package net.pokepandamon.strife3.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.*;

public class CustomSword extends SwordItem {
    private ArrayList<String> toolTip = new ArrayList<String>();

    private static Map<String, ArrayList<String>> itemTooltips = new HashMap<String, ArrayList<String>> () {{
        put("butcher_knife", new ArrayList<String>(){{ add("item.strife3.butcher_knife.tooltip.1"); add("item.strife3.butcher_knife.tooltip.2"); add("item.strife3.butcher_knife.tooltip.3"); add("item.strife3.butcher_knife.tooltip.4"); add("item.strife3.butcher_knife.tooltip.5"); }});
    }};

    public CustomSword(String itemType, ToolMaterials toolMaterials, Settings settings) {
        super(toolMaterials, settings);
        this.toolTip = itemTooltips.get(itemType);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        for(String line : toolTip){
            tooltip.add(Text.translatable(line));
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
