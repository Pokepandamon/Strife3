package net.pokepandamon.strife3.items;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.items.custom.*;

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
    public static final Item BUTCHER_KNIFE = registerItem("butcher_knife", new ButcherKnife("butcher_knife", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(ButcherKnife.attributeModifiersComponent).maxDamage(131)));
    public static final Item COMBAT_KNIFE = registerItem("combat_knife", new CustomSword("combat_knife", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F)).maxDamage(131)));
    public static final Item CROWBAR = registerItem("crowbar", new Crowbar("crowbar", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F))));
    public static final Item CRUDE_SWORD = registerItem("crude_sword", new CustomSword("crude_sword", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F))));
    public static final Item DEMON_SWORD = registerItem("demon_sword", new DemonSword("demon_sword", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(DemonSword.attributeModifiersComponent)));
    public static final Item KATANA = registerItem("katana", new Katana("katana", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(Katana.attributeModifiersComponent)));
    public static final Item CRUDE_AXE = registerItem("crude_axe", new CustomAxe("crude_axe", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.STONE, 0, 0.3F))));
    public static final Item DATA_CORE = registerItem("data_core", new CustomItem("data_core", (new Item.Settings()).maxCount(1)));
    public static final Item ALLOY_AXE = registerItem("alloy_axe", new AlloyAxe("alloy_axe", ToolMaterials.DIAMOND, (new Item.Settings()).attributeModifiers(AlloyAxe.attributeModifiersComponent)));
    public static final Item ALLOY_PICKAXE = registerItem("alloy_pickaxe", new AlloyPickaxe("alloy_pickaxe", ToolMaterials.DIAMOND, (new Item.Settings()).attributeModifiers(AlloyPickaxe.attributeModifiersComponent)));
    public static final Item DIVERS_MASK = registerItem("divers_mask", new DiversMask("divers_mask", ArmorMaterialInit.DIVERS_MASK, ArmorItem.Type.HELMET, (new Item.Settings()).maxCount(1)));
    public static final Item MORPHINE = registerItem("morphine", new Morphine("morphine", (new Item.Settings()).maxCount(1)));

    public static void registerModItems() {
        Strife3.LOGGER.info("Registered Mod Items for " + Strife3.MOD_ID);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Strife3.MOD_ID, name), item);
    }


}
