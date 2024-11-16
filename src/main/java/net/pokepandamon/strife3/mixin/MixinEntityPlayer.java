package net.pokepandamon.strife3.mixin;

import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.pokepandamon.strife3.Area;
import net.pokepandamon.strife3.PlayerMixinInterface;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.Strife3Dimensions;
import net.pokepandamon.strife3.items.ModItems;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.pokepandamon.strife3.Strife3.protectedAreas;

@Mixin(PlayerEntity.class)
public abstract class MixinEntityPlayer extends LivingEntity implements PlayerMixinInterface {
    @Shadow @Final private static Logger LOGGER;
    @Shadow private final PlayerAbilities abilities = new PlayerAbilities();

    @Shadow public abstract boolean damage(DamageSource source, float amount);
    @Shadow public abstract PlayerInventory getInventory();

    @Shadow protected boolean isSubmergedInWater;

    @Shadow public abstract void playSoundToPlayer(SoundEvent sound, SoundCategory category, float volume, float pitch);

    @Unique private int morphine = 0;
    @Unique private int resistanceDrug = 0;
    @Unique private int speedDrug = 0;
    @Unique private int strengthDrug = 0;
    @Unique private int superDrug = 0;
    @Unique private int superDrugRandomChoice = 0;
    @Unique private int medkit = 0;
    @Unique private boolean drowned = false;
    @Unique private int drowningTick = 15;
    @Unique private int fullHeavyArmorTick = 25;
    @Unique private int anyHeavyArmorTick = 25;
    @Unique protected String currentZone;

