package net.pokepandamon.strife3.items;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class Strife3ModelProvider extends FabricModelProvider {
    public Strife3ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.HEAVY_DIVERS_MASK, Models.GENERATED);
        itemModelGenerator.register(ModItems.HEAVY_DIVERS_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.HEAVY_DIVERS_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.HEAVY_DIVERS_BOOTS, Models.GENERATED);
    }
}
