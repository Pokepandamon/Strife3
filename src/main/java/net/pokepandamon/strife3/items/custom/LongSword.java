package net.pokepandamon.strife3.items.custom;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.items.CustomSword;

public class LongSword extends CustomSword {
    public static final double attackDamage = 13;
    public static final double movementSpeed = 0.03;
    public static final double attackSpeed = 0;
    public static final AttributeModifiersComponent attributeModifiersComponent = AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)attackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                                                                                    .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                                                                                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(Identifier.of(Strife3.MOD_ID, "movement_speed"), movementSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    public LongSword(String itemType, ToolMaterials toolMaterials, Settings settings) {
        super(itemType, toolMaterials, settings);
    }
}
