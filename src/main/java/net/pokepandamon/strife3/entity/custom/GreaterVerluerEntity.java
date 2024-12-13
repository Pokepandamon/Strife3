package net.pokepandamon.strife3.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pokepandamon.strife3.Strife3;

import java.util.EnumSet;

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
            Strife3.LOGGER.info("" + dashCooldown);
        }
    }

    @Override
    protected void initGoals(){
        super.initGoals();
        this.goalSelector.add(1, new DashAttackGoal(this));
        //this.goalSelector.add(2, new FollowPlayerGoal(this, 1.2)); // Custom goal to follow player

    }

    public static DefaultAttributeContainer.Builder createGreaterVerluer(){
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_ARMOR, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, (double) 100.0F)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, (double) 1.1F)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
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
        Strife3.LOGGER.info("DASH!");
    }*/

    public void useDash(){
        this.dashCooldown = 1000;
        this.noClip = true;
    }

    public boolean onDashCooldown(){
        return this.dashCooldown > 0;
    }

/*   public static class DashAttackGoal extends Goal {
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

    public class DashAttackGoal extends Goal {
        private final GreaterVerluerEntity entity;
        private LivingEntity target;
        private boolean dashed = false;
        //private int cooldown;

        public DashAttackGoal(GreaterVerluerEntity entity) {
            this.entity = entity;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            this.target = this.entity.getTarget();
            return this.target != null && this.entity.squaredDistanceTo(this.target) > 4.0D && !this.entity.onDashCooldown(); // Start if target is within a reasonable range.
        }

        @Override
        public void start() {
            //this.cooldown = 20; // Initial cooldown.
            this.entity.useDash();
            //this.entity.getNavigation().stop();
        }

        @Override
        public void stop() {
            this.target = null;
            this.entity.noClip = false;
        }

        @Override
        public boolean shouldContinue() {
            return !this.dashed; // Continue if target is still within range.
        }

        @Override
        public void tick() {
            /*if (this.cooldown > 0) {
                this.cooldown--;
                return;
            }*/

            if (this.target != null) {
                double dx = this.target.getX() - this.entity.getX();
                double dy = this.target.getY() - this.entity.getY();
                double dz = this.target.getZ() - this.entity.getZ();
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (distance > 0) {
                    double speed = 1.5; // Dash speed.
                    this.entity.setVelocity(dx / distance * speed, dy / distance * speed, dz / distance * speed);
                    this.entity.velocityModified = true; // Ensure velocity is applied.
                    //this.cooldown = 40; // Set cooldown after a dash.


                }
            }
            this.entity.getNavigation().startMovingTo(this.target, this.entity.speed);

            this.dashed = true;
        }
    }


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
