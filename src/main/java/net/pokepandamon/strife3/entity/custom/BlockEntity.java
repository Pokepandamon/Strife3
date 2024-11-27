package net.pokepandamon.strife3.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pokepandamon.strife3.Strife3;

public class BlockEntity extends Entity {
    private BlockState block;
    private int interpolationTicksPassed;
    private int totalInterpolationTicks;
    private Vec3d initialLocation;
    private Vec3d finalLocation;

    public BlockEntity(EntityType<? extends BlockEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public void tick() {
        //super.setBoundingBox(super.getBoundingBox().offset(new Vec3d(0.5, 0,0.5)));
        if(this.block == null){
            this.block = this.getWorld().getBlockState(this.getBlockPos());
            if(this.block.equals(Blocks.AIR.getDefaultState()) || this.block.equals(Blocks.WATER.getDefaultState())){
                this.kill();
            }
        }

        if(totalInterpolationTicks > 0){
            if(interpolationTicksPassed == totalInterpolationTicks + 2){
                totalInterpolationTicks = 0;
                interpolationTicksPassed = 0;
                this.getWorld().setBlockState(this.getBlockPos(), this.block);
                this.kill();
            }else{
                if(interpolationTicksPassed == 0){
                    this.getWorld().setBlockState(this.getBlockPos(), Blocks.AIR.getDefaultState());
                }
                interpolationTicksPassed++;
                /*double i = ((double) interpolationTicksPassed) / ((double) totalInterpolationTicks);

                double x = this.initialLocation.x + (this.finalLocation.x - this.initialLocation.x) * i;
                double y = this.initialLocation.y + (this.finalLocation.y - this.initialLocation.y) * i;
                double z = this.initialLocation.z + (this.finalLocation.z - this.initialLocation.z) * i;

                //Strife3.LOGGER.info(new Vec3d(x,y,z).toString() + " " + i + " " + (this.finalLocation.y - this.initialLocation.y) + " " + (this.finalLocation.y - this.initialLocation.y) * i);
                //this.setPosition(new Vec3d(x, y, z));*/

                //this.setPosition(this.initialLocation.lerp(this.finalLocation, i));
                this.setVelocity(new Vec3d((this.finalLocation.getX() - this.initialLocation.getX()) * (1.0/((double) totalInterpolationTicks)),(this.finalLocation.getY() - this.initialLocation.getY()) * (1.0/((double) totalInterpolationTicks)),(this.finalLocation.getZ() - this.initialLocation.getZ()) * (1.0/((double) totalInterpolationTicks))));
                /*this.velocityModified = true;
                this.velocityDirty = true;*/
                this.move(MovementType.SELF, this.getVelocity());
                //Strife3.LOGGER.info("YV: " + this.getVelocity().getY() + " | " + (this.finalLocation.getY() - this.initialLocation.getY()) + " | " + (1.0/((double) totalInterpolationTicks)) + " | " + (this.finalLocation.getY() - this.initialLocation.getY()) * (1.0/((double) totalInterpolationTicks)));
            }
        }
        // Your entity's logic goes here (movement, interaction, etc.)
        /*Strife3.LOGGER.info("I was here I swear... " + this.block);
        Strife3.LOGGER.info(this.getBlockPos().toString());*/
        super.tick();
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        // Load custom data from NBT
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        // Save custom data to NBT
    }

    public BlockState getBlockState() {
        return this.block;
    }

    public void startInterpolation(Vec3d initialLocation, Vec3d finalLocation, int ticks){
        this.initialLocation = initialLocation;
        this.finalLocation = finalLocation;
        this.totalInterpolationTicks = ticks;
    }

    public boolean isCollidable() {
        return true;
    }

    @Override
    public Box calculateBoundingBox() {
        return super.calculateBoundingBox().offset(new Vec3d(0.5, 0, 0.5));
    }

    public boolean interpolating(){
        return totalInterpolationTicks > 0;
    }
}
