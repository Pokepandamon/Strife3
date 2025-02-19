package net.pokepandamon.strife3;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.pokepandamon.strife3.block.ModBlocks;
import net.pokepandamon.strife3.mixin.MixinServerPlayerEntity;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class SchematicReference {
    private ArrayList<ArrayList<ArrayList<BlockState>>> originalSchematic = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<BlockState>>> NSchematic = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<BlockState>>> WSchematic = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<BlockState>>> SSchematic = new ArrayList<>();

    private ArrayList<ArrayList<ArrayList<BlockState>>> mirroredSchematic = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<BlockState>>> MNSchematic = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<BlockState>>> MWSchematic = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<BlockState>>> MSSchematic = new ArrayList<>();
    //private ArrayList<ArrayList<ArrayList<BlockState>>> newSchematic = new ArrayList<>();
    private ArrayList<ReplacementBlock> originalReplacement = new ArrayList<>();
    private ArrayList<ReplacementBlock> NReplacement = new ArrayList<>();
    private ArrayList<ReplacementBlock> WReplacement = new ArrayList<>();
    private ArrayList<ReplacementBlock> SReplacement = new ArrayList<>();

    private ArrayList<ReplacementBlock> mirroredReplacement = new ArrayList<>();
    private ArrayList<ReplacementBlock> MNReplacement = new ArrayList<>();
    private ArrayList<ReplacementBlock> MWReplacement = new ArrayList<>();
    private ArrayList<ReplacementBlock> MSReplacement = new ArrayList<>();
    public String name;
    //private static Map<String, String> bloodKelpRoot;
    public static boolean debug = false;
    private int volume = 0;
    private ArrayList<ArrayList<ArrayList<ArrayList<BlockState>>>> transformationPointer = new ArrayList<>();
    private ArrayList<ArrayList<ReplacementBlock>> transformationReplacementPointer = new ArrayList<>();
    private SchematicSearchResult schematicSearchResult = new SchematicSearchResult();
    private static ArrayList<Block> connectingBlocks = new ArrayList<>();

    static{
        connectingBlocks.add(ModBlocks.boneSpire);
        connectingBlocks.add(ModBlocks.obsidianCrystal);
        connectingBlocks.add(ModBlocks.whispyMoss);
    }

    /*static{
        bloodKelpRoot = Map.ofEntries(Map.entry("minecraft:mushroom_stem[down=false,east=false,north=false,south=false,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=false,south=false,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=false,south=false,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=false,south=false,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=false,south=true,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=false,south=true,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=false,south=true,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=false,south=true,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=true,south=false,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=true,south=false,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=true,south=false,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=true,south=false,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=true,south=true,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=true,south=true,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=true,south=true,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=false,north=true,south=true,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=false,south=false,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=false,south=false,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=false,south=false,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=false,south=false,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=false,south=true,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=false,south=true,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=false,south=true,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=false,south=true,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=true,south=false,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=true,south=false,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=true,south=false,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=true,south=false,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=true,south=true,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=true,south=true,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=true,south=true,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=false,east=true,north=true,south=true,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=false,south=false,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=false,south=false,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=false,south=false,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=false,south=false,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=false,south=true,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=false,south=true,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=false,south=true,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=false,south=true,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=true,south=false,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=true,south=false,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=true,south=false,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=true,south=false,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=true,south=true,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=true,south=true,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=true,south=true,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=false,north=true,south=true,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=false,south=false,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=false,south=false,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=false,south=false,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=false,south=false,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=false,south=true,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=false,south=true,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=false,south=true,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=false,south=true,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=true,south=false,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=true,south=false,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=true,south=false,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=true,south=false,up=true,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=true,south=true,up=false,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=true,south=true,up=false,west=true]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=true,south=true,up=true,west=false]","strife3:bone_kelp_root"),
                Map.entry("minecraft:mushroom_stem[down=true,east=true,north=true,south=true,up=true,west=true]","strife3:bone_kelp_root"));
    }*/

    /*public SchematicReference(NbtCompound schematic, Map<String, String> replacedKeys){
        //NbtCompound schematicTag = schematic.getCompound("Schematic");
        NbtCompound paletteTag = schematic.getCompound("Palette");

        NbtCompound newPaletteTag = new NbtCompound();
        NbtCompound paletteTagReversed = new NbtCompound();
        NbtCompound newPaletteTagReversed = new NbtCompound();

        for(String key : paletteTag.getKeys()){
            paletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), key);
             if(replacedKeys.containsKey(key)){
                 newPaletteTag.putInt(replacedKeys.get(key), paletteTag.getInt(key));
                 newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), replacedKeys.get(key));
             }else{
                 Strife3.LOGGER.info(key + " " + key.contains("minecraft:mushroom_stem"));
                 if(key.contains("minecraft:mushroom_stem")){
                     newPaletteTag.putInt("strife3:bone_kelp_root", paletteTag.getInt(key));
                     newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), "strife3:bone_kelp_root");
                 }
                 newPaletteTag.putInt(key, paletteTag.getInt(key));
                 newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), key);
             }
        }



        byte[] blockData = schematic.getByteArray("BlockData");

        //Strife3.LOGGER.info(newPaletteTagReversed.toString());

        short length = schematic.getShort("Length");
        short width = schematic.getShort("Width");
        short height = schematic.getShort("Height");

        for(int k = 0; k < height; k++){
            ArrayList<ArrayList<BlockState>> yArrayO = new ArrayList<>();
            for(int j = 0; j < length; j++){
                ArrayList<BlockState> zArrayO = new ArrayList<>();
                for(int i = 0; i < width; i++){
                    //Strife3.LOGGER.info("WLH" + " " + width + " " + length + " " + height + " " + i + " " + j + " " + k + " " +String.valueOf((int) blockData[i + j*width + k*width*length]));
                    zArrayO.add(getBlockStateFromString(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get());
                    if(replacedKeys.containsKey(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length])))){
                        replacement.add(new ReplacementBlock(i, j, k, getBlockStateFromString(newPaletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get()));
                    }
                }
                yArrayO.add(zArrayO);
            }
            originalSchematic.add(yArrayO);
        }

        //Strife3.LOGGER.info(originalSchematic.get(0).get(0).get(0).toString());
    }

    public SchematicReference(NbtCompound schematic, Map<String, String> replacedKeys, Map<String, String> specificReplacedKeys){
        //NbtCompound schematicTag = schematic.getCompound("Schematic");
        Strife3.LOGGER.info("Got to specific replaced keys");
        NbtCompound paletteTag = schematic.getCompound("Palette");

        NbtCompound newPaletteTag = new NbtCompound();
        NbtCompound paletteTagReversed = new NbtCompound();
        NbtCompound newPaletteTagReversed = new NbtCompound();

        for(String key : paletteTag.getKeys()){
            paletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), key);
            if(replacedKeys.containsKey(key)){
                newPaletteTag.putInt(replacedKeys.get(key), paletteTag.getInt(key));
                newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), replacedKeys.get(key));
            }else{
                if(key.contains("minecraft:mushroom_stem")){
                    newPaletteTag.putInt("strife3:bone_kelp_root", paletteTag.getInt(key));
                    newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), "strife3:bone_kelp_root");
                }else if(specificReplacedKeys.containsKey(key)){
                    newPaletteTag.putInt(specificReplacedKeys.get(key), paletteTag.getInt(key));
                    newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), specificReplacedKeys.get(key));
                }else {
                    newPaletteTag.putInt(key, paletteTag.getInt(key));
                    newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), key);
                }
            }
        }



        byte[] blockData = schematic.getByteArray("BlockData");

        //Strife3.LOGGER.info(newPaletteTagReversed.toString());

        short length = schematic.getShort("Length");
        short width = schematic.getShort("Width");
        short height = schematic.getShort("Height");

        for(int k = 0; k < height; k++){
            ArrayList<ArrayList<BlockState>> yArrayO = new ArrayList<>();
            for(int j = 0; j < length; j++){
                ArrayList<BlockState> zArrayO = new ArrayList<>();
                for(int i = 0; i < width; i++){
                    //Strife3.LOGGER.info("WLH" + " " + width + " " + length + " " + height + " " + i + " " + j + " " + k + " " +String.valueOf((int) blockData[i + j*width + k*width*length]));
                    zArrayO.add(getBlockStateFromString(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get());
                    if(replacedKeys.containsKey(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length])))){
                        replacement.add(new ReplacementBlock(i, j, k, getBlockStateFromString(newPaletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get()));
                    }
                }
                yArrayO.add(zArrayO);
            }
            originalSchematic.add(yArrayO);
        }

        //Strife3.LOGGER.info(originalSchematic.get(0).get(0).get(0).toString());
    }*/

    public SchematicReference(NbtCompound schematic, Map<String, String> replacedKeys, String name){
        //NbtCompound schematicTag = schematic.getCompound("Schematic");
        this.name = name;
        NbtCompound paletteTag = schematic.getCompound("Palette");

        NbtCompound newPaletteTag = new NbtCompound();
        NbtCompound paletteTagReversed = new NbtCompound();
        NbtCompound newPaletteTagReversed = new NbtCompound();

        for(String key : paletteTag.getKeys()){
            paletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), key);
            if(replacedKeys.containsKey(key)){
                newPaletteTag.putInt(replacedKeys.get(key), paletteTag.getInt(key));
                newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), replacedKeys.get(key));
            }else{
                if(key.contains("minecraft:mushroom_stem")){
                    newPaletteTag.putInt("strife3:bone_kelp_root", paletteTag.getInt(key));
                    newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), "strife3:bone_kelp_root");
                }else {
                    newPaletteTag.putInt(key, paletteTag.getInt(key));
                    newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), key);
                }
            }
        }



        byte[] blockData = schematic.getByteArray("BlockData");
        //this.volume = blockData.length;

        //Strife3.LOGGER.info(newPaletteTagReversed.toString());

        short length = schematic.getShort("Length");
        short width = schematic.getShort("Width");
        short height = schematic.getShort("Height");

        for(int k = 0; k < height; k++){
            ArrayList<ArrayList<BlockState>> yArrayO = new ArrayList<>();
            for(int j = 0; j < length; j++){
                ArrayList<BlockState> zArrayO = new ArrayList<>();
                for(int i = 0; i < width; i++){
                    //Strife3.LOGGER.info("WLH" + " " + width + " " + length + " " + height + " " + i + " " + j + " " + k + " " +String.valueOf((int) blockData[i + j*width + k*width*length]));
                    zArrayO.add(getBlockStateFromString(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get());
                    if(!getBlockStateFromString(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get().getBlock().equals(Blocks.WATER)){
                        this.volume++;
                    }
                    if(replacedKeys.containsKey(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))) || paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length])).contains("minecraft:mushroom_stem")){
                        originalReplacement.add(new ReplacementBlock(i, k, j, getBlockStateFromString(newPaletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get(),getBlockStateFromString(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get()));
                    }
                }
                yArrayO.add(zArrayO);
            }
            originalSchematic.add(yArrayO);
        }

        this.createTransformations();

        //Strife3.LOGGER.info(originalSchematic.get(0).get(0).get(0).toString());
    }

    public SchematicReference(NbtCompound schematic, Map<String, String> replacedKeys, Map<String, String> specificReplacedKeys, String name){
        //NbtCompound schematicTag = schematic.getCompound("Schematic");
        //Strife3.LOGGER.info("Ran specific thingie");
        String lookingGood = "{";
        for(String keys : specificReplacedKeys.keySet()){
            lookingGood = lookingGood + "," + keys + "::" + specificReplacedKeys.get(keys);
        }
        Strife3.LOGGER.info(lookingGood);
        this.name = name;
        NbtCompound paletteTag = schematic.getCompound("Palette");

        NbtCompound newPaletteTag = new NbtCompound();
        NbtCompound paletteTagReversed = new NbtCompound();
        NbtCompound newPaletteTagReversed = new NbtCompound();

        for(String key : paletteTag.getKeys()){
            paletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), key);
            if(replacedKeys.containsKey(key)){
                newPaletteTag.putInt(replacedKeys.get(key), paletteTag.getInt(key));
                newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), replacedKeys.get(key));
            }else{
                if(key.contains("minecraft:mushroom_stem")){
                    newPaletteTag.putInt("strife3:bone_kelp_root", paletteTag.getInt(key));
                    newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), "strife3:bone_kelp_root");
                }else if(specificReplacedKeys.containsKey(key)){
                    newPaletteTag.putInt(specificReplacedKeys.get(key), paletteTag.getInt(key));
                    newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), specificReplacedKeys.get(key));
                }else {
                    newPaletteTag.putInt(key, paletteTag.getInt(key));
                    newPaletteTagReversed.putString(String.valueOf(paletteTag.getInt(key)), key);
                }
            }
        }



        byte[] blockData = schematic.getByteArray("BlockData");
        //this.volume = blockData.length;

        //Strife3.LOGGER.info(newPaletteTagReversed.toString());

        short length = schematic.getShort("Length");
        short width = schematic.getShort("Width");
        short height = schematic.getShort("Height");

        for(int k = 0; k < height; k++){
            ArrayList<ArrayList<BlockState>> yArrayO = new ArrayList<>();
            for(int j = 0; j < length; j++){
                ArrayList<BlockState> zArrayO = new ArrayList<>();
                for(int i = 0; i < width; i++){
                    //Strife3.LOGGER.info("WLH" + " " + width + " " + length + " " + height + " " + i + " " + j + " " + k + " " +String.valueOf((int) blockData[i + j*width + k*width*length]));
                    zArrayO.add(getBlockStateFromString(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get());
                    if(!getBlockStateFromString(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get().getBlock().equals(Blocks.WATER)){
                        this.volume++;
                    }
                    if(replacedKeys.containsKey(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))) || specificReplacedKeys.containsKey(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))) || paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length])).contains("minecraft:mushroom_stem")){
                        originalReplacement.add(new ReplacementBlock(i, k, j, getBlockStateFromString(newPaletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get(),getBlockStateFromString(paletteTagReversed.getString(String.valueOf((int) blockData[i + j*width + k*width*length]))).get()));
                    }
                }
                yArrayO.add(zArrayO);
            }
            originalSchematic.add(yArrayO);
        }

        this.createTransformations();

        //Strife3.LOGGER.info(originalSchematic.get(0).get(0).get(0).toString());
    }

    private static Optional<BlockState> getBlockStateFromString(String blockStateString) {
        // 1. Split the string into block ID and properties part.
        String[] parts = blockStateString.split("\\[");
        String blockIdString = parts[0];

        // 2. Get the Block object from the registry.
        Identifier blockId = Identifier.tryParse(blockIdString);
        if (blockId == null) {
            System.err.println("Invalid block ID: " + blockIdString);
            return Optional.empty();
        }

        Optional<Block> blockOptional = Registries.BLOCK.getOrEmpty(blockId);
        if (blockOptional.isEmpty()) {
            System.err.println("Block not found in registry: " + blockId);
            return Optional.empty();
        }
        Block block = blockOptional.get();

        // 3. Start with the default block state.
        BlockState blockState = block.getDefaultState();

        // 4. If properties are present, parse and apply them.
        if (parts.length > 1) {
            String propertiesString = parts[1].substring(0, parts[1].length() - 1); // Remove closing bracket
            String[] propertyPairs = propertiesString.split(",");

            StateManager<Block, BlockState> stateManager = block.getStateManager();

            for (String propertyPair : propertyPairs) {
                String[] keyValue = propertyPair.split("=");
                if (keyValue.length != 2) {
                    System.err.println("Invalid property format: " + propertyPair);
                    return Optional.empty();
                }

                String propertyName = keyValue[0];
                String propertyValueString = keyValue[1];

                Property<?> property = stateManager.getProperty(propertyName);
                if (property == null) {
                    System.err.println("Property not found: " + propertyName + " for block: " + blockId);
                    return Optional.empty();
                }

                blockState = withProperty(blockState, property, propertyValueString);
                if (blockState == null) {
                    return Optional.empty(); //Error already printed by withProperty()
                }
            }
        }
        return Optional.of(blockState);
    }

    private static <T extends Comparable<T>> BlockState withProperty(BlockState state, Property<T> property, String valueString) {
        Optional<T> optionalValue = property.parse(valueString);
        if (optionalValue.isPresent()) {
            return state.with(property, optionalValue.get());
        } else {
            Strife3.LOGGER.info(state.getBlock().toString());
            System.err.println("Invalid value: " + valueString + " for property: " + property.getName());
            return null;
        }
    }

    private void createTransformations(){
        this.mirroredSchematic = mirror(this.originalSchematic);

        this.NSchematic = rotateCCW(this.originalSchematic);
        this.WSchematic = rotateCCW(this.NSchematic);
        this.SSchematic = rotateCCW(this.WSchematic);

        this.MNSchematic = rotateCCW(this.mirroredSchematic);
        this.MWSchematic = rotateCCW(this.MNSchematic);
        this.MSSchematic = rotateCCW(this.MWSchematic);

        this.mirroredReplacement = mirror(this.originalReplacement, this.originalSchematic.getFirst().getFirst().size());

        this.NReplacement = rotateCCW(this.originalReplacement, this.originalSchematic.getFirst().getFirst().size());
        this.WReplacement = rotateCCW(this.NReplacement, this.NSchematic.getFirst().getFirst().size());
        this.SReplacement = rotateCCW(this.WReplacement, this.WSchematic.getFirst().getFirst().size());

        this.MNReplacement = rotateCCW(this.mirroredReplacement, this.mirroredSchematic.getFirst().getFirst().size());
        this.MWReplacement = rotateCCW(this.MNReplacement, this.MNSchematic.getFirst().getFirst().size());
        this.MSReplacement = rotateCCW(this.MWReplacement, this.MWSchematic.getFirst().getFirst().size());

        this.transformationPointer.add(originalSchematic);
        this.transformationPointer.add(NSchematic);
        this.transformationPointer.add(WSchematic);
        this.transformationPointer.add(SSchematic);

        this.transformationPointer.add(mirroredSchematic);
        this.transformationPointer.add(MNSchematic);
        this.transformationPointer.add(MWSchematic);
        this.transformationPointer.add(MSSchematic);

        this.transformationReplacementPointer.add(originalReplacement);
        this.transformationReplacementPointer.add(NReplacement);
        this.transformationReplacementPointer.add(WReplacement);
        this.transformationReplacementPointer.add(SReplacement);

        this.transformationReplacementPointer.add(mirroredReplacement);
        this.transformationReplacementPointer.add(MNReplacement);
        this.transformationReplacementPointer.add(MWReplacement);
        this.transformationReplacementPointer.add(MSReplacement);
    }

    public ArrayList<ArrayList<ArrayList<BlockState>>> getOriginalSchematic(){
        return originalSchematic;
    }

    public ArrayList<ReplacementBlock> getReplacement(){
        return originalReplacement;
    }

    private static ArrayList<ArrayList<ArrayList<BlockState>>> rotateCCW(ArrayList<ArrayList<ArrayList<BlockState>>> arrayList){
        ArrayList<ArrayList<ArrayList<BlockState>>> returnValue = new ArrayList<>();
        for(int k = 0; k < arrayList.size(); k++){
            returnValue.add(new ArrayList<>());
            for(int i = 0; i < arrayList.getFirst().getFirst().size(); i++){
                returnValue.get(k).add(new ArrayList<>());
                for(int j = 0; j < arrayList.getFirst().size(); j++){
                    returnValue.get(k).get(i).add(Blocks.PLAYER_HEAD.getDefaultState());
                }
            }
        }

        for(int k = 0; k < arrayList.size(); k++){
            for(int j = 0; j < arrayList.get(k).size(); j++){
                for(int i = 0; i < arrayList.get(k).get(j).size(); i++){
                    returnValue.get(k).get(-i+returnValue.getFirst().size()-1).set(j,arrayList.get(k).get(j).get(i).rotate(BlockRotation.COUNTERCLOCKWISE_90));
                }
            }
        }
        return returnValue;
    }

    private static ArrayList<ArrayList<ArrayList<BlockState>>> mirror(ArrayList<ArrayList<ArrayList<BlockState>>> arrayList){
        ArrayList<ArrayList<ArrayList<BlockState>>> returnValue = new ArrayList<>();
        for(int k = 0; k < arrayList.size(); k++){
            returnValue.add(new ArrayList<>());
            for(int j = 0; j < arrayList.getFirst().size(); j++){
                returnValue.get(k).add(new ArrayList<>());
                for(int i = 0; i < arrayList.getFirst().getFirst().size(); i++){
                    returnValue.get(k).get(j).add(Blocks.PLAYER_HEAD.getDefaultState());
                }
            }
        }

        for(int k = 0; k < arrayList.size(); k++){
            for(int j = 0; j < arrayList.get(k).size(); j++){
                for(int i = 0; i < arrayList.get(k).get(j).size(); i++){
                    returnValue.get(k).get(j).set(-i+arrayList.getFirst().getFirst().size()-1, arrayList.get(k).get(j).get(i).mirror(BlockMirror.FRONT_BACK));
                }
            }
        }
        return returnValue;
    }

    private ArrayList<ReplacementBlock> rotateCCW(ArrayList<ReplacementBlock> replacementList, int xSize){
        ArrayList<ReplacementBlock> returnValue = new ArrayList<>();
        for(ReplacementBlock replacementBlock : replacementList) {
            returnValue.add(new ReplacementBlock(replacementBlock.getOffsetZ(),replacementBlock.getOffsetY(),-replacementBlock.getOffsetX()+xSize-1,replacementBlock.getBlockState().rotate(BlockRotation.COUNTERCLOCKWISE_90),replacementBlock.getReplaced().rotate(BlockRotation.COUNTERCLOCKWISE_90)));
        }

        return returnValue;
    }

    private ArrayList<ReplacementBlock> mirror(ArrayList<ReplacementBlock> replacementList, int xSize){
        ArrayList<ReplacementBlock> returnValue = new ArrayList<>();
        for(ReplacementBlock replacementBlock : replacementList) {
            returnValue.add(new ReplacementBlock(-replacementBlock.getOffsetX()+xSize-1,replacementBlock.getOffsetY(),replacementBlock.getOffsetZ(),replacementBlock.getBlockState().mirror(BlockMirror.FRONT_BACK),replacementBlock.getReplaced().mirror(BlockMirror.FRONT_BACK)));
        }

        return returnValue;
    }

    public class ReplacementBlock{
        private int offsetX;
        private int offsetY;
        private int offsetZ;
        private BlockState block;
        private BlockState replaced;


        private ReplacementBlock(int x, int y, int z, BlockState blockState,BlockState replacedBlockState){
            this.offsetX = x;
            this.offsetY = y;
            this.offsetZ = z;
            this.block = blockState;
            this.replaced = replacedBlockState;
        }

        public int getOffsetX(){
            return this.offsetX;
        }

        public int getOffsetY(){
            return this.offsetY;
        }

        public int getOffsetZ(){
            return this.offsetZ;
        }

        public BlockState getBlockState(){
            return this.block;
        }

        public BlockState getReplaced(){
            return this.replaced;
        }

        public String toString(){
            return "{" + this.offsetX + "," + this.offsetY + "," + this.offsetZ + ":" + this.block + "}";
        }

        public BlockPos getBlockPos(){
            return new BlockPos(this.offsetX,this.offsetY,this.offsetZ);
        }
    }

    public SchematicSearchResult checkPositionAndSurroundings(BlockPos blockPos, ServerWorld world, ArrayList<ArrayList<ArrayList<SchematicReference.SchematicSearchResult>>> previousSchematicResults){
        int aX = blockPos.getX() - (Strife3.schematicReplacementStartX);
        int aY = blockPos.getY() - 1;
        int aZ = blockPos.getZ() - (Strife3.schematicReplacementStartZ);
        SchematicSearchResult currentBlockResult;
        if(aX == 0 && aY == 0 && aZ == 0){
            currentBlockResult = checkPosition(blockPos,world);
            previousSchematicResults.get(aZ).get(aX).add(currentBlockResult);
        }else{
            currentBlockResult = previousSchematicResults.get(aZ).get(aX).get(aY);
        }
        if(aZ >= 1){
            aZ = 2;
        }
        //Strife3.LOGGER.info(currentBlockResult.toString());
        //Strife3.LOGGER.info(currentBlockResult.toString());
        //Strife3.LOGGER.info("Found a schematic and now checking the surroundings");
        ArrayList<SchematicSearchResult> cubeCheck = new ArrayList<>();
        int startingX = -1;
        int endingX = 1;
        int startingY = -1;
        int endingY = 1;
        int startingZ = -1;
        int endingZ = 1;
        if (aX == 0) {
            startingX = 0;
        } else if (aX == (Strife3.schematicReplacementEndX - Strife3.schematicReplacementStartX + 1)) {
            endingX = 0;
        }
        if (aY == 0) {
            startingY = 0;
        } else if (aY == Strife3.schematicReplacementMaxY - 1) {
            endingY = 0;
        }
        if (aZ == 0) {
            startingZ = 0;
        } else if (aZ == (Strife3.schematicReplacementEndZ - Strife3.schematicReplacementStartZ + 1)) {
            endingZ = 0;
        }
        for (int i = startingX; i <= endingX; i++) {
            for (int j = startingY; j <= endingY; j++) {
                for (int k = startingZ; k <= endingZ; k++) {
                    //Strife3.LOGGER.info("Ax,Ay,Az,i,j,k: " + aX + "," + aY + "," + aZ + "," + i + "," + j + "," + k);
                    if (!(i == 0 && j == 0 && k == 0)) {
                        if (aZ == 0) {
                            if (i == -1 || k == -1 || j == -1) {
                                cubeCheck.add(previousSchematicResults.get(aZ + k).get(aX + i).get(aY + j));
                            } else {
                                SchematicSearchResult newPosition = checkPosition(blockPos.add(i, j, k), world);
                                cubeCheck.add(newPosition);
                                previousSchematicResults.get(aZ + k).get(aX + i).add(newPosition);
                            }
                        } else {
                            if (k != 1) {
                                cubeCheck.add(previousSchematicResults.get(aZ + k).get(aX + i).get(aY + j));
                            } else {
                                SchematicSearchResult newPosition = checkPosition(blockPos.add(i, j, k), world);
                                cubeCheck.add(newPosition);
                                previousSchematicResults.get(aZ + k).get(aX + i).add(newPosition);
                            }
                        }
                    }
                }
            }
        }
        if(currentBlockResult.result()) {

            double currentBlockPercentage = currentBlockResult.blockPercentage();
            for(SchematicSearchResult cubeResult : cubeCheck){
                //Strife3.LOGGER.info(cubeResult.toString());
                if(cubeResult.result()) {
                    if (cubeResult.blockPercentage() < currentBlockPercentage) {
                        //Strife3.LOGGER.info("Block was found that was better");
                        return schematicSearchResult.failure(this);
                    }
                }
            }
            return currentBlockResult;
        }else{
            return schematicSearchResult.failure(this);
        }

        /*boolean switchYZ = false;

        for(ReplacementBlock replacementBlock : replacement){
            if(!world.getBlockState(replacementBlock.getBlockPos()).equals(replacementBlock.replaced)){
                switchYZ = true;
            }
        }

        if(switchYZ){
            Strife3.LOGGER.info("Had to switch Y and Z coords");
        }*/
    }

    private SchematicSearchResult checkPosition(BlockPos blockPos, ServerWorld world) {
        if (this.name != null) {
            //Strife3.LOGGER.info("Trying: " + this.name);
        }

        //Strife3.LOGGER.info(blockPos.toString());
        int maximumError = (int) (this.volume * 0.75);
        int nonMatchingBlocks = 0;
        boolean foundMatch = false;
        int transformation = 0;
        double[] bestTransformation = new double[8];

        while (transformation < 8) {
            nonMatchingBlocks = 0;
            current:
            for (int k = 0; k < this.transformationPointer.get(transformation).size(); k++) {
                for (int j = 0; j < this.transformationPointer.get(transformation).get(0).size(); j++) {
                    for (int i = 0; i < this.transformationPointer.get(transformation).get(0).get(0).size(); i++) {
                        if (!this.transformationPointer.get(transformation).get(k).get(j).get(i).getBlock().equals(Blocks.WATER)) {
                            if (!world.getBlockState(blockPos.add(i, k, j)).equals(this.transformationPointer.get(transformation).get(k).get(j).get(i))) {
                                /*if (debug) {
                                    Strife3.LOGGER.info("Was looking for: " + this.transformationPointer.get(transformation).get(k).get(j).get(i) + " Found: " + world.getBlockState(blockPos.add(i, k, j)));
                                }*/
                                if(connectingBlocks.contains(this.transformationPointer.get(transformation).get(k).get(j).get(i).getBlock())){
                                    if(!this.transformationPointer.get(transformation).get(k).get(j).get(i).getBlock().equals(world.getBlockState(blockPos.add(i, k, j)).getBlock())){
                                        nonMatchingBlocks++;
                                        if (nonMatchingBlocks > maximumError) {
                                            break current;
                                        }
                                    }
                                }else {
                                    nonMatchingBlocks++;
                                    if (nonMatchingBlocks > maximumError) {
                                        break current;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            bestTransformation[transformation] = (double) nonMatchingBlocks /volume;
            transformation++;
        }

        double fitTransformation = bestTransformation[0];
        //Strife3.LOGGER.info("Schematic Confidence:");
        //Strife3.LOGGER.info("0: " + bestTransformation[0]);
        int fittestTransformation = 0;
        for(int i = 1; i < 8; i++){
            //Strife3.LOGGER.info(i + ": " + bestTransformation[i]);
            if(bestTransformation[i] < fitTransformation){
                fitTransformation = bestTransformation[i];
                fittestTransformation = i;
            }
        }
        if (fitTransformation > 0.75) {
            return schematicSearchResult.failure(this);
        }

        //Strife3.LOGGER.info("Found a matching schematic");
        return schematicSearchResult.success(fitTransformation,fittestTransformation,this);
    }

    public void replacePosition(BlockPos blockPos, ServerWorld world, int transformation){
        //Strife3.LOGGER.info("Found a match: " + transformation + " " + volume + " blocks");
        for(ReplacementBlock replacementBlock : this.transformationReplacementPointer.get(transformation)){
            //Strife3.LOGGER.info(replacementBlock.toString());
            //if(switchYZ) {
            //world.setBlockState(blockPos.add(replacementBlock.getOffsetX(), replacementBlock.getOffsetZ(), replacementBlock.getOffsetY()), replacementBlock.getBlockState());
            //}else{
            if(world.getBlockState(blockPos.add(replacementBlock.getOffsetX(), replacementBlock.getOffsetY(), replacementBlock.getOffsetZ())).equals(replacementBlock.getReplaced())) {
                world.setBlockState(blockPos.add(replacementBlock.getOffsetX(), replacementBlock.getOffsetY(), replacementBlock.getOffsetZ()), replacementBlock.getBlockState());
            }else{
                if(connectingBlocks.contains(world.getBlockState(blockPos.add(replacementBlock.getOffsetX(), replacementBlock.getOffsetY(), replacementBlock.getOffsetZ())).getBlock())){
                    if(world.getBlockState(blockPos.add(replacementBlock.getOffsetX(), replacementBlock.getOffsetY(), replacementBlock.getOffsetZ())).getBlock().equals(replacementBlock.getReplaced().getBlock())){
                        world.setBlockState(blockPos.add(replacementBlock.getOffsetX(), replacementBlock.getOffsetY(), replacementBlock.getOffsetZ()), replacementBlock.getBlockState());
                    }
                }
            }
            //}
        }
    }

    public class SchematicSearchResult{
        private boolean result;
        private double matchingPercentage;
        private int transformation;
        private SchematicReference schematic;

        private SchematicSearchResult(boolean success, double percentage, int transformation, SchematicReference schematic){
            this.result = success;
            this.matchingPercentage = percentage;
            this.transformation = transformation;
            this.schematic = schematic;
        }

        public SchematicSearchResult(){

        }

        public SchematicSearchResult failure(SchematicReference schematic){
            return new SchematicSearchResult(false, 0,0,schematic);
        }

        public SchematicSearchResult success(double percentage, int transformation, SchematicReference schematic){
            return new SchematicSearchResult(true, percentage, transformation, schematic);
        }

        public boolean result(){
            return this.result;
        }

        public double blockPercentage(){
            return this.matchingPercentage;
        }

        public int transformation(){
            return this.transformation;
        }

        public SchematicReference schematic(){
            return this.schematic;
        }

        public String toString(){
            if (this.schematic().name == null){
                return "Sch: null R: " + this.result() + " P: " + this.blockPercentage() + " T: " + this.transformation();
            }else {
                return "Sch: " + this.schematic().name + " R: " + this.result() + " P: " + this.blockPercentage() + " T: " + this.transformation();
            }
        }
    }

    public void debugTransformations(BlockPos blockPos, ServerWorld world){
        for(int k = 0; k < originalSchematic.size(); k++){
            for(int j = 0; j < originalSchematic.getFirst().size(); j++){
                for(int i = 0; i < originalSchematic.getFirst().getFirst().size(); i++){
                    world.setBlockState(blockPos.add(i,k,j),originalSchematic.get(k).get(j).get(i));
                }
            }
        }

        for(int k = 0; k < NSchematic.size(); k++){
            for(int j = 0; j < NSchematic.getFirst().size(); j++){
                for(int i = 0; i < NSchematic.getFirst().getFirst().size(); i++){
                    world.setBlockState(blockPos.add(5,0,0).add(i,k,j).add(originalSchematic.getFirst().getFirst().size(),0,0),NSchematic.get(k).get(j).get(i));
                }
            }
        }

        for(int k = 0; k < WSchematic.size(); k++){
            for(int j = 0; j < WSchematic.getFirst().size(); j++){
                for(int i = 0; i < WSchematic.getFirst().getFirst().size(); i++){
                    world.setBlockState(blockPos.add(5,0,0).add(5,0,0).add(i,k,j).add(originalSchematic.getFirst().getFirst().size(),0,0).add(NSchematic.getFirst().getFirst().size(),0,0),WSchematic.get(k).get(j).get(i));
                }
            }
        }

        for(int k = 0; k < SSchematic.size(); k++){
            for(int j = 0; j < SSchematic.getFirst().size(); j++){
                for(int i = 0; i < SSchematic.getFirst().getFirst().size(); i++){
                    world.setBlockState(blockPos.add(5,0,0).add(5,0,0).add(5,0,0).add(i,k,j).add(originalSchematic.getFirst().getFirst().size(),0,0).add(NSchematic.getFirst().getFirst().size(),0,0).add(WSchematic.getFirst().getFirst().size(),0,0),SSchematic.get(k).get(j).get(i));
                }
            }
        }

        for(int k = 0; k < mirroredSchematic.size(); k++){
            for(int j = 0; j < mirroredSchematic.getFirst().size(); j++){
                for(int i = 0; i < mirroredSchematic.getFirst().getFirst().size(); i++){
                    world.setBlockState(blockPos.add(i,k,j+Math.max(originalSchematic.getFirst().size(),originalSchematic.getFirst().getFirst().size())+5),mirroredSchematic.get(k).get(j).get(i));
                }
            }
        }

        for(int k = 0; k < MNSchematic.size(); k++){
            for(int j = 0; j < MNSchematic.getFirst().size(); j++){
                for(int i = 0; i < MNSchematic.getFirst().getFirst().size(); i++){
                    world.setBlockState(blockPos.add(5,0,0).add(i,k,j+Math.max(originalSchematic.getFirst().size(),originalSchematic.getFirst().getFirst().size())+5).add(mirroredSchematic.getFirst().getFirst().size(),0,0),MNSchematic.get(k).get(j).get(i));
                }
            }
        }

        for(int k = 0; k < MWSchematic.size(); k++){
            for(int j = 0; j < MWSchematic.getFirst().size(); j++){
                for(int i = 0; i < MWSchematic.getFirst().getFirst().size(); i++){
                    world.setBlockState(blockPos.add(5,0,0).add(5,0,0).add(i,k,j+Math.max(originalSchematic.getFirst().size(),originalSchematic.getFirst().getFirst().size())+5).add(mirroredSchematic.getFirst().getFirst().size(),0,0).add(MNSchematic.getFirst().getFirst().size(),0,0),MWSchematic.get(k).get(j).get(i));
                }
            }
        }

        for(int k = 0; k < MSSchematic.size(); k++){
            for(int j = 0; j < MSSchematic.getFirst().size(); j++){
                for(int i = 0; i < MSSchematic.getFirst().getFirst().size(); i++){
                    world.setBlockState(blockPos.add(5,0,0).add(5,0,0).add(5,0,0).add(i,k,j+Math.max(originalSchematic.getFirst().size(),originalSchematic.getFirst().getFirst().size())+5).add(mirroredSchematic.getFirst().getFirst().size(),0,0).add(MNSchematic.getFirst().getFirst().size(),0,0).add(MWSchematic.getFirst().getFirst().size(),0,0),MSSchematic.get(k).get(j).get(i));
                }
            }
        }

        for(ReplacementBlock replacementBlock : originalReplacement){
            world.setBlockState(blockPos.add(0,originalSchematic.size()+5,0).add(replacementBlock.getOffsetX(),replacementBlock.getOffsetY(),replacementBlock.getOffsetZ()),replacementBlock.getBlockState());
        }

        for(ReplacementBlock replacementBlock : NReplacement){
            world.setBlockState(blockPos.add(5,0,0).add(0,originalSchematic.size()+5,0).add(originalSchematic.getFirst().getFirst().size(),0,0).add(replacementBlock.getOffsetX(),replacementBlock.getOffsetY(),replacementBlock.getOffsetZ()),replacementBlock.getBlockState());
        }

        for(ReplacementBlock replacementBlock : WReplacement){
            world.setBlockState(blockPos.add(5,0,0).add(5,0,0).add(0,originalSchematic.size()+5,0).add(originalSchematic.getFirst().getFirst().size(),0,0).add(NSchematic.getFirst().getFirst().size(),0,0).add(replacementBlock.getOffsetX(),replacementBlock.getOffsetY(),replacementBlock.getOffsetZ()),replacementBlock.getBlockState());
        }

        for(ReplacementBlock replacementBlock : SReplacement){
            world.setBlockState(blockPos.add(5,0,0).add(5,0,0).add(5,0,0).add(0,originalSchematic.size()+5,0).add(originalSchematic.getFirst().getFirst().size(),0,0).add(NSchematic.getFirst().getFirst().size(),0,0).add(WSchematic.getFirst().getFirst().size(),0,0).add(replacementBlock.getOffsetX(),replacementBlock.getOffsetY(),replacementBlock.getOffsetZ()),replacementBlock.getBlockState());
        }

        for(ReplacementBlock replacementBlock : mirroredReplacement){
            world.setBlockState(blockPos.add(0,mirroredSchematic.size()+5,0).add(0,0,Math.max(mirroredSchematic.getFirst().size(),mirroredSchematic.getFirst().getFirst().size())+5).add(replacementBlock.getOffsetX(),replacementBlock.getOffsetY(),replacementBlock.getOffsetZ()),replacementBlock.getBlockState());
        }

        for(ReplacementBlock replacementBlock : MNReplacement){
            world.setBlockState(blockPos.add(5,0,0).add(0,mirroredSchematic.size()+5,0).add(0,0,Math.max(mirroredSchematic.getFirst().size(),mirroredSchematic.getFirst().getFirst().size())+5).add(mirroredSchematic.getFirst().getFirst().size(),0,0).add(replacementBlock.getOffsetX(),replacementBlock.getOffsetY(),replacementBlock.getOffsetZ()),replacementBlock.getBlockState());
        }

        for(ReplacementBlock replacementBlock : MWReplacement){
            world.setBlockState(blockPos.add(5,0,0).add(5,0,0).add(0,mirroredSchematic.size()+5,0).add(0,0,Math.max(mirroredSchematic.getFirst().size(),mirroredSchematic.getFirst().getFirst().size())+5).add(mirroredSchematic.getFirst().getFirst().size(),0,0).add(MNSchematic.getFirst().getFirst().size(),0,0).add(replacementBlock.getOffsetX(),replacementBlock.getOffsetY(),replacementBlock.getOffsetZ()),replacementBlock.getBlockState());
        }

        for(ReplacementBlock replacementBlock : MSReplacement){
            world.setBlockState(blockPos.add(5,0,0).add(5,0,0).add(5,0,0).add(0,mirroredSchematic.size()+5,0).add(0,0,Math.max(mirroredSchematic.getFirst().size(),mirroredSchematic.getFirst().getFirst().size())+5).add(mirroredSchematic.getFirst().getFirst().size(),0,0).add(MNSchematic.getFirst().getFirst().size(),0,0).add(MWSchematic.getFirst().getFirst().size(),0,0).add(replacementBlock.getOffsetX(),replacementBlock.getOffsetY(),replacementBlock.getOffsetZ()),replacementBlock.getBlockState());
        }
    }
}