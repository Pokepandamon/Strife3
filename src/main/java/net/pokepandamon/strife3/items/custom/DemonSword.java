package net.pokepandamon.strife3.items.custom;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.items.CustomSword;

import static net.minecraft.item.Item.BASE_ATTACK_DAMAGE_MODIFIER_ID;
import static net.minecraft.item.Item.BASE_ATTACK_SPEED_MODIFIER_ID;

public class DemonSword extends CustomSword {
    public static final double attackDamage = 13;
    public static final double knockbackResistance = 4;
    public static final double movementSpeed = 0.03;
    public static final double maxHealth = 4;
    public static final double attackSpeed = 0;
    public static final AttributeModifiersComponent attributeModifiersComponent = AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)attackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                                                                                    .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                                                                                    .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(Identifier.of(Strife3.MOD_ID, "sword_knockback"), knockbackResistance, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                                                                                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(Identifier.of(Strife3.MOD_ID, "movement_speed"), movementSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                                                                                    .add(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(Identifier.of(Strife3.MOD_ID, "max_health"), maxHealth, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    public DemonSword(String itemType, ToolMaterials toolMaterials, Item.Settings settings) {
        super(itemType, toolMaterials, settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player){

        return false;
    }

}