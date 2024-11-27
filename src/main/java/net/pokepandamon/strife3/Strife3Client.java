package net.pokepandamon.strife3;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.pokepandamon.strife3.entity.Strife3Entities;
import net.pokepandamon.strife3.entity.client.BlockEntityRenderer;
import net.pokepandamon.strife3.entity.client.GreaterVerluerModel;
import net.pokepandamon.strife3.entity.client.GreaterVerluerRenderer;
import net.pokepandamon.strife3.entity.client.Strife3ModelLayers;
import net.pokepandamon.strife3.entity.custom.GreaterVerluerEntity;
import net.pokepandamon.strife3.networking.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Strife3Client implements ClientModInitializer {
    public static int permissionLevel;

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(this::onJoinServer);
        UseBlockCallback.EVENT.register(this::onBlockUse);
        UseEntityCallback.EVENT.register(this::onEntityUse);
        AttackEntityCallback.EVENT.register(this::onEntityAttack);
        UseItemCallback.EVENT.register(this::onItemUse);

        ClientPlayNetworking.registerGlobalReceiver(PermissionLevelRequestS2CPayload.ID, (payload, context) -> {
            permissionLevel = payload.permissionValue();
        });

        ClientPlayNetworking.registerGlobalReceiver(AdminBooleanRequestS2CPayload.ID, (payload, context) -> {
            ClientPlayNetworking.send(new AdminBooleanRequestC2SPayload(Strife3Config.admin));
        });

        ClientPlayNetworking.registerGlobalReceiver(SuperDrugRandomValueS2CPayload.ID, (payload, context) -> {
            Strife3.LOGGER.info("Packet Reception" + payload.superDrugRandomValue());
            ((PlayerMixinInterface) context.player()).useSuperDrug(payload.superDrugRandomValue());
        });

        EntityRendererRegistry.register(Strife3Entities.GREATER_VERLUER, GreaterVerluerRenderer::new);
        EntityRendererRegistry.register(Strife3Entities.BLOCK_ENTITY, BlockEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(Strife3ModelLayers.GREATER_VERLUER, GreaterVerluerModel::getTexturedModelData);
    }

    private void onJoinServer(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient minecraftClient) {
        ClientPlayNetworking.send(new AdminBooleanRequestC2SPayload(Strife3Config.admin));
        ClientPlayNetworking.send(new PermissionLevelRequestC2SPayload(true));
    }

    private ActionResult onBlockUse(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult){
        ClientPlayNetworking.send(new PermissionLevelRequestC2SPayload(true));
        BlockState replacedBlock = world.getBlockState(hitResult.getBlockPos().offset(hitResult.getSide()));
        //Strife3.LOGGER.info("I ran the door thingie " + (new ItemStack(Items.OAK_DOOR).isIn(Strife3.BANNED_UNDERWATER_ITEMS)) + " " + player.getStackInHand(hand).isIn(Strife3.BANNED_UNDERWATER_ITEMS) + " " + player.getStackInHand(hand));
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
            //Strife3.LOGGER.info("Ran thiss");
            if(Strife3Config.admin){
                return ActionResult.PASS;
            }
        }else{
            //Strife3.LOGGER.info("Ran thisss");
            Strife3.LOGGER.info(String.valueOf(permissionLevel));
            if (Strife3Config.admin && permissionLevel >= 2){
                return ActionResult.PASS;
            }
        }

        if(((PlayerMixinInterface) player).inDeepOptic()){
            return ActionResult.FAIL;
        }
        //LOGGER.info("Ran this");
        for(Area i : Strife3.protectedAreas){
            if(i.inArea(hitResult.getBlockPos())){
                return ActionResult.FAIL;
            }
        }
        if(player.isSubmergedInWater()){
            ItemStack item = player.getStackInHand(hand);
            if(item.isIn(Strife3.BANNED_UNDERWATER_ITEMS)) {
                return ActionResult.FAIL;
            }
        }
        if(player.getStackInHand(hand).isIn(Strife3.BANNED_UNDERWATER_ITEMS)){
            //Strife3.LOGGER.info(String.valueOf(world.getBlockState(hitResult.getBlockPos())) + world.getBlockState(hitResult.getBlockPos()).isLiquid() + world.getBlockState(hitResult.getBlockPos().offset(hitResult.getSide())));
            if(replacedBlock.isLiquid()){
                return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;
    }

    private ActionResult onEntityUse(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        //Strife3.LOGGER.info(String.valueOf(player instanceof PlayerEntity));
        //Strife3.LOGGER.info(String.valueOf(player instanceof ServerPlayerEntity));
        //Strife3.LOGGER.info(String.valueOf(player instanceof ClientPlayerEntity));

        ClientPlayNetworking.send(new PermissionLevelRequestC2SPayload(true));
        if(MinecraftClient.getInstance().isInSingleplayer()){
            //Strife3.LOGGER.info("Ran thiss");
            if(Strife3Config.admin){
                return ActionResult.PASS;
            }
        }else{
            //Strife3.LOGGER.info("Ran thisss");
            Strife3.LOGGER.info(String.valueOf(permissionLevel));
            if (Strife3Config.admin && permissionLevel >= 2){
                return ActionResult.PASS;
            }
        }

        if(((PlayerMixinInterface) player).inDeepOptic()){
            return ActionResult.FAIL;
        }
        for(Area i : Strife3.protectedAreas){
            if(i.inArea(entity)){
                return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;
    }

    private ActionResult onEntityAttack(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        //Strife3.LOGGER.info(String.valueOf(player instanceof PlayerEntity));
        //Strife3.LOGGER.info(String.valueOf(player instanceof ServerPlayerEntity));
        //Strife3.LOGGER.info(String.valueOf(player instanceof ClientPlayerEntity));
        ClientPlayNetworking.send(new PermissionLevelRequestC2SPayload(true));
        if(MinecraftClient.getInstance().isInSingleplayer()){
            //Strife3.LOGGER.info("Ran thiss");
            if(Strife3Config.admin){
                return ActionResult.PASS;
            }
        }else{
            //Strife3.LOGGER.info("Ran thisss");
            Strife3.LOGGER.info(String.valueOf(permissionLevel));
            if (Strife3Config.admin && permissionLevel >= 2){
                return ActionResult.PASS;
            }
        }

        if(entity instanceof ItemFrameEntity) {
            if(((PlayerMixinInterface) player).inDeepOptic()){
                return ActionResult.FAIL;
            }
            for (Area i : Strife3.protectedAreas) {
                if (i.inArea(entity)) {
                    return ActionResult.FAIL;
                }
            }
        }
        return ActionResult.PASS;
    }

    private TypedActionResult<ItemStack> onItemUse(PlayerEntity player, World world, Hand hand) {
        //Strife3.LOGGER.info("I ran the door thingie " + (new ItemStack(Items.OAK_DOOR).isIn(Strife3.BANNED_UNDERWATER_ITEMS)) + " " + player.getActiveItem().isIn(Strife3.BANNED_UNDERWATER_ITEMS) + " " + player.getActiveItem());
        ClientPlayNetworking.send(new PermissionLevelRequestC2SPayload(true));
        if(MinecraftClient.getInstance().isInSingleplayer()){
            //Strife3.LOGGER.info("Ran thiss");
            if(Strife3Config.admin){
                return TypedActionResult.pass(player.getActiveItem());
            }
        }else{
            //Strife3.LOGGER.info("Ran thisss");
            Strife3.LOGGER.info(String.valueOf(permissionLevel));
            if (Strife3Config.admin && permissionLevel >= 2){
                return TypedActionResult.pass(player.getActiveItem());
            }
        }

        if(player.isSubmergedInWater()){
            ItemStack item = player.getStackInHand(hand);
            if(item.isIn(Strife3.BANNED_UNDERWATER_ITEMS)){
                return TypedActionResult.fail(player.getActiveItem());
            }
        }
        return TypedActionResult.pass(player.getActiveItem());
    }
}