    protected MixinEntityPlayer(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int speedDrugTimer(){
        return speedDrug;
    }

    @Override
    public boolean onSpeedDrug(){
        return speedDrug != 0;
    }

    @Override
    public void useSpeedDrug(){
        this.speedDrug = 120*20 + 20*20;
    }

    @Override
    public void speedDrugTick(){
        if(this.onSpeedDrug()){this.speedDrug--;}
    }

    @Override
    public int strengthDrugTimer(){
        return strengthDrug;
    }

    @Override
    public boolean onStrengthDrug(){
        return strengthDrug != 0;
    }

    @Override
    public void useStrengthDrug(){
        this.strengthDrug = 110*20;
    }

    @Override
    public void strengthDrugTick(){
        if(this.onStrengthDrug()){this.strengthDrug--;}
    }

    @Override
    public int superDrugTimer(){
        return superDrug;
    }

    @Override
    public boolean onSuperDrug(){
        return superDrug != 0;
    }

    @Override
    public void useSuperDrug(int superDrugRandomChoiceM){
        this.superDrug = 30*20;
        this.superDrugRandomChoice = superDrugRandomChoiceM;
    }

    @Override
    public void superDrugTick(){
        if(this.onSuperDrug()){this.superDrug--;}
    }

    @Override
    public int superDrugRandomChoice(){
        return superDrugRandomChoice;
    }

    @Override
    public int morphineTimer(){
        return morphine;
    }

    @Override
    public int resistanceDrugTimer(){
        return resistanceDrug;
    }

    @Override
    public boolean onMorphine(){
        return morphine != 0;
    }

    @Override
    public boolean onResistanceDrug(){
        return resistanceDrug != 0;
    }

    @Override
    public boolean onMedkit(){
        return medkit != 0;
    }

    @Override
    public void useMorphine(){
        this.morphine = 345;
    }

    @Override
    public void useResistanceDrug(){
        this.resistanceDrug = 120*20;
    }

    @Override
    public void useMedkit(){
        this.medkit = 345;
    }

    @Override
    public void morphineTick(){
        if(this.onMorphine()){this.morphine--;}
    }

    @Override
    public void resistanceDrugTick(){
        if(this.onResistanceDrug()){this.resistanceDrug--;}
    }

    @Override
    public void medkitTick(){
        if(this.onResistanceDrug()){this.medkit--;}
    }

    @Override
    public void customPlayerTick() {
        //LOGGER.info(String.valueOf(this.morphineTimer()));
        //Strife3.LOGGER.info("Super Tick " + this.superDrugTimer() + " | Random Choice: " + this.superDrugRandomChoice + " | Random Choice Actual " + this.superDrugRandomChoice());
        //Strife3.LOGGER.info("Morphine: " + this.morphineTimer() + " | Resistance Drug: " + this.resistanceDrugTimer() + " | Speed Drug: " + this.speedDrugTimer() + " | Strength Drug: " + this.strengthDrugTimer() + " | Super Drug: " + this.superDrugTimer());
        this.drowningTick();
        this.resistanceDrugTick();
        this.morphineTick();
        this.medkitTick();
        this.speedDrugTick();
        this.strengthDrugTick();
        this.superDrugTick();
        this.locationTick();
    }

    @Override
    public void drowningTick(){
        if(this.getAir() < 0 && !drowned){
            drowned = true;
        }
        if(this.getAir() == 300 && drowned){
            drowned = false;
        }
        if(drowningTick == 0) {
            drowningTick = 16;
            this.damage(getDamageSources().drown(), 1F);
        }
        if(drowned){
            drowningTick--;
        }
    }

    @Override
    public void heavyArmorTick(){
        ItemStack helmet = this.getInventory().getArmorStack(3);
        ItemStack chestplate = this.getInventory().getArmorStack(2);
        ItemStack leggings = this.getInventory().getArmorStack(1);
        ItemStack boots = this.getInventory().getArmorStack(0);

        if(helmet.getItem() == ModItems.HEAVY_DIVERS_MASK &&
                chestplate.getItem() == ModItems.HEAVY_DIVERS_CHESTPLATE &&
                leggings.getItem() == ModItems.HEAVY_DIVERS_LEGGINGS &&
                boots.getItem() == ModItems.HEAVY_DIVERS_BOOTS){
            fullHeavyArmorTick = 25;
        }else{
            if(fullHeavyArmorTick > 0){fullHeavyArmorTick--;}
        }

        if(helmet.getItem() == ModItems.HEAVY_DIVERS_MASK ||
                chestplate.getItem() == ModItems.HEAVY_DIVERS_CHESTPLATE ||
                leggings.getItem() == ModItems.HEAVY_DIVERS_LEGGINGS ||
                boots.getItem() == ModItems.HEAVY_DIVERS_BOOTS){
            anyHeavyArmorTick = 25;
        }else{
            if(anyHeavyArmorTick > 0){anyHeavyArmorTick--;}
        }
    }

    @Override
    public boolean anyHeavyArmorCooldown(){
        return anyHeavyArmorTick >= 0;
    }

    @Override
    public boolean fullHeavyArmorCooldown(){
        return fullHeavyArmorTick >= 0;
    }

    @Override
    public boolean inDeepOptic(){
        return this.getWorld().getRegistryKey().equals(Strife3Dimensions.DEEP_OPTIC_DIM_LEVEL_KEY);
    }

    @Override
    public void locationTick(){
        if(this.inDeepOptic()){
            currentZone = "DeepOptic";
        }else {
            if (((Double) this.getY()).intValue() > 150) {
                currentZone = "Shallows";
            } else if (((Double) this.getY()).intValue() > 100) {
                currentZone = "Lush";
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 2, 0, true, false));
            } else {
                currentZone = "Depths";
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 2, 1, true, false));
            }
        }
    }

    @Inject(method= "tick", at=@At("HEAD"))
    public void tick(CallbackInfo ci){
        this.customPlayerTick();
    }

    @Override
    public String getCurrentZone(){
        return this.currentZone;
    }
    /*
    @Inject(method= "canPlaceOn", at=@At("HEAD"))
    public void canPlaceOn(BlockPos pos, Direction facing, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        LOGGER.info("canPlaceOn");
        for(Area i : protectedAreas){
            if(i.inArea(pos)){
                LOGGER.info("inArea");
                cir.setReturnValue(false);
            }
        }
        if (this.abilities.allowModifyWorld) {
            cir.setReturnValue(true);
        } else {
            BlockPos blockPos = pos.offset(facing.getOpposite());
            CachedBlockPosition cachedBlockPosition = new CachedBlockPosition(this.getWorld(), blockPos, false);
            cir.setReturnValue(stack.canPlaceOn(cachedBlockPosition));
        }
    }

    @Inject(method= "canModifyBlocks", at=@At("HEAD"))
    public void canModifyBlocks(CallbackInfoReturnable<Boolean> cir) {
        LOGGER.info("canPlaceOn");
        for(Area i : protectedAreas){
            if(i.inArea((PlayerEntity) (Object)this)){
                LOGGER.info("inArea");
                cir.setReturnValue(false);
            }
        }
        cir.setReturnValue(this.abilities.allowModifyWorld);
    }*/
}
