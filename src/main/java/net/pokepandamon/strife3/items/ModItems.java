package net.pokepandamon.strife3.items;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.items.custom.DemonSword;

public class ModItems {

    /*
    Base Attack Damage of each Sword:
        Wooden    1
        Stone     2
        Iron      3
        Gold      1
        Diamond   4
    Base Attack Speed 4
    */

    public static final Item BLUE_KEYCARD = registerItem("blue_keycard", new Item(new Item.Settings()));
    public static final Item GREEN_KEYCARD = registerItem("green_keycard", new Item(new Item.Settings()));
    public static final Item ONYX_KEYCARD = registerItem("onyx_keycard", new Item(new Item.Settings()));
    public static final Item RED_KEYCARD = registerItem("red_keycard", new Item(new Item.Settings()));
    public static final Item YELLOW_KEYCARD = registerItem("yellow_keycard", new Item(new Item.Settings()));
    public static final Item BUTCHER_KNIFE = registerItem("butcher_knife", new CustomSword("butcher_knife", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F))));
    public static final Item COMBAT_KNIFE = registerItem("combat_knife", new CustomSword("combat_knife", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F))));
    public static final Item CROWBAR = registerItem("crowbar", new CustomSword("crowbar", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F))));
    public static final Item CRUDE_SWORD = registerItem("crude_sword", new CustomSword("crude_sword", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F))));
    //public static final Item DEMON_SWORD = registerItem("demon_sword", new CustomSword("demon_sword", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F))));
    public static final Item DEMON_SWORD = registerItem("demon_sword", new DemonSword("demon_sword", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(DemonSword.attributeModifiersComponent)));
    public static final Item KATANA = registerItem("katana", new CustomSword("katana", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(DemonSword.attributeModifiersComponent)));
    //.component(Enchantment.builder().addEffect(EnchantmentEffectComponentTypes.ITEM_DAMAGE)
    public static void registerModItems() {
        Strife3.LOGGER.info("Registered Mod Items for " + Strife3.MOD_ID);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Strife3.MOD_ID, name), item);
    }


}
