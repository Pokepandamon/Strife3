package net.pokepandamon.strife3.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;

public class ModItemGroups {
    public static void registerItemGroups() {
        Strife3.LOGGER.info("Registered Item Groups for " + Strife3.MOD_ID);
    }

    public static final ItemGroup STRIFE_3_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(Strife3.MOD_ID, "strife3_items"), FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.BLUE_KEYCARD))
            .displayName(Text.translatable("itemgroup.strife3.strife3_items"))
            .entries((displayContext, entries) -> {
                entries.add(ModItems.BLUE_KEYCARD);
                entries.add(ModItems.GREEN_KEYCARD);
                entries.add(ModItems.ONYX_KEYCARD);
                entries.add(ModItems.RED_KEYCARD);
                entries.add(ModItems.YELLOW_KEYCARD);
            }).build());
}
