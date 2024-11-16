package net.pokepandamon.strife3.items;

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
    public static final Item DIVERS_MASK = registerItem("divers_mask", new DiversMask("divers_mask", CustomMaterialInit.DIVERS_MASK, ArmorItem.Type.HELMET, (new Item.Settings()).maxCount(1)));
    public static final Item MORPHINE = registerItem("morphine", new Morphine("morphine", (new Item.Settings()).maxCount(1)));
    public static final Item HEAVY_DIVERS_MASK = registerItem("heavy_divers_mask", new HeavyDiversMask("heavy_divers_mask", CustomMaterialInit.HEAVY_DIVERS_MASK, ArmorItem.Type.HELMET, (new Item.Settings().maxCount(1))));
    public static final Item HEAVY_DIVERS_CHESTPLATE = registerItem("heavy_divers_chestplate", new CustomArmor("heavy_divers_chestplate", CustomMaterialInit.HEAVY_DIVERS_CHESTPLATE, ArmorItem.Type.CHESTPLATE, (new Item.Settings().maxCount(1))));
    public static final Item HEAVY_DIVERS_LEGGINGS = registerItem("heavy_divers_leggings", new CustomArmor("heavy_divers_leggings", CustomMaterialInit.HEAVY_DIVERS_LEGGINGS, ArmorItem.Type.LEGGINGS, (new Item.Settings().maxCount(1))));
    public static final Item HEAVY_DIVERS_BOOTS = registerItem("heavy_divers_boots", new CustomArmor("heavy_divers_boots", CustomMaterialInit.HEAVY_DIVERS_BOOTS, ArmorItem.Type.BOOTS, (new Item.Settings().maxCount(1))));
    public static final Item HYBRID_MASK = registerItem("hybrid_mask", new HybridMask("hybrid_mask", CustomMaterialInit.HYBRID_MASK, ArmorItem.Type.HELMET, (new Item.Settings()).maxCount(1)));
    public static final Item JUGGERNAUT = registerItem("juggernaut", new Juggernaut("juggernaut", CustomMaterialInit.JUGGERNAUT, ArmorItem.Type.HELMET, (new Item.Settings()).maxCount(1)));
    public static final Item KINGS_CROWN = registerItem("kings_crown", new KingsCrown("kings_crown", CustomMaterialInit.KINGS_CROWN, ArmorItem.Type.HELMET, (new Item.Settings()).maxCount(1).attributeModifiers(KingsCrown.attributeModifiersComponent)));
    public static final Item LONG_SWORD = registerItem("long_sword", new LongSword("long_sword", ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(LongSword.attributeModifiersComponent).maxDamage(131)));
    public static final Item NIGHT_VISION_GOGGLES = registerItem("night_vision_goggles", new NightVisionGoggles("night_vision_goggles", CustomMaterialInit.NIGHT_VISION_GOGGLES, ArmorItem.Type.HELMET, (new Item.Settings().maxCount(1))));
    public static final Item MEDKIT = registerItem("medkit", new Medkit("medkit", (new Item.Settings()).maxCount(1)));
    public static final Item ETHER_TABLET = registerItem("ether_tablet", new EtherTablet("ether_tablet", (new Item.Settings()).maxCount(1)));
    public static final Item RESISTANCE_DRUG = registerItem("resistance_drug", new ResistanceDrug("resistance_drug", (new Item.Settings()).maxCount(1)));
    public static final Item STEEL_AXE = registerItem("steel_axe", new SteelAxe("steel_axe", ToolMaterials.IRON, (new Item.Settings()).attributeModifiers(SteelAxe.attributeModifiersComponent)));
    public static final Item STEEL_PICKAXE = registerItem("steel_pickaxe", new SteelPickaxe("steel_pickaxe", ToolMaterials.IRON, (new Item.Settings()).attributeModifiers(SteelPickaxe.attributeModifiersComponent)));
    public static final Item SPEED_DRUG = registerItem("speed_drug", new SpeedDrug("speed_drug", (new Item.Settings()).maxCount(1)));
    public static final Item STRENGTH_DRUG = registerItem("strength_drug", new StrengthDrug("strength_drug", (new Item.Settings()).maxCount(1)));
    public static final Item SUPER_DRUG = registerItem("super_drug", new SuperDrug("super_drug", (new Item.Settings()).maxCount(1)));

    public static void registerModItems() {
        Strife3.LOGGER.info("Registered Mod Items for " + Strife3.MOD_ID);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Strife3.MOD_ID, name), item);
    }


}
