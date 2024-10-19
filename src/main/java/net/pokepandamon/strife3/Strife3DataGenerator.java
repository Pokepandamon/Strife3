package net.pokepandamon.strife3;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.pokepandamon.strife3.datagen.Strife3WorldGenerator;
import net.pokepandamon.strife3.items.Strife3ModelProvider;

public class Strife3DataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(Strife3ModelProvider::new);
		pack.addProvider(Strife3WorldGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder){
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, Strife3Dimensions::bootstrapType);
	}
}
