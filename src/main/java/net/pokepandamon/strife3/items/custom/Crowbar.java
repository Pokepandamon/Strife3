package net.pokepandamon.strife3.items.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.items.CustomSword;

import static net.minecraft.enchantment.Enchantments.UNBREAKING;

public class Crowbar extends CustomSword {
    public Crowbar(String itemType, ToolMaterials toolMaterials, Settings settings) {
        super(itemType, toolMaterials, settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player){
        stack.addEnchantment(player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(UNBREAKING).get(), 1);
        stack.set(DataComponentTypes.ENCHANTMENTS, stack.getEnchantments().withShowInTooltip(false));
        stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        /*stack.addEnchantment(UNBREAKING, 4);
        stack.addEnchantment(Enchantments.UNBREAKING, 1);
        stack.addEnchantment(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE.get(UNBREAKING), 4);*/
        /*stack.getTooltip().removeIf(text -> {
            // Check for enchantment text formatting, e.g., italics or gray color
            return text.contains(Text.literal("Unbreaking"));
        });*/
        return false;
    }

    /*@Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip
    }*/
}
