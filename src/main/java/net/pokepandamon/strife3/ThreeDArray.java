package net.pokepandamon.strife3;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.pokepandamon.strife3.mixin.MixinServerPlayerEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class ThreeDArray {
    private ThreeDNode head;
    private ThreeDArray tail;
    private ArrayList<ArrayList<ArrayList<ThreeDNode>>> references = new ArrayList<>(MixinServerPlayerEntity.schematicReplacementEndZ - MixinServerPlayerEntity.schematicReplacementStartZ + 1);

    public ThreeDArray(){
        for(int i = 0; i < MixinServerPlayerEntity.schematicReplacementEndZ - MixinServerPlayerEntity.schematicReplacementStartZ + 1; i++){
            ArrayList<ArrayList<ThreeDNode>> currentRow = new ArrayList<>();
            for(int j = 0; j < MixinServerPlayerEntity.schematicReplacementEndX - MixinServerPlayerEntity.schematicReplacementStartX + 1; j++){
                currentRow.add(new ArrayList<>());
            }
            references.add(currentRow);
        }
    }

    public void add(int chunkX, int chunkZ, int y, SchematicReference.SchematicSearchResult searchResult, BlockPos pos){
        int aChunkX = chunkX - MixinServerPlayerEntity.schematicReplacementStartX;
        int aChunkZ = chunkZ - MixinServerPlayerEntity.schematicReplacementStartZ;
        if(aChunkX == 0 && aChunkZ == 0 && y == 1 && ChunkSectionPos.getSectionCoord(pos.getX()) == 0 && ChunkSectionPos.getSectionCoord(pos.getZ()) == 0){
            this.head = new ThreeDNode(searchResult, pos);
            references.get(chunkZ).get(chunkX).add(this.head);
            ArrayList<Object> newExterior = new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null,null,null,null,this.head,pos.offset(Direction.Axis.X, 1),null,pos.offset(Direction.Axis.Z,1),pos.add(1,0,1),null,null,null,null,pos.add(0,1,0),pos.add(1,1,0),null,pos.add(0,1,1),pos.add(1,1,1)));
            this.head.exterior = newExterior;
        }else{
            ThreeDNode newOne = new ThreeDNode(searchResult, pos);
            ThreeDNode reference;
            ArrayList<Object> newExterior;
            if(pos.getY() == 1){
                if(aChunkX == 0 && ChunkSectionPos.getSectionCoord(pos.getX()) == 0 && ChunkSectionPos.getSectionCoord(pos.getZ()) == 0){
                    reference = get(chunkX, chunkZ, pos.offset(Direction.Axis.Z, -1));
                    newExterior = new ArrayList<>(Arrays.asList(
                            null,null,null,
                            null,null,null,
                            null,null,null,

                            null,reference,reference.exterior.get(14),
                            null,newOne,reference.exterior.get(17),
                            null,pos.add(0,0,1),pos.add(1,0,1),

                            null,reference.exterior.get(22),reference.exterior.get(23),
                            null,pos.add(0,1,0),pos.add(1,1,0),
                            null,pos.add(0,1,1),pos.add(1,1,1)));
                }else {
                    reference = get(chunkX, chunkZ, pos.offset(Direction.Axis.X, -1));
                    newExterior = new ArrayList<>(Arrays.asList(
                            null,null,null,
                            null,null,null,
                            null,null,null,

                            reference.exterior.get(12),reference,reference.exterior.get(14),
                            reference.exterior.get(15),newOne,reference.exterior.get(17),
                            pos.add(-1,0,0),pos.add(0,0,1),pos.add(1,0,1),

                            reference.exterior.get(21),reference.exterior.get(22),reference.exterior.get(23),
                            reference.exterior.get(24),pos.add(0,1,0),pos.add(1,1,0),
                            pos.add(-1,1,1),pos.add(0,1,1),pos.add(1,1,1)));
                }
            }else{
                reference = get(chunkX, chunkZ, pos.offset(Direction.Axis.Y, -1));
                newExterior = new ArrayList<>(Arrays.asList(
                        null,null,null,
                        null,null,null,
                        null,null,null,

                        reference.exterior.get(12),reference,reference.exterior.get(14),
                        reference.exterior.get(15),newOne,reference.exterior.get(17),
                        pos.add(-1,0,0),pos.add(0,0,1),pos.add(1,0,1),

                        reference.exterior.get(21),reference.exterior.get(22),reference.exterior.get(23),
                        reference.exterior.get(24),pos.add(0,1,0),pos.add(1,1,0),
                        pos.add(-1,1,1),pos.add(0,1,1),pos.add(1,1,1)));
            }

            if(pos.getX() % 16 == 0 && pos.getZ() % 16 == 0){
                if(pos.getY() == 1 || pos.getY() % 64 == 0){
                    references.get(chunkZ).get(chunkX).add(newOne);
                }
            }
        }
    }

    public ThreeDNode get(int chunkX, int chunkZ, BlockPos pos){
        ThreeDNode start = closestReferenceDuringAdding(chunkX, chunkZ, pos);
        ThreeDNode destination = start;
        int xDifference = pos.getX() - start.blockPos.getX();
        int yDifference = pos.getY() - start.blockPos.getY();
        int zDifference = pos.getZ() - start.blockPos.getZ();
        while(xDifference != 0 || yDifference != 0 || zDifference != 0){
            int xTransform = ((xDifference > 0) ? 1 : 0) - ((xDifference < 0) ? 1 : 0);
            int yTransform = ((yDifference > 0) ? 1 : 0) - ((yDifference < 0) ? 1 : 0);
            int zTransform = ((zDifference > 0) ? 1 : 0) - ((zDifference < 0) ? 1 : 0);
            destination = (ThreeDNode) destination.exterior.get((xTransform+1) + (zTransform+1)*3 + (yTransform+1)*9);
            xDifference -= xTransform;
            yDifference -= yTransform;
            zDifference -= zTransform;
        }
        return destination;
    }

    private ThreeDNode closestReferenceDuringAdding(int chunkX, int chunkZ, BlockPos pos){
        if(references.get(chunkZ).get(chunkX).isEmpty()){
            if(chunkX == 0){
                return references.get(chunkZ - 1).get(chunkX).get((int)(((double)pos.getY() / 64.0) + 0.5));
            }else{
                return references.get(chunkZ).get(chunkX - 1).get((int)(((double)pos.getY() / 64.0) + 0.5));
            }
        }else{
            if((int)(((double)pos.getY() / 64.0) + 0.5) >= references.get(chunkZ).get(chunkX).size()){
                return references.get(chunkZ).get(chunkX - 1).getLast();
            }else{
                return references.get(chunkZ).get(chunkX).get((int)(((double)pos.getY() / 64.0) + 0.5));
            }
        }
    }

    private class ThreeDNode {
        private SchematicReference.SchematicSearchResult result;
        private BlockPos blockPos;
        private ArrayList<Object> exterior = new ArrayList<>(27);

        private ThreeDNode(SchematicReference.SchematicSearchResult searchResult, BlockPos pos){
            this.result = searchResult;
            this.blockPos = pos;
        }
        private void setExterior(ArrayList<Object> newExterior){
            this.exterior = newExterior;
        }
    }
}
