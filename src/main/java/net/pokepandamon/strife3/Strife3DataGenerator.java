package net.pokepandamon.strife3;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.datagen.Strife3Biomes;
import net.pokepandamon.strife3.datagen.Strife3WorldGenerator;
import net.pokepandamon.strife3.items.Strife3ModelProvider;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class Strife3DataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(Strife3ModelProvider::new);
		pack.addProvider(Strife3WorldGenerator::new);
		pack.addProvider(Strife3TagGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder){
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, Strife3Dimensions::bootstrapType);
		registryBuilder.addRegistry(RegistryKeys.BIOME, Strife3Biomes::bootsrap);
	}
	private static class Strife3TagGenerator extends FabricTagProvider.ItemTagProvider {
		private static final TagKey<Item> BANNED_UDNERWATER_ITEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(Strife3.MOD_ID, "banned_underwater_items"));

		public Strife3TagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup arg) {
			// This creates a tag builder, where we add slime balls, rotten flesh and everything in the minecraft:dirt item tag.
			// This will automatically generate "src/main/generated/data/tutorial/tags/items/smelly_items.json" in the "generated" folder.
			getOrCreateTagBuilder(BANNED_UDNERWATER_ITEMS)
				.add(Items.BUCKET)
					.add(Items.END_ROD)
					.add(Items.DEAD_BUSH)
					.add(Items.LEVER)
					.add(Items.BROWN_MUSHROOM)
					.add(Items.RED_MUSHROOM)
					.add(Items.COBWEB)
					.add(Items.STRING)
					.add(Items.REDSTONE)
					.add(Items.TWISTING_VINES)
					.add(Items.WEEPING_VINES)
					.add(Items.WARPED_ROOTS)
					.add(Items.NETHER_SPROUTS)
					.add(Items.TORCH)
					.add(Items.SOUL_TORCH)
					.add(Items.HANGING_ROOTS)
					.add(Items.FERN)
					.add(Items.MOSS_CARPET)
					.add(Items.VINE)
					.add(Items.LARGE_FERN)
					.add(Items.SHORT_GRASS)
					.add(Items.TALL_GRASS)
					.add(Items.TURTLE_EGG)
					.add(Items.REDSTONE_TORCH)
					.add(Items.REPEATER)
					.add(Items.COMPARATOR)
					.add(Items.CAKE)
					.add(Items.STONECUTTER)
					.addOptionalTag(ItemTags.WOOL_CARPETS)
					.addOptionalTag(ItemTags.DOORS)
					.addOptionalTag(ItemTags.FENCE_GATES);
		}
	}
}
