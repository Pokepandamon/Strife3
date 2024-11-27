package net.pokepandamon.strife3;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pokepandamon.strife3.entity.custom.BlockEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Strife3Doors {
    private int width;
    private int height;
    private int depth;
    private ArrayList<ArrayList<ArrayList<BlockState>>> blockArray;
    private Area doorStart;
    private Area doorEnd;

    private World world;

    private String id;

    public Strife3Doors(Area doorStart, Area doorEnd, World world, String identifier){
        this.width = doorStart.width();
        this.height = doorStart.height();
        this.depth = doorStart.depth();

        this.doorStart = doorStart;
        this.doorEnd = doorEnd;

        this.blockArray = doorStart.blockStateArray(world);

        this.world = world;

        this.id = identifier;
    }

    public Strife3Doors(Area doorStart, Area doorEnd, ArrayList<ArrayList<ArrayList<BlockState>>> blockStateArray){
        this.width = doorStart.width();
        this.height = doorStart.height();
        this.depth = doorStart.depth();

        this.doorStart = doorStart;
        this.doorEnd = doorEnd;

        this.blockArray = blockStateArray;
    }

    public void open(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                for(int k = 0; k < this.depth; k++){
                    int finalI = i;
                    int finalJ = j;
                    int finalK = k;
                    //Entity entity = EntityType.loadEntityWithPassengers(new NbtCompound(), this.world, (entityx) -> {
                    //    entityx.refreshPositionAndAngles(doorStart.getX1() + finalI, doorStart.getY1()+ finalJ, doorStart.getZ1()+ finalK, entityx.getYaw(), entityx.getPitch());
                    //    return entityx;
                    //});
                    BlockEntity customEntity = ((EntityType<BlockEntity>) EntityType.get("strife3:block_entity").get()).create(world);
                    customEntity.updatePosition(doorStart.getX1() + finalI, doorStart.getY1()+ finalJ, doorStart.getZ1()+ finalK);
                    world.spawnEntity(customEntity);
                    customEntity.startInterpolation(new Vec3d(doorStart.getX1() + finalI, doorStart.getY1()+ finalJ, doorStart.getZ1()+ finalK), new Vec3d(doorEnd.getX1() + finalI, doorEnd.getY1()+ finalJ, doorEnd.getZ1()+ finalK), (int) (Math.sqrt(Math.pow((doorStart.getX1() - doorEnd.getX1()),2) + Math.pow((doorStart.getY1() - doorEnd.getY1()),2) + Math.pow((doorStart.getZ1() - doorEnd.getZ1()),2))*40));
                    //world.spawnEntity(entity);
                }
            }
        }
    }

    public String getBlockStateArray(){
        String result = "";
        for(ArrayList<ArrayList<BlockState>> xArray : this.blockArray){
            for(ArrayList<BlockState> yArray : xArray){
                for(BlockState zArray : yArray){
                    result = result + zArray + " | ";
                }
                result = result + "\n";
            }
            result = result + "\n\n";
        }
        return result;
    }

    public String getIdentifier(){
        return this.id;
    }
}
