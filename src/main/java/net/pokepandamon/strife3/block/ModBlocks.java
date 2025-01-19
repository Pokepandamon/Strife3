package net.pokepandamon.strife3.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.block.custom.*;
import net.pokepandamon.strife3.block.custom.MossBlock;

import java.util.ArrayList;

public class ModBlocks {
    public static final Block radiationStation = registerBlock("radiation_station_block", new RadiationStation(AbstractBlock.Settings.create().strength(4F)));
    public static final Block boneKelpFruit = registerBlock("bone_kelp_fruit", new Block(AbstractBlock.Settings.create().strength(5F, 3F)));
    public static final Block boneKelp = registerBlock("bone_kelp_block", new PillarBlock(AbstractBlock.Settings.create().strength(4F, 3F)));
    public static final Block kelpBlock = registerBlock("kelp_block", new Block(AbstractBlock.Settings.create().strength(2F, 3F)));
    public static final Block kelpGrowth = registerBlock("kelp_growth", new LeavesBlock(AbstractBlock.Settings.create().nonOpaque().noCollision().strength(0.5F)));
    public static final Block kelpFruit = registerBlock("kelp_fruit", new SeaPickleBlock(AbstractBlock.Settings.create().strength(0.25F).luminance(state -> state.get(SeaPickleBlock.PICKLES) * 3)));
    public static final Block smoothBubbleCoral = registerBlock("smooth_bubble_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Purple Terracota Pick
    public static final Block bubbleCoralSlab = registerBlock("bubble_coral_slab", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Crimson Slab Pick
    public static final Block smoothBrainCoral = registerBlock("smooth_brain_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Magenta Terracota Pick
    public static final Block brainCoralSlab = registerBlock("brain_coral_slab", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Purpur Slab Pick
    public static final Block smoothHornCoral = registerBlock("smooth_horn_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Yellow Terracota Pick
    public static final Block hornCoralSlab = registerBlock("horn_coral_slab", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Smooth Sandstone Slab Pick
    public static final Block smoothFireCoral = registerBlock("smooth_fire_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Pink Terracota Pick
    public static final Block fireCoralSlab = registerBlock("fire_coral_slab", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Cut Red Sandstone Slab Pick
    public static final Block smoothTubeCoral = registerBlock("smooth_tube_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Light Blue Terracota Pick
    public static final Block tubeCoralSlab = registerBlock("tube_coral_slab", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Prismarine Slab Pick
    public static final Block coralArch = registerBlock("coral_arch", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // White Terracota Pick
    public static final Block kelpRoot = registerBlock("kelp_root", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Green Terracota Hoe
    public static final Block boneKelpRoot = registerBlock("bone_kelp_root", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Mushroom Stem Hoe
    public static final Block boneKelpGrowth = registerBlock("bone_kelp_growth", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Dead Brain Coral Block Hoe
    public static final Block boneKelpShoot = registerFanBlock("bone_kelp_shoot", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F).noCollision())); // Dead Horn Coral Hoe
    public static final Block boneKelpShootWall = registerFanBlock("bone_kelp_shoot_wall", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F).noCollision())); // Dead Horn Coral Hoe
    public static final Block boneSpire = registerBlock("bone_spire", new BoneSpire(AbstractBlock.Settings.create().strength(5F, 3F))); // White Stained Glass Hoe
    public static final Block kelpyCobblestoneSlab = registerBlock("kelpy_cobblestone_slab", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Mossy Cobblestone Slab Pick
    public static final Block smoothDeepBrainCoral = registerBlock("smooth_deep_brain_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Light Blue Terracota Pick
    public static final Block deepBrainCoralBlock = registerBlock("deep_brain_coral_block", new GlazedTerracottaBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Pink Glazed Terracota Pick
    public static final Block deepBrainCoral = registerBlock("deep_brain_coral", new DeadCoralBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Brain Coral Pick
    public static final Block deepBrainCorallite = registerBlock("deep_brain_corallite", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Prismarine Slab Pick
    public static final Block deepBrainCoralFan = registerFanBlock("deep_brain_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Brain Coral Fan Pick
    public static final Block deepBrainCoralWallFan = registerFanBlock("deep_brain_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Brain Coral Fan Pick
    public static final Block smoothDeepBubbleCoral = registerBlock("smooth_deep_bubble_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Purple Terracota Pick
    public static final Block deepBubbleCoralBlock = registerBlock("deep_bubble_coral_block", new GlazedTerracottaBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Purple Glazed Terracota Pick
    public static final Block deepBubbleCoral = registerBlock("deep_bubble_coral", new DeadCoralBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Bubble Coral Pick
    public static final Block deepBubbleCorallite = registerBlock("deep_bubble_corallite", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Purpur Slab Pick
    public static final Block deepBubbleCoralFan = registerFanBlock("deep_bubble_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Bubble Coral Fan Pick
    public static final Block deepBubbleCoralWallFan = registerFanBlock("deep_bubble_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Bubble Coral Fan Pick
    public static final Block smoothDeepTubeCoral = registerBlock("smooth_deep_tube_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Cyan Terracota Pick
    public static final Block deepTubeCoralBlock = registerBlock("deep_tube_coral_block", new GlazedTerracottaBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Blue Glazed Terracota Pick
    public static final Block deepTubeCoral = registerBlock("deep_tube_coral", new DeadCoralBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Tube Coral Pick
    public static final Block deepTubeCorallite = registerBlock("deep_tube_corallite", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Andesite Slab Pick
    public static final Block deepTubeCoralFan = registerFanBlock("deep_tube_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Tube Coral Fan Pick
    public static final Block deepTubeCoralWallFan = registerFanBlock("deep_tube_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Tube Coral Fan Pick
    public static final Block smoothDeepFireCoral = registerBlock("smooth_deep_fire_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // White Terracota Pick
    public static final Block deepFireCoralBlock = registerBlock("deep_fire_coral_block", new GlazedTerracottaBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Red Glazed Terracota Pick
    public static final Block deepFireCoral = registerBlock("deep_fire_coral", new DeadCoralBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Fire Coral Pick
    public static final Block deepFireCorallite = registerBlock("deep_fire_corallite", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Smooth Sandstone Slab Pick
    public static final Block deepFireCoralFan = registerFanBlock("deep_fire_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Fire Coral Fan Pick
    public static final Block deepFireCoralWallFan = registerFanBlock("deep_fire_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Fire Coral Fan Pick
    public static final Block smoothDeepHornCoral = registerBlock("smooth_deep_horn_coral", new Block(AbstractBlock.Settings.create().strength(5F, 3F))); // Light Gray Terracota Pick
    public static final Block deepHornCoralBlock = registerBlock("deep_horn_coral_block", new GlazedTerracottaBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Yellow Glazed Terracota Pick
    public static final Block deepHornCoral = registerBlock("deep_horn_coral", new DeadCoralBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Horn Coral Pick
    public static final Block deepHornCorallite = registerBlock("deep_horn_corallite", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Smooth Sandstone Slab Pick
    public static final Block deepHornCoralFan = registerFanBlock("deep_horn_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Horn Coral Fan Pick
    public static final Block deepHornCoralWallFan = registerFanBlock("deep_horn_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Horn Coral Fan Pick
    public static final Block boneProtrusion = registerFanBlock("bone_protrusion", new BoneProtrusion(AbstractBlock.Settings.create().strength(5F, 3F)));
    public static final Block boneProtrusionWall = registerFanBlock("bone_protrusion_wall", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Horn Coral Fan Pick
    public static final Block spineBlock = registerBlock("spine_block", new Block(AbstractBlock.Settings.create().strength(5F, 3F).luminance((state) -> 15))); // Sea Lanturn Pick
    public static final Block obsidianCrystal = registerBlock("obsidian_crystal", new PaneBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Black Stained Glass Pane Pick
    public static final Block seedlingHeart = registerBlock("seedling_heart", new Block(AbstractBlock.Settings.create().strength(5F, 3F).luminance((state) -> 15))); // Sea Lanturn Pick
    public static final Block obsidianFruit = registerBlock("obsidian_fruit", new LanternBlock(AbstractBlock.Settings.create().strength(5F, 3F).luminance((state) -> 10))); // Soul Lanturn Pick
    public static final Block mossBlock = registerMossBlock("moss_block", new MossBlock(AbstractBlock.Settings.create().strength(5F, 3F)));
    public static final Block mutatedMoss = registerBlock("mutated_moss", new GlazedTerracottaBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Green Glazed Terracota Hoe
    public static final Block mossflower = registerBlock("mossflower", new Mossflower(AbstractBlock.Settings.create().strength(0.25F).luminance(state -> state.get(Mossflower.PICKLES) * 3)));
    public static final Block whispyMoss = registerBlock("whispy_moss", new PaneBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Green Stained Glass Pane Pick
    public static final Block kelpyCobblestoneWall = registerBlock("kelpy_cobblestone_wall", new WallBlock(AbstractBlock.Settings.create().strength(5F, 3F)));
    public static final Block deepBloom = registerBlock("deep_bloom", new Block(AbstractBlock.Settings.create().strength(5F, 3F).luminance((state) -> 15)));
    public static final Block deepSeaPickle = registerBlock("deep_sea_pickle", new DeepSeaPickle(AbstractBlock.Settings.create().strength(0.25F).luminance(state -> state.get(Mossflower.PICKLES) * 3)));
    public static final Block boneSlab = registerBlock("bone_slab", new SlabBlock(AbstractBlock.Settings.create().strength(5F, 3F))); //Smooth Quartz Slab Pick

    //public static ArrayList<ArrayList<Block>> fanBlocks = new ArrayList<>(); .luminance((state) -> 15)

    /*static{
        deepBrainCoralFan = registerFanBlock("deep_brain_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Brain Coral Fan Pick
        deepBrainCoralWallFan = registerFanBlock("wall_deep_brain_coral_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Wall Brain Coral Fan Pick
        ArrayList<Block> deepBrain = new ArrayList<>();
        deepBrain.add(deepBrainCoralFan);
        deepBrain.add(deepBrainCoralWallFan);
        fanBlocks.add(deepBrain);
        deepBubbleCoralFan = registerFanBlock("deep_bubble_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Bubble Coral Fan Pick
        deepBubbleCoralWallFan = registerFanBlock("wall_deep_bubble_coral_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Wall Bubble Coral Fan Pick
        ArrayList<Block> deepBubble = new ArrayList<>();
        deepBubble.add(deepBrainCoralFan);
        deepBubble.add(deepBrainCoralWallFan);
        fanBlocks.add(deepBubble);
        deepTubeCoralFan = registerFanBlock("deep_tube_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Tube Coral Fan Pick
        deepTubeCoralWallFan = registerFanBlock("wall_deep_tube_coral_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Wall Tube Coral Fan Pick
        ArrayList<Block> deepTube = new ArrayList<>();
        deepTube.add(deepBrainCoralFan);
        deepTube.add(deepBrainCoralWallFan);
        fanBlocks.add(deepTube);
        deepFireCoralFan = registerFanBlock("deep_fire_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Fire Coral Fan Pick
        deepFireCoralWallFan = registerFanBlock("wall_deep_fire_coral_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Wall Fire Coral Fan Pick
        ArrayList<Block> deepFire = new ArrayList<>();
        deepFire.add(deepBrainCoralFan);
        deepFire.add(deepBrainCoralWallFan);
        fanBlocks.add(deepFire);
        deepHornCoralFan = registerFanBlock("deep_horn_coral_fan", new DeadCoralFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Horn Coral Fan Pick
        deepHornCoralWallFan = registerFanBlock("wall_deep_horn_coral_fan", new DeadCoralWallFanBlock(AbstractBlock.Settings.create().strength(5F, 3F))); // Wall Horn Coral Fan Pick
        ArrayList<Block> deepHorn = new ArrayList<>();
        deepHorn.add(deepBrainCoralFan);
        deepHorn.add(deepBrainCoralWallFan);
        fanBlocks.add(deepHorn);

        for(ArrayList<Block> fans : fanBlocks){
            registerFanItem(fans.get(0).getTranslationKey(), fans.get(0), fans.get(1));
        }
    }*/

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Strife3.MOD_ID, name), block);
    }

    private static Block registerMossBlock(String name, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(Strife3.MOD_ID, name), block);
    }

    private static Block registerFanBlock(String name, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(Strife3.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(Strife3.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        //Registry.register(Registries.ITEM, Identifier.of(Strife3.MOD_ID, name), new CoralFan(block, new Item.Settings()));
    }

    private static void registerFanItem(String name, Block normalBlock, Block wallBlock){
        Strife3.LOGGER.info(name);
        Registry.register(Registries.ITEM, Identifier.of(Strife3.MOD_ID, name), new VerticallyAttachableBlockItem(normalBlock, wallBlock, new Item.Settings(), Direction.DOWN));
    }

    public static void registerModBocks(){
        Strife3.LOGGER.info("Registering Custom Blocks for " + Strife3.MOD_ID);
        registerFanItem("bone_kelp_shoot", boneKelpShoot, boneKelpShootWall);
        registerFanItem("deep_brain_coral_fan", deepBrainCoralFan, deepBrainCoralWallFan);
        registerFanItem("deep_bubble_coral_fan", deepBubbleCoralFan, deepBubbleCoralWallFan);
        registerFanItem("deep_tube_coral_fan", deepTubeCoralFan, deepTubeCoralWallFan);
        registerFanItem("deep_fire_coral_fan", deepFireCoralFan, deepFireCoralWallFan);
        registerFanItem("deep_horn_coral_fan", deepHornCoralFan, deepHornCoralWallFan);
        registerFanItem("bone_protrusion", boneProtrusion, boneProtrusionWall);
    }
}
