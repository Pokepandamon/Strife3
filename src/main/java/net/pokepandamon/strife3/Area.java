package net.pokepandamon.strife3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

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
}
