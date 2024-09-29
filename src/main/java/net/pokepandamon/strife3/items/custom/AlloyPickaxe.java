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
import net.pokepandamon.strife3.items.CustomAxe;
import net.pokepandamon.strife3.items.CustomPickaxe;

import static net.minecraft.enchantment.Enchantments.EFFICIENCY;
import static net.minecraft.enchantment.Enchantments.MENDING;

public class AlloyPickaxe extends CustomPickaxe {
    public static final double attackDamage = 13;
    public static final double attackSpeed = 0;
    public static final AttributeModifiersComponent attributeModifiersComponent = AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)attackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                                                                                    .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    public AlloyPickaxe(String itemType, ToolMaterials toolMaterials, Settings settings) {
        super(itemType, toolMaterials, settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player){
        stack.addEnchantment(player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(EFFICIENCY).get(), 3);
        stack.addEnchantment(player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(MENDING).get(), 1);
        stack.set(DataComponentTypes.ENCHANTMENTS, stack.getEnchantments().withShowInTooltip(false));
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
