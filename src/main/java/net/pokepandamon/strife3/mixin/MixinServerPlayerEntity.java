package net.pokepandamon.strife3.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.pokepandamon.strife3.ServerPlayerMixinInterface;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.Strife3Dimensions;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends MixinEntityPlayer implements ServerPlayerMixinInterface {

    @Shadow @Final private static Logger LOGGER;

    @Unique private boolean seenZoneChange = false;
    @Unique private boolean clientAdminValue;
//
    protected MixinServerPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Override
    public void customServerPlayerTick(){
        this.locationTick();
    }
    @Override
    public void locationTick(){
        //LOGGER.info(String.valueOf(this.getWorld().getDimension().effects()));
        if(this.inDeepOptic()){
            ((ServerPlayerEntity)(Object)this).networkHandler.sendPacket(new GameMessageS2CPacket(Text.literal("Depth: " + (255 - ((Double) this.getY()).intValue() + 555) +  " meters"), true));
        }else {
            //LOGGER.info("X: " + this.getX() + " Y: " + this.getY() + " Z: " + this.getZ() + " RX: " + ((Double) this.getX()).intValue() + " RY: " + ((Double) this.getY()).intValue() + " RZ: " + ((Double) this.getZ()).intValue() + " Zone: " + super.currentZone + " Seen: " + seenZoneChange);
            if (seenZoneChange) {
                seenZoneChange = !(this.getY() <= 90 || (this.getY() >= 110 && this.getY() <= 140) || this.getY() >= 160);
            }
            if (!seenZoneChange && ((((Double) this.getY()).intValue() == 100) || (((Double) this.getY()).intValue() == 150))) {
                seenZoneChange = true;

                Text title;
                Text subtitle;

                if (((Double) this.getY()).intValue() == 100) {
                    if (super.currentZone == "Lush") {
                        title = Text.literal("You are entering the Depths").formatted(Formatting.BOLD, Formatting.DARK_PURPLE);
                        subtitle = Text.literal("Your mining speed has been decreased").formatted(Formatting.GRAY);
                    } else {
                        title = Text.literal("You are entering the Lush").formatted(Formatting.BOLD, Formatting.DARK_GREEN);
                        subtitle = Text.literal("Your mining speed has been increased").formatted(Formatting.GRAY);
                    }
                } else {
                    if (super.currentZone == "Shallows") {
                        title = Text.literal("You are entering the Lush").formatted(Formatting.BOLD, Formatting.DARK_GREEN);
                        subtitle = Text.literal("Your mining speed has been decreased").formatted(Formatting.GRAY);
                    } else {
                        title = Text.literal("You are entering the Shallows").formatted(Formatting.BOLD, Formatting.AQUA);
                        subtitle = Text.literal("Your mining speed has been increased").formatted(Formatting.GRAY);
                    }
                }

                //net.minecraft.network.packet.s2c.play.

                TitleS2CPacket titlePacket = new TitleS2CPacket(title);
                SubtitleS2CPacket subtitlePacket = new SubtitleS2CPacket(subtitle);

                ((ServerPlayerEntity) (Object) this).networkHandler.sendPacket(titlePacket);
                ((ServerPlayerEntity) (Object) this).networkHandler.sendPacket(subtitlePacket);
            }
            //LOGGER.info("Inside Water: " + this.isInsideWaterOrBubbleColumn() + " | My Check" + this.getWorld().getBlockState(new BlockPos((int) this.getX(), (int)this.getY(), (int)this.getZ())));
            if (this.isInsideWaterOrBubbleColumn()) {
                //LOGGER.info("I WAS HERE");
                ((ServerPlayerEntity) (Object) this).networkHandler.sendPacket(new GameMessageS2CPacket(Text.literal("Depth: " + (255 - ((Double) this.getY()).intValue()) + " meters"), true));
            }
        }
        super.locationTick();
    }

    @Inject(method= "tick", at=@At("HEAD"))
    public void tick(CallbackInfo ci){
        this.customServerPlayerTick();
    }

    @Override
    public void setClientAdminValue(boolean newClientAdminValue){
        this.clientAdminValue = newClientAdminValue;
    }

    @Override
    public boolean getClientAdminValue(){
        return clientAdminValue;
    }

    @Override
    public boolean inDeepOptic(){
        return this.getWorld().getRegistryKey().equals(Strife3Dimensions.DEEP_OPTIC_DIM_LEVEL_KEY);
    }
}
