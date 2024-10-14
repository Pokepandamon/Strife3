package net.pokepandamon.strife3.mixin;

import com.google.common.base.Objects;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow public float lastHandSwingProgress;
    @Shadow public float handSwingProgress;
    //@Shadow public boolean firstUpdate;
    @Shadow private BlockPos lastBlockPos;
    @Shadow public int hurtTime;
    @Shadow protected int playerHitTimer;
    @Shadow protected PlayerEntity attackingPlayer;
    @Shadow private LivingEntity attacking;
    @Shadow private LivingEntity attacker;
    @Shadow private int lastAttackedTime;
    @Shadow public float prevHeadYaw;
    @Shadow public float headYaw;
    @Shadow public float bodyYaw;
    @Shadow public float prevBodyYaw;
    @Shadow protected float lookDirection;
    @Shadow protected float prevLookDirection;

    @Shadow protected void tickStatusEffects() {}
    @Shadow public Optional<BlockPos> getSleepingPosition() {return null;}
    @Shadow private void setPositionInBed(BlockPos pos) {}
    @Shadow public final boolean canBreatheInWater() {return false;}
    @Shadow protected int getNextAirUnderwater(int air) {return 1;}
    @Shadow protected int getNextAirOnLand(int air) {return 1;}
    @Shadow protected void applyMovementEffects(ServerWorld world, BlockPos pos) {}
    @Shadow public boolean isDead() {
        return false;
    }
    @Shadow protected void updatePostDeath() {}
    @Shadow public void setAttacker(@Nullable LivingEntity attacker) {}

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void baseTick() {
        this.lastHandSwingProgress = this.handSwingProgress;
        if (super.firstUpdate) {
            this.getSleepingPosition().ifPresent(this::setPositionInBed);
        }

        World var2 = this.getWorld();
        if (var2 instanceof ServerWorld serverWorld) {
            EnchantmentHelper.onTick(serverWorld, (LivingEntity) (Object) this);
        }

        super.baseTick();
        this.getWorld().getProfiler().push("livingEntityBaseTick");
        if (this.isFireImmune() || this.getWorld().isClient) {
            this.extinguish();
        }

        if (this.isAlive()) {
            boolean bl = (LivingEntity) (Object) this instanceof PlayerEntity;
            if (!this.getWorld().isClient) {
                if (this.isInsideWall()) {
                    this.damage(this.getDamageSources().inWall(), 1.0F);
                } else if (bl && !this.getWorld().getWorldBorder().contains(this.getBoundingBox())) {
                    double d = this.getWorld().getWorldBorder().getDistanceInsideBorder(this) + this.getWorld().getWorldBorder().getSafeZone();
                    if (d < 0.0) {
                        double e = this.getWorld().getWorldBorder().getDamagePerBlock();
                        if (e > 0.0) {
                            this.damage(this.getDamageSources().outsideBorder(), (float)Math.max(1, MathHelper.floor(-d * e)));
                        }
                    }
                }
            }

            if (this.isSubmergedIn(FluidTags.WATER)) {
                boolean bl2 = !this.canBreatheInWater() && !StatusEffectUtil.hasWaterBreathing((LivingEntity) (Object) this) && (!bl || !((PlayerEntity) (Object)this).getAbilities().invulnerable);
                if (bl2) {
                    this.setAir(this.getNextAirUnderwater(this.getAir()));
                    if (this.getAir() == -20) {
                        this.setAir(0);
                        Vec3d vec3d = this.getVelocity();

                        for(int i = 0; i < 8; ++i) {
                            double f = this.random.nextDouble() - this.random.nextDouble();
                            double g = this.random.nextDouble() - this.random.nextDouble();
                            double h = this.random.nextDouble() - this.random.nextDouble();
                            this.getWorld().addParticle(ParticleTypes.BUBBLE, this.getX() + f, this.getY() + g, this.getZ() + h, vec3d.x, vec3d.y, vec3d.z);
                        }

                        this.damage(this.getDamageSources().drown(), 2.0F);
                    }
                }

                if (!this.getWorld().isClient && this.hasVehicle() && this.getVehicle() != null && this.getVehicle().shouldDismountUnderwater()) {
                    this.stopRiding();
                }
            } else if (this.getAir() < this.getMaxAir()) {
                this.setAir(this.getNextAirOnLand(this.getAir()));
            }

            World var15 = this.getWorld();
            if (var15 instanceof ServerWorld) {
                ServerWorld serverWorld2 = (ServerWorld)var15;
                BlockPos blockPos = this.getBlockPos();
                if (!Objects.equal(this.lastBlockPos, blockPos)) {
                    this.lastBlockPos = blockPos;
                    this.applyMovementEffects(serverWorld2, blockPos);
                }
            }
        }

        if (this.isAlive() && (this.isWet() || this.inPowderSnow)) {
            this.extinguishWithSound();
        }

        if (this.hurtTime > 0) {
            --this.hurtTime;
        }

        if (this.timeUntilRegen > 0 && !((LivingEntity) (Object) this instanceof ServerPlayerEntity)) {
            --this.timeUntilRegen;
        }

        if (this.isDead() && this.getWorld().shouldUpdatePostDeath(this)) {
            this.updatePostDeath();
        }

        if (this.playerHitTimer > 0) {
            --this.playerHitTimer;
        } else {
            this.attackingPlayer = null;
        }

        if (this.attacking != null && !this.attacking.isAlive()) {
            this.attacking = null;
        }

        if (this.attacker != null) {
            if (!this.attacker.isAlive()) {
                this.setAttacker((LivingEntity)null);
            } else if (this.age - this.lastAttackedTime > 100) {
                this.setAttacker((LivingEntity)null);
            }
        }

        this.tickStatusEffects();
        this.prevLookDirection = this.lookDirection;
        this.prevBodyYaw = this.bodyYaw;
        this.prevHeadYaw = this.headYaw;
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.getWorld().getProfiler().pop();
    }
}
