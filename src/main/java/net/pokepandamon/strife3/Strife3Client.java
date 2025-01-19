package net.pokepandamon.strife3;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
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
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.pokepandamon.strife3.block.ModBlocks;
import net.pokepandamon.strife3.entity.Strife3Entities;
import net.pokepandamon.strife3.entity.client.BlockEntityRenderer;
import net.pokepandamon.strife3.entity.client.GreaterVerluerModel;
import net.pokepandamon.strife3.entity.client.GreaterVerluerRenderer;
import net.pokepandamon.strife3.entity.client.Strife3ModelLayers;
import net.pokepandamon.strife3.entity.custom.GreaterVerluerEntity;
import net.pokepandamon.strife3.networking.*;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class Strife3Client implements ClientModInitializer {
    public static int permissionLevel;
    private static KeyBinding updateWorld;

    static{
        updateWorld = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "strife3.keybind.update_world", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_H, // The keycode of the key
                "category.strife3.test" // The translation key of the keybinding's category.
        ));
    }

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepBrainCoralFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepBubbleCoralFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepFireCoralFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepTubeCoralFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepHornCoralFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.boneKelpShoot, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepBrainCoral, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepBubbleCoral, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepFireCoral, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepTubeCoral, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepHornCoral, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepBrainCoralWallFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepBubbleCoralWallFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepFireCoralWallFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepTubeCoralWallFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.deepHornCoralWallFan, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.boneKelpShootWall, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.boneSpire, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.boneProtrusion, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.boneProtrusionWall, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.obsidianCrystal, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.whispyMoss, RenderLayer.getTranslucent());

        ClientPlayConnectionEvents.JOIN.register(this::onJoinServer);
        UseBlockCallback.EVENT.register(this::onBlockUse);
        UseEntityCallback.EVENT.register(this::onEntityUse);
        AttackEntityCallback.EVENT.register(this::onEntityAttack);
        UseItemCallback.EVENT.register(this::onItemUse);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                        0x62C744, // Default color if biome info isn't available
                ModBlocks.kelpGrowth
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (updateWorld.wasPressed()) {
                //client.player.sendMessage(Text.literal("Key 1 was pressed!"), false);
                ClientPlayNetworking.send(new WorldUpdateC2SPayload(true));
            }
        });

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
