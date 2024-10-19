package net.pokepandamon.strife3;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.pokepandamon.strife3.networking.AdminBooleanRequestC2SPayload;
import net.pokepandamon.strife3.networking.AdminBooleanRequestS2CPayload;
import net.pokepandamon.strife3.networking.PermissionLevelRequestC2SPayload;
import net.pokepandamon.strife3.networking.PermissionLevelRequestS2CPayload;
import org.jetbrains.annotations.Nullable;

public class Strife3Client implements ClientModInitializer {
    public static int permissionLevel;

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(this::onJoinServer);
        UseBlockCallback.EVENT.register(this::onBlockUse);
        UseEntityCallback.EVENT.register(this::onEntityUse);
        AttackEntityCallback.EVENT.register(this::onEntityAttack);

        ClientPlayNetworking.registerGlobalReceiver(PermissionLevelRequestS2CPayload.ID, (payload, context) -> {
            permissionLevel = payload.permissionValue();
        });

        ClientPlayNetworking.registerGlobalReceiver(AdminBooleanRequestS2CPayload.ID, (payload, context) -> {
            ClientPlayNetworking.send(new AdminBooleanRequestC2SPayload(Strife3Config.admin));
        });
    }

    private void onJoinServer(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient minecraftClient) {
        ClientPlayNetworking.send(new AdminBooleanRequestC2SPayload(Strife3Config.admin));
        ClientPlayNetworking.send(new PermissionLevelRequestC2SPayload(true));
    }

    private ActionResult onBlockUse(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult){
        ClientPlayNetworking.send(new PermissionLevelRequestC2SPayload(true));
        //LOGGER.info(String.valueOf(player instanceof PlayerEntity));
        //LOGGER.info(String.valueOf(player instanceof ServerPlayerEntity));
        //LOGGER.info(String.valueOf(player instanceof ClientPlayerEntity));
		/*try {
			//LOGGER.info("Tried if server");
			if (player.getServer().getPermissionLevel(player.getGameProfile()) >= 2) {
				return ActionResult.PASS;
			}
		} catch (Exception e) {
			//LOGGER.info("Now admin tag: " + Strife3Config.admin);
			if(Strife3Config.admin){
				//LOGGER.info("Are you alive");
				return ActionResult.PASS;
			}
		}*/
        if(MinecraftClient.getInstance().isInSingleplayer()){
            Strife3.LOGGER.info("Ran thiss");
            if(Strife3Config.admin){
                return ActionResult.PASS;
            }
        }else{
            Strife3.LOGGER.info("Ran thisss");
            Strife3.LOGGER.info(String.valueOf(permissionLevel));
            if (Strife3Config.admin && permissionLevel >= 2){
                return ActionResult.PASS;
            }
        }
        //LOGGER.info("Ran this");
        for(Area i : Strife3.protectedAreas){
            if(i.inArea(hitResult.getBlockPos())){
                return ActionResult.FAIL;
            }
        }

        return ActionResult.PASS;
    }

    private ActionResult onEntityUse(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        Strife3.LOGGER.info(String.valueOf(player instanceof PlayerEntity));
        Strife3.LOGGER.info(String.valueOf(player instanceof ServerPlayerEntity));
        Strife3.LOGGER.info(String.valueOf(player instanceof ClientPlayerEntity));
        try {
            if (player.getServer().getPermissionLevel(player.getGameProfile()) >= 2) {
                return ActionResult.SUCCESS;
            }
        } catch (Exception e) {
            if(Strife3Config.admin){
                return ActionResult.SUCCESS;
            }
        }
        for(Area i : Strife3.protectedAreas){
            if(i.inArea(entity)){
                return ActionResult.FAIL;
            }
        }
        return ActionResult.SUCCESS;
    }

    private ActionResult onEntityAttack(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        Strife3.LOGGER.info(String.valueOf(player instanceof PlayerEntity));
        Strife3.LOGGER.info(String.valueOf(player instanceof ServerPlayerEntity));
        Strife3.LOGGER.info(String.valueOf(player instanceof ClientPlayerEntity));
        try {
            if (player.getServer().getPermissionLevel(player.getGameProfile()) >= 2) {
                return ActionResult.SUCCESS;
            }
        } catch (Exception e) {
            if(Strife3Config.admin){
                return ActionResult.SUCCESS;
            }
        }
        if(entity instanceof ItemFrameEntity) {
            for (Area i : Strife3.protectedAreas) {
                if (i.inArea(entity)) {
                    return ActionResult.FAIL;
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
