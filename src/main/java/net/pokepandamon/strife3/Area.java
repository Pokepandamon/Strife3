package net.pokepandamon.strife3;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class Area {
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;

    public Area(int x1,int y1,int z1,int x2,int y2,int z2){
        if(x1 < x2){
            this.x1 = x1;
            this.x2 = x2;
        }else{
            this.x1 = x2;
            this.x2 = x1;
        }
        if(y1 < y2){
            this.y1 = y1;
            this.y2 = y2;
        }else{
            this.y1 = y2;
            this.y2 = y1;
        }
        if(z1 < z2){
            this.z1 = z1;
            this.z2 = z2;
        }else{
            this.z1 = z2;
            this.z2 = z1;
        }
    }

    public Area(BlockPos startPos, BlockPos endPos){
        int x1 = startPos.getX();
        int y1 = startPos.getY();
        int z1 = startPos.getZ();
        int x2 = endPos.getX();
        int y2 = endPos.getY();
        int z2 = endPos.getZ();

        if(x1 < x2){
            this.x1 = x1;
            this.x2 = x2;
        }else{
            this.x1 = x2;
            this.x2 = x1;
        }
        if(y1 < y2){
            this.y1 = y1;
            this.y2 = y2;
        }else{
            this.y1 = y2;
            this.y2 = y1;
        }
        if(z1 < z2){
            this.z1 = z1;
            this.z2 = z2;
        }else{
            this.z1 = z2;
            this.z2 = z1;
        }
    }

    public boolean inArea(PlayerEntity player){
        return (((Double) player.getX()).intValue() >= x1 && ((Double) player.getX()).intValue() <= x2) && (((Double) player.getY()).intValue() >= y1 && ((Double) player.getY()).intValue() <= y2) && (((Double) player.getZ()).intValue() >= z1 && ((Double) player.getZ()).intValue() <= z2);
    }

    public boolean inArea(BlockPos pos){
        return (pos.getX() >= x1 && pos.getX() <= x2) && (pos.getY() >= y1 && pos.getY() <= y2) && (pos.getZ() >= z1 && pos.getZ() <= z2);
    }

    public boolean inArea(int x, int y, int z){
        return (x >= x1 && x <= x2) && (y >= y1 && y <= y2) && (z >= z1 && z <= z2);
    }

    public boolean inArea(Entity entity){
        return (((Double) entity.getX()).intValue() >= x1 && ((Double) entity.getX()).intValue() <= x2) && (((Double) entity.getY()).intValue() >= y1 && ((Double) entity.getY()).intValue() <= y2) && (((Double) entity.getZ()).intValue() >= z1 && ((Double) entity.getZ()).intValue() <= z2);
    }

    public int width(){
        return this.x2 - this.x1 + 1;
    }

    public int height(){
        return this.y2 - this.y1 + 1;
    }

    public int depth(){
        return this.z2 - this.z1 + 1;
    }

    public ArrayList<ArrayList<ArrayList<BlockState>>> blockStateArray(World world){
        ArrayList<ArrayList<ArrayList<BlockState>>> returnValue = new ArrayList<>();

        for(int i = 0; i < this.width(); i++){
            ArrayList<ArrayList<BlockState>> xArray = new ArrayList<>();
            for(int j = 0; j < this.height(); j++){
                ArrayList<BlockState> yArray = new ArrayList<>();
                for(int k = 0; k < this.depth(); k++){
                    yArray.add(world.getBlockState(new BlockPos(i, j, k)));
                }
                xArray.add(yArray);
            }
            returnValue.add(xArray);
        }

        return returnValue;
    }

    public int getX1(){
        return this.x1;
    }

    public int getY1(){
        return this.y1;
    }

    public int getZ1(){
        return this.z1;
    }

    public int getX2(){
        return this.x2;
    }

    public int getY2(){
        return this.y2;
    }

    public int getZ2(){
        return this.z2;
    }
}
