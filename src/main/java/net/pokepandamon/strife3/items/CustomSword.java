package net.pokepandamon.strife3.items;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;

import java.util.*;

public class CustomSword extends SwordItem {
    private ArrayList<String> toolTip = new ArrayList<String>();
    //private Identifier BASE_HEALTH_MODIFIER_ID = new Identifier("strife3", "items.custom");

    // Telling the tooltip setter which tooltips to fetch
    private static Map<String, ArrayList<String>> itemTooltips = new HashMap<String, ArrayList<String>> () {{
        put("butcher_knife", new ArrayList<String>(){{ add("item.strife3.butcher_knife.tooltip.1"); add("item.strife3.butcher_knife.tooltip.2"); add("item.strife3.butcher_knife.tooltip.3"); add("item.strife3.butcher_knife.tooltip.4"); add("item.strife3.butcher_knife.tooltip.5"); add("item.strife3.butcher_knife.tooltip.6"); }});
        put("combat_knife", new ArrayList<String>(){{ add("item.strife3.combat_knife.tooltip.1"); add("item.strife3.combat_knife.tooltip.2"); add("item.strife3.combat_knife.tooltip.3"); add("item.strife3.combat_knife.tooltip.4"); add("item.strife3.combat_knife.tooltip.5"); add("item.strife3.combat_knife.tooltip.6"); }});
        put("crowbar", new ArrayList<String>(){{ add("item.strife3.crowbar.tooltip.1"); add("item.strife3.crowbar.tooltip.2"); add("item.strife3.crowbar.tooltip.3"); add("item.strife3.crowbar.tooltip.4"); add("item.strife3.crowbar.tooltip.5"); add("item.strife3.crowbar.tooltip.6"); }});
        put("crude_sword", new ArrayList<String>(){{ add("item.strife3.crude_sword.tooltip.1"); add("item.strife3.crude_sword.tooltip.2"); add("item.strife3.crude_sword.tooltip.3"); add("item.strife3.crude_sword.tooltip.4"); add("item.strife3.crude_sword.tooltip.5"); add("item.strife3.crude_sword.tooltip.6"); add("item.strife3.crude_sword.tooltip.7"); }});
        put("demon_sword", new ArrayList<String>(){{ add("item.strife3.demon_sword.tooltip.1"); add("item.strife3.demon_sword.tooltip.2"); add("item.strife3.demon_sword.tooltip.3"); add("item.strife3.demon_sword.tooltip.4"); add("item.strife3.demon_sword.tooltip.5"); add("item.strife3.demon_sword.tooltip.6"); add("item.strife3.demon_sword.tooltip.7"); add("item.strife3.demon_sword.tooltip.8"); add("item.strife3.demon_sword.tooltip.9"); add("item.strife3.demon_sword.tooltip.10"); add("item.strife3.demon_sword.tooltip.11"); add("item.strife3.demon_sword.tooltip.12"); add("item.strife3.demon_sword.tooltip.13"); add("item.strife3.demon_sword.tooltip.14"); add("item.strife3.demon_sword.tooltip.15"); add("item.strife3.demon_sword.tooltip.16"); add("item.strife3.demon_sword.tooltip.17"); }});
        put("katana", new ArrayList<String>(){{add("item.strife3.katana.tooltip.1"); add("item.strife3.katana.tooltip.2"); add("item.strife3.katana.tooltip.3"); add("item.strife3.katana.tooltip.4"); add("item.strife3.katana.tooltip.5"); add("item.strife3.katana.tooltip.6"); add("item.strife3.katana.tooltip.7"); add("item.strife3.katana.tooltip.8");}});
        put("long_sword", new ArrayList<String>(){{ add("item.strife3.long_sword.tooltip.1"); add("item.strife3.long_sword.tooltip.2"); add("item.strife3.long_sword.tooltip.3"); add("item.strife3.long_sword.tooltip.4"); add("item.strife3.long_sword.tooltip.5"); add("item.strife3.long_sword.tooltip.6"); }});
    }};

    public CustomSword(String itemType, ToolMaterials toolMaterials, Settings settings) {
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
