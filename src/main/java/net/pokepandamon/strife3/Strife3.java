package net.pokepandamon.strife3;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.dedicated.command.OpCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pokepandamon.strife3.datagen.Strife3WorldGenerator;
import net.pokepandamon.strife3.effects.HeavyDiverDebuffEffect;
import net.pokepandamon.strife3.effects.HeavyDiverRegenEffect;
import net.pokepandamon.strife3.effects.MorphineEffect;
import net.pokepandamon.strife3.items.CustomMaterialInit;
import net.pokepandamon.strife3.items.ModItemGroups;
import net.pokepandamon.strife3.items.ModItems;
import net.pokepandamon.strife3.music.Ambient;
import net.pokepandamon.strife3.music.MasterMusic;
import net.pokepandamon.strife3.music.Song;
import net.pokepandamon.strife3.networking.AdminBooleanRequestC2SPayload;
import net.pokepandamon.strife3.networking.AdminBooleanRequestS2CPayload;
import net.pokepandamon.strife3.networking.PermissionLevelRequestS2CPayload;
import net.pokepandamon.strife3.networking.PermissionLevelRequestC2SPayload;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Strife3 implements ModInitializer {
	public static final String MOD_ID = "strife3";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final StatusEffect MORPHINE;
	public static final StatusEffect HEAVY_DIVER_DEBUFF;
	public static final StatusEffect HEAVY_DIVER_REGENERATION;
	public static ArrayList<Area> protectedAreas;
	public static final Identifier ADMIN_VALUE_PACKET = Identifier.of("strife3", "admin_value");
	public static final Identifier PERMISSION_LEVEL_REQUEST = Identifier.of("strife3", "permission_level_request");

	static {
		MORPHINE = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "morphine"), new MorphineEffect());
		HEAVY_DIVER_DEBUFF = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "heavy_diver_debuff"), new HeavyDiverDebuffEffect());
		HEAVY_DIVER_REGENERATION = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "heavy_diver_regeneration"), new HeavyDiverRegenEffect());
		protectedAreas = new ArrayList<Area>();
	}

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		CustomMaterialInit.load();
		Strife3Config.loadConfig();
		MasterMusic.init();
		//Strife3WorldGenerator.generateModWorldGen();
		protectedAreas.add(new Area(-10,-10,-10,10,-12,10));


		PlayerBlockBreakEvents.BEFORE.register(this::onBlockBreak);

		//Registry.register(Registry.SOUND_EVENT, CUSTOM_MUSIC, CUSTOM_MUSIC_EVENT);
		//ServerBlockEntityEvents  // Ensure you are writing the boolean first
		//responseBuf.writeBlockPos(blockPos);    // If you are sending more data, make sure the order is the same as on the server
		PayloadTypeRegistry.playC2S().register(AdminBooleanRequestC2SPayload.ID, AdminBooleanRequestC2SPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(AdminBooleanRequestS2CPayload.ID, AdminBooleanRequestS2CPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PermissionLevelRequestC2SPayload.ID, PermissionLevelRequestC2SPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PermissionLevelRequestS2CPayload.ID, PermissionLevelRequestS2CPayload.CODEC);

		//MinecraftServer

		/*ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			if (!server.getWorld(Strife3Dimensions.DEEP_OPTIC_DIM_LEVEL_KEY).hasBiomes()) {
				server.getWorld(Strife3Dimensions.DEEP_OPTIC_DIM_LEVEL_KEY).  // Reference your pre-made world here
			}
		});*/

		ServerPlayNetworking.registerGlobalReceiver(AdminBooleanRequestC2SPayload.ID, (payload, context) -> {
			((ServerPlayerMixinInterface) context.player()).setClientAdminValue(payload.adminValue());
		});

		ServerPlayNetworking.registerGlobalReceiver(PermissionLevelRequestC2SPayload.ID, (payload, context) -> {
			ServerPlayNetworking.send(context.player(), new PermissionLevelRequestS2CPayload(context.server().getPermissionLevel(context.player().getGameProfile())));
		});

		ServerTickEvents.START_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				//((PlayerMixinInterface) player).customPlayerTick();

				if (isWearingAnyHeavyDiversArmor(player) && !player.hasStatusEffect(StatusEffects.POISON)) {
					player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_DEBUFF), 50, 0, false, false));
				}

				if (isWearingFullHeavyDiversArmor(player) && !player.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(MORPHINE))) {
					player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_REGENERATION), 50, 0, false, false));
					//ServerPlayNetworking.send(player, new EntityStatusEffectS2CPacket(player.getId(), new StatusEffectInstance(StatusEffects.REGENERATION, 19, 0, false, false), false), PacketByteBufs.empty());
				}

				if (player.getInventory().getArmorStack(3).getItem() == ModItems.HYBRID_MASK || player.getInventory().getArmorStack(3).getItem() == ModItems.NIGHT_VISION_GOGGLES){
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 60, 0, false, false));
				}

				server.getPlayerManager().sendStatusEffects(player);
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("playsoundstrife")
				.executes(context -> {
					//context.getSource().getPlayer().playSoundToPlayer(Ambient.ABOVE_WATER, SoundCategory.MASTER, 1F, 1F);
					//context.getSource().getPlayer().playSound(Ambient.ABOVE_WATER, 1F, 1F);
					context.getSource().getWorld().playSound(null, context.getSource().getPlayer().getBlockPos(), Ambient.ABOVE_WATER, SoundCategory.MASTER);
					LOGGER.info("But I ran this...");
					return 1;
				})));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("morphinestate")
				.executes(context -> {
					LOGGER.info(String.valueOf(((PlayerMixinInterface) (context.getSource().getPlayer())).onMorphine()));
					LOGGER.info(String.valueOf(((PlayerMixinInterface) (context.getSource().getPlayer())).morphineTimer()));
					return 1;
				})));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("usemorphine")
				.executes(context -> {
					((PlayerMixinInterface) (context.getSource().getPlayer())).useMorphine();
					return 1;
				})));


		/*ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, tooltip) -> {
			if (EnchantmentHelper.hasEnchantments(stack)) {
				// Iterate through the tooltip lines to find and remove the enchantment lines
				tooltip.removeIf(text -> {
					// Check for enchantment text formatting, e.g., italics or gray color
					return text.getStyle().isItalic();
				});
			}
		});*/
	}

	public static boolean isWearingFullHeavyDiversArmor(ServerPlayerEntity player) {
		ItemStack helmet = player.getInventory().getArmorStack(3);
		ItemStack chestplate = player.getInventory().getArmorStack(2);
		ItemStack leggings = player.getInventory().getArmorStack(1);
		ItemStack boots = player.getInventory().getArmorStack(0);

		return helmet.getItem() == ModItems.HEAVY_DIVERS_MASK &&
				chestplate.getItem() == ModItems.HEAVY_DIVERS_CHESTPLATE &&
				leggings.getItem() == ModItems.HEAVY_DIVERS_LEGGINGS &&
				boots.getItem() == ModItems.HEAVY_DIVERS_BOOTS;
	}

	public static boolean isWearingFullHeavyDiversArmor(ClientPlayerEntity player) {
		ItemStack helmet = player.getInventory().getArmorStack(3);
		ItemStack chestplate = player.getInventory().getArmorStack(2);
		ItemStack leggings = player.getInventory().getArmorStack(1);
		ItemStack boots = player.getInventory().getArmorStack(0);

		return helmet.getItem() == ModItems.HEAVY_DIVERS_MASK &&
				chestplate.getItem() == ModItems.HEAVY_DIVERS_CHESTPLATE &&
				leggings.getItem() == ModItems.HEAVY_DIVERS_LEGGINGS &&
				boots.getItem() == ModItems.HEAVY_DIVERS_BOOTS;
	}

	public static boolean isWearingAnyHeavyDiversArmor(ServerPlayerEntity player) {
		ItemStack helmet = player.getInventory().getArmorStack(3);
		ItemStack chestplate = player.getInventory().getArmorStack(2);
		ItemStack leggings = player.getInventory().getArmorStack(1);
		ItemStack boots = player.getInventory().getArmorStack(0);

		return helmet.getItem() == ModItems.HEAVY_DIVERS_MASK ||
				chestplate.getItem() == ModItems.HEAVY_DIVERS_CHESTPLATE ||
				leggings.getItem() == ModItems.HEAVY_DIVERS_LEGGINGS ||
				boots.getItem() == ModItems.HEAVY_DIVERS_BOOTS;
	}

	public static boolean isWearingAnyHeavyDiversArmor(ClientPlayerEntity player) {
		ItemStack helmet = player.getInventory().getArmorStack(3);
		ItemStack chestplate = player.getInventory().getArmorStack(2);
		ItemStack leggings = player.getInventory().getArmorStack(1);
		ItemStack boots = player.getInventory().getArmorStack(0);

		return helmet.getItem() == ModItems.HEAVY_DIVERS_MASK ||
				chestplate.getItem() == ModItems.HEAVY_DIVERS_CHESTPLATE ||
				leggings.getItem() == ModItems.HEAVY_DIVERS_LEGGINGS ||
				boots.getItem() == ModItems.HEAVY_DIVERS_BOOTS;
	}

	public static Identifier id(String path) { return Identifier.of(MOD_ID, path);}

	private boolean onBlockBreak(World world, PlayerEntity player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {
		LOGGER.info("Ran this");
		//LOGGER.info(String.valueOf(player instanceof PlayerEntity));
		//LOGGER.info(String.valueOf(player instanceof ServerPlayerEntity));
		//LOGGER.info(String.valueOf(player instanceof ClientPlayerEntity));
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			if (MinecraftClient.getInstance().isInSingleplayer()) {
				LOGGER.info("Ran thiss");
				if (Strife3Config.admin) {
					return true;
				}
			} else {
				LOGGER.info("Ran thisss");
				if (((ServerPlayerMixinInterface) player).getClientAdminValue() && player.getServer().getPermissionLevel(player.getGameProfile()) >= 2) {
					return true;
				}
			}
		}else{
			if (((ServerPlayerMixinInterface) player).getClientAdminValue() && player.getServer().getPermissionLevel(player.getGameProfile()) >= 2) {
				return true;
			}
		}
		/*try {
			if (player.getServer().getPermissionLevel(player.getGameProfile()) >= 2) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("Now admin tag: " + Strife3Config.admin);
			if(Strife3Config.admin){
				return true;
			}
		}*/

		//LOGGER.info("Ran this");

		for(Area i : protectedAreas){
			if(i.inArea(blockPos)){
				LOGGER.info("Ran that");
				return false;
				//return ActionResult.FAIL;
			}
		}
		return true;
		//return ActionResult.PASS;
	}


	/*private void onTooltip(ItemStack stack, List<Text> tooltip, Item.TooltipContext context) {
		// Check if the item has enchantments
		if (EnchantmentHelper.hasEnchantments(stack)) {
			// Iterate through the tooltip lines to find and remove the enchantment lines
			tooltip.removeIf(text -> {
				// Check for enchantment text formatting, e.g., italics or gray color
				return text.getStyle().isItalic();
			});
		}
	}*/
}