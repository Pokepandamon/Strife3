package net.pokepandamon.strife3.items;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.pokepandamon.strife3.Strife3;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CustomMaterialInit {
    public static final RegistryEntry<ArmorMaterial> DIVERS_MASK = register("divers_mask", Map.of(ArmorItem.Type.HELMET, 3, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> Ingredient.ofItems(ModItems.DIVERS_MASK), 1F, 0F, false);
    public static final RegistryEntry<ArmorMaterial> HEAVY_DIVERS_MASK = register("heavy_divers", Map.of(ArmorItem.Type.HELMET, 6, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(ModItems.HEAVY_DIVERS_MASK), 3.5F, 0, false);
    public static final RegistryEntry<ArmorMaterial> HEAVY_DIVERS_CHESTPLATE = register("heavy_divers", Map.of(ArmorItem.Type.HELMET, 3, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(ModItems.HEAVY_DIVERS_MASK), 2F, 2F, false);
    public static final RegistryEntry<ArmorMaterial> HEAVY_DIVERS_LEGGINGS = register("heavy_divers", Map.of(ArmorItem.Type.HELMET, 3, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(ModItems.HEAVY_DIVERS_MASK), 2F, 0, false);
    public static final RegistryEntry<ArmorMaterial> HEAVY_DIVERS_BOOTS = register("heavy_divers", Map.of(ArmorItem.Type.HELMET, 3, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(ModItems.HEAVY_DIVERS_MASK), 2F, 0, false);
    public static final RegistryEntry<ArmorMaterial> HYBRID_MASK = register("hybrid_mask", Map.of(ArmorItem.Type.HELMET, 3, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> Ingredient.ofItems(ModItems.DIVERS_MASK), 0F, 0F, false);
    public static final RegistryEntry<ArmorMaterial> JUGGERNAUT = register("juggernaut", Map.of(ArmorItem.Type.HELMET, 8, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> Ingredient.ofItems(ModItems.DIVERS_MASK), 5F, 0F, false);
    public static final RegistryEntry<ArmorMaterial> KINGS_CROWN = register("kings_crown", Map.of(ArmorItem.Type.HELMET, 0, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> Ingredient.ofItems(ModItems.DIVERS_MASK), 1.5F, 0F, false);
    public static final RegistryEntry<ArmorMaterial> NIGHT_VISION_GOGGLES = register("night_vision_goggles", Map.of(ArmorItem.Type.HELMET, 0, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3), 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> Ingredient.ofItems(ModItems.DIVERS_MASK), 0F, 0F, false);

    public static void load() {}

    public static RegistryEntry<ArmorMaterial> register(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, float toughness, float knockbackResistance, boolean dyeable) {
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(Strife3.id(id), "", dyeable)
        );

        var material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance);

        material = Registry.register(Registries.ARMOR_MATERIAL, Strife3.id(id), material);

        return RegistryEntry.of(material);
    }
}
