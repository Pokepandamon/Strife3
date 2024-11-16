package net.pokepandamon.strife3.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.block.custom.RadiationStation;

public class ModBlocks {
    public static final Block radiationStation = registerBlock("radiation_station_block", new RadiationStation(AbstractBlock.Settings.create().strength(4F)));
    public static final Block boneKelpFruit = registerBlock("bone_kelp_fruit", new Block(AbstractBlock.Settings.create().strength(2F, 3F)));
    public static final Block boneKelp = registerBlock("bone_kelp", new Block(AbstractBlock.Settings.create().strength(3F, 3F)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Strife3.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(Strife3.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBocks(){
        Strife3.LOGGER.info("Registering Custom Blocks for " + Strife3.MOD_ID);
    }
}
