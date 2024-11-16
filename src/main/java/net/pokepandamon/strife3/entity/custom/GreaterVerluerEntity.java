package net.pokepandamon.strife3.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class GreaterVerluerEntity extends DrownedEntity {
    //protected final SwimNavigation waterNavigation;
    //protected final MobNavigation landNavigation;
    private int dashCooldown = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public GreaterVerluerEntity(EntityType<? extends DrownedEntity> entityType, World world) {
        super(entityType, world);
        //this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        //this.moveControl = new AquaticMoveControl(this, 85, 10, 1.5F, 1.0F, false);
    }

    private void updateAnimationState(){
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        }else {
            this.idleAnimationTimeout--;
        }
    }

    @Override
    public void tick(){
        super.tick();
        if(this.getWorld().isClient){
            updateAnimationState();
        }else {
            if (dashCooldown > 0) {
                dashCooldown--;
            }
        }
    }

    @Override
    protected void initGoals(){
        super.initGoals();
        //this.goalSelector.add(1, new DashAttackGoal(this, 1.0));
        //this.goalSelector.add(2, new FollowPlayerGoal(this, 1.2)); // Custom goal to follow player

    }

    public static DefaultAttributeContainer.Builder createGreaterVerluer(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_ARMOR, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4);
    }

    @Override
    protected int getNextAirUnderwater(int air) {
        return air;
    }

    /*private void dashAttack(LivingEntity target){
        // Calculate direction towards the target
        Vec3d direction = new Vec3d(
                target.getX() - this.getX(),
                target.getY() - this.getY(),
                target.getZ() - this.getZ()
        ).normalize();

        // Apply a dash movement
        this.setVelocity(direction.multiply(1.5)); // Adjust speed as needed
        this.velocityModified = true;
        this.dashCooldown = 100;
    }*/

   /* public static class DashAttackGoal extends Goal {
        private final GreaterVerluerEntity entity;
        private final double speed;

        public DashAttackGoal(GreaterVerluerEntity entity, double speed) {
            this.entity = entity;
            this.speed = speed;
        }

        @Override
        public boolean canStart() {
            if(entity.dashCooldown == 0){
                return false;
            }
            LivingEntity target = entity.getTarget();
            return target != null && entity.distanceTo(target) < 10.0D; // Adjust range as needed
        }

        @Override
        public void start() {
            entity.dashAttack(entity.getTarget());
        }
    }*/

    /*public void updateSwimming() {
        if (!this.getWorld().isClient) {
            if (this.canMoveVoluntarily() && this.isTouchingWater() && this.isTargetingUnderwater()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                this.navigation = this.landNavigation;
                this.setSwimming(false);
            }
        }

    }*/

    /*boolean isTargetingUnderwater() {
        if (this.targetingUnderwater) {
            return true;
        } else {
            LivingEntity livingEntity = this.getTarget();
            return livingEntity != null && livingEntity.isTouchingWater();
        }
    }*/
}
