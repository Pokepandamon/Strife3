package net.pokepandamon.strife3.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;

public class ModItems {
    public static final Item BLUE_KEYCARD = registerItem("blue_keycard", new Item(new Item.Settings()));
    public static final Item GREEN_KEYCARD = registerItem("green_keycard", new Item(new Item.Settings()));
    public static final Item ONYX_KEYCARD = registerItem("onyx_keycard", new Item(new Item.Settings()));
    public static final Item RED_KEYCARD = registerItem("red_keycard", new Item(new Item.Settings()));
    public static final Item YELLOW_KEYCARD = registerItem("yellow_keycard", new Item(new Item.Settings()));

    public static void registerModItems() {
        Strife3.LOGGER.info("Registered Mod Items for " + Strife3.MOD_ID);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Strife3.MOD_ID, name), item);
    }


}
