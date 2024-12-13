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
import net.pokepandamon.strife3.block.ModBlocks;

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
                entries.add(ModItems.BUTCHER_KNIFE);
                entries.add(ModItems.COMBAT_KNIFE);
                entries.add(ModItems.CROWBAR);
                entries.add(ModItems.CRUDE_SWORD);
                entries.add(ModItems.DEMON_SWORD);
                entries.add(ModItems.KATANA);
                entries.add(ModItems.CRUDE_AXE);
                entries.add(ModItems.DATA_CORE);
                entries.add(ModItems.ALLOY_AXE);
                entries.add(ModItems.ALLOY_PICKAXE);
                entries.add(ModItems.DIVERS_MASK);
                entries.add(ModItems.MORPHINE);
                entries.add(ModItems.HYBRID_MASK);
                entries.add(ModItems.HEAVY_DIVERS_MASK);
                entries.add(ModItems.HEAVY_DIVERS_CHESTPLATE);
                entries.add(ModItems.HEAVY_DIVERS_LEGGINGS);
                entries.add(ModItems.HEAVY_DIVERS_BOOTS);
                entries.add(ModItems.JUGGERNAUT);
                entries.add(ModItems.KINGS_CROWN);
                entries.add(ModItems.LONG_SWORD);
                entries.add(ModItems.MEDKIT);
                entries.add(ModItems.NIGHT_VISION_GOGGLES);
                entries.add(ModItems.RESISTANCE_DRUG);
                entries.add(ModItems.STEEL_AXE);
                entries.add(ModItems.STEEL_PICKAXE);
                entries.add(ModItems.SPEED_DRUG);
                entries.add(ModItems.STRENGTH_DRUG);
                entries.add(ModItems.SUPER_DRUG);
                entries.add(ModBlocks.radiationStation);
                entries.add(ModBlocks.boneKelpFruit);
                entries.add(ModBlocks.boneKelp);
                entries.add(ModBlocks.kelpBlock);
                entries.add(ModBlocks.kelpFruit);
                entries.add(ModBlocks.kelpGrowth);
                entries.add(ModBlocks.smoothBubbleCoral);
                entries.add(ModBlocks.bubbleCoralSlab);
                entries.add(ModBlocks.smoothBrainCoral);
                entries.add(ModBlocks.brainCoralSlab);
                entries.add(ModBlocks.smoothHornCoral);
                entries.add(ModBlocks.hornCoralSlab);
                entries.add(ModBlocks.smoothFireCoral);
                entries.add(ModBlocks.fireCoralSlab);
                entries.add(ModBlocks.smoothTubeCoral);
                entries.add(ModBlocks.tubeCoralSlab);
                entries.add(ModBlocks.coralArch);
                entries.add(ModBlocks.kelpRoot);
                entries.add(ModBlocks.boneKelpRoot);
                entries.add(ModBlocks.boneKelpGrowth);
                entries.add(ModBlocks.boneKelpShoot);
                entries.add(ModBlocks.boneSpire);
                entries.add(ModBlocks.kelpyCobblestoneSlab);
                entries.add(ModBlocks.smoothDeepBrainCoral);
                entries.add(ModBlocks.deepBrainCoralBlock);
                entries.add(ModBlocks.deepBrainCoral);
                entries.add(ModBlocks.deepBrainCorallite);
                entries.add(ModBlocks.deepBrainCoralFan);
                entries.add(ModBlocks.smoothDeepBubbleCoral);
                entries.add(ModBlocks.deepBubbleCoralBlock);
                entries.add(ModBlocks.deepBubbleCoral);
                entries.add(ModBlocks.deepBubbleCorallite);
                entries.add(ModBlocks.deepBubbleCoralFan);
                entries.add(ModBlocks.smoothDeepTubeCoral);
                entries.add(ModBlocks.deepTubeCoralBlock);
                entries.add(ModBlocks.deepTubeCoral);
                entries.add(ModBlocks.deepTubeCorallite);
                entries.add(ModBlocks.deepTubeCoralFan);
                entries.add(ModBlocks.smoothDeepFireCoral);
                entries.add(ModBlocks.deepFireCoralBlock);
                entries.add(ModBlocks.deepFireCoral);
                entries.add(ModBlocks.deepFireCorallite);
                entries.add(ModBlocks.deepFireCoralFan);
                entries.add(ModBlocks.smoothDeepHornCoral);
                entries.add(ModBlocks.deepHornCoral);
                entries.add(ModBlocks.deepHornCoralBlock);
                entries.add(ModBlocks.deepHornCorallite);
                entries.add(ModBlocks.deepHornCoralFan);
            }).build());
}
