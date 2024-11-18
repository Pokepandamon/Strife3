package net.pokepandamon.strife3;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.sound.AmbientSoundLoops;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.pokepandamon.strife3.block.ModBlocks;
import net.pokepandamon.strife3.effects.*;
import net.pokepandamon.strife3.entity.Strife3Entities;
import net.pokepandamon.strife3.entity.custom.GreaterVerluerEntity;
import net.pokepandamon.strife3.items.CustomMaterialInit;
import net.pokepandamon.strife3.items.ModItemGroups;
import net.pokepandamon.strife3.items.ModItems;
import net.pokepandamon.strife3.items.custom.EtherTablet;
import net.pokepandamon.strife3.music.Ambient;
import net.pokepandamon.strife3.music.MasterMusic;
import net.pokepandamon.strife3.music.Song;
import net.pokepandamon.strife3.networking.*;
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
	public static final StatusEffect MEDKIT;
	public static final StatusEffect RESISTANCE_DRUG;
	public static final StatusEffect SPEED_DRUG;
	public static final StatusEffect STRENGTH_DRUG;
	public static final StatusEffect SUPER_DRUG;
	public static ArrayList<Area> protectedAreas;
	public static final Identifier ADMIN_VALUE_PACKET = Identifier.of("strife3", "admin_value");
	public static final Identifier PERMISSION_LEVEL_REQUEST = Identifier.of("strife3", "permission_level_request");
	public static final Identifier SUPER_DRUG_RANDOM_VALUE = Identifier.of("strife3", "super_drug_random_value");
	public static final TagKey<Item> BANNED_UNDERWATER_ITEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of("strife3", "banned_underwater_items"));
	public static final Area starterIsland1 = new Area(-4,239,-102,31,255,-71);
	public static final Area starterIsland2 = new Area(-1,243,13,47,255,54);
	public static final Area deepOpticEntrance = new Area(-575,11,296,-540,59,343);
	public static final Area netherideFactory = new Area(509,9,-126,574,77,-31);
	public static final Area deepOpticChanger = new Area(-430, 4, 264, -391,31, 338);

	static {
		MORPHINE = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "morphine"), new MorphineEffect());
		HEAVY_DIVER_DEBUFF = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "heavy_diver_debuff"), new HeavyDiverDebuffEffect());
		HEAVY_DIVER_REGENERATION = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "heavy_diver_regeneration"), new HeavyDiverRegenEffect());
		MEDKIT = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "medkit"), new MedkitEffect());
		RESISTANCE_DRUG = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "resistance_drug"), new ResistanceDrugEffect());
		SPEED_DRUG = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "speed_drug"), new SpeedDrugEffect());
		STRENGTH_DRUG = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "strength_drug"), new StrengthDrugEffect());
		SUPER_DRUG = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "super_drug"), new SuperDrugEffect());
		protectedAreas = new ArrayList<Area>();
	}

	@Override
	public void onInitialize() {
		//
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBocks();
		CustomMaterialInit.load();
		Strife3Config.loadConfig();
		MasterMusic.init();
		Strife3Entities.registerStrife3Entities();
		FabricDefaultAttributeRegistry.register(Strife3Entities.GREATER_VERLUER, GreaterVerluerEntity.createGreaterVerluer());
		//FabricDefaultAttributeRegistry.register(Strife3Entities.GREATER_VERLUER, DrownedEntity.createDrownedAttributes());
		//Strife3WorldGenerator.generateModWorldGen();
		protectedAreas.add(starterIsland1);
		protectedAreas.add(starterIsland2);
		protectedAreas.add(deepOpticEntrance);
		protectedAreas.add(netherideFactory);
		protectedAreas.add(deepOpticChanger);

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
						0x62C744, // Default color if biome info isn't available
				ModBlocks.kelpGrowth
		);

		// You can also register the item color provider if needed
		ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
						0x62C744, // Default color for inventory item if you want it green
				ModBlocks.kelpGrowth
		);
		//Registry.register(Registry.BIOME, CUSTOM_BIOME_KEY.getValue(), ModBiomes.createCustomBiome());

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("depths")
				.executes(context -> {
					WorldFlags worldFlags = WorldFlags.getServerState(context.getSource().getServer());
					worldFlags.depthsEntered = !worldFlags.depthsEntered;
					return 1;
				})));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("factory")
				.executes(context -> {
					WorldFlags worldFlags = WorldFlags.getServerState(context.getSource().getServer());
					worldFlags.factoryEntered = !worldFlags.factoryEntered;
					return 1;
				})));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("didWe")
				.executes(context -> {
					WorldFlags worldFlags = WorldFlags.getServerState(context.getSource().getServer());
					context.getSource().sendMessage(Text.of("Depths: " + worldFlags.depthsEntered + " | Factory: " + worldFlags.factoryEntered));
					return 1;
				})));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("elevator")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.argument("targets", EntityArgumentType.entities()).executes(context -> {
					for(Entity entity : EntityArgumentType.getEntities(context, "targets")){
						if(entity instanceof ServerPlayerEntity){
							((ServerPlayerEntity) entity).playSoundToPlayer(Song.DEEP_OPTIC, SoundCategory.MUSIC, 1F, 1F);
							((ServerPlayerMixinInterface) entity).setSongTimer(9800);
						}
					}
					return 1;
				}))));

		PlayerBlockBreakEvents.BEFORE.register(this::onBlockBreak);

		//Registry.register(Registry.SOUND_EVENT, CUSTOM_MUSIC, CUSTOM_MUSIC_EVENT);
		//ServerBlockEntityEvents  // Ensure you are writing the boolean first
		//responseBuf.writeBlockPos(blockPos);    // If you are sending more data, make sure the order is the same as on the server
		PayloadTypeRegistry.playC2S().register(AdminBooleanRequestC2SPayload.ID, AdminBooleanRequestC2SPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(AdminBooleanRequestS2CPayload.ID, AdminBooleanRequestS2CPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PermissionLevelRequestC2SPayload.ID, PermissionLevelRequestC2SPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PermissionLevelRequestS2CPayload.ID, PermissionLevelRequestS2CPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SuperDrugRandomValueS2CPayload.ID, SuperDrugRandomValueS2CPayload.CODEC);

		//MinecraftServer

		/*ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			if (!server.getWorld(Strife3Dimensions.DEEP_OPTIC_DIM_LEVEL_KEY).hasBiomes()) {
				server.getWorld(Strife3Dimensions.DEEP_OPTIC_DIM_LEVEL_KEY).  // Reference your pre-made world here
			}
		});*/
		UseItemCallback.EVENT.register((player, world, hand) -> onItemUse(player, world, hand));

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
					player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_DEBUFF), 300, 0, false, false));
				}

				if (isWearingFullHeavyDiversArmor(player) && !player.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(MORPHINE))) {
					player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_REGENERATION), 300, 0, false, false));
					//ServerPlayNetworking.send(player, new EntityStatusEffectS2CPacket(player.getId(), new StatusEffectInstance(StatusEffects.REGENERATION, 19, 0, false, false), false), PacketByteBufs.empty());
				}

				if (player.getInventory().getArmorStack(3).getItem() == ModItems.HYBRID_MASK || player.getInventory().getArmorStack(3).getItem() == ModItems.NIGHT_VISION_GOGGLES){
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 0, false, false));
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
		//LOGGER.info("Ran this");
		//LOGGER.info(String.valueOf(player instanceof PlayerEntity));
		//LOGGER.info(String.valueOf(player instanceof ServerPlayerEntity));
		//LOGGER.info(String.valueOf(player instanceof ClientPlayerEntity));
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			if (MinecraftClient.getInstance().isInSingleplayer()) {
				//LOGGER.info("Ran thiss");
				if (Strife3Config.admin) {
					return true;
				}
			} else {
				//LOGGER.info("Ran thisss");
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
		if(((PlayerMixinInterface) player).inDeepOptic()){
			return false;
		}

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

	private static TypedActionResult onItemUse(PlayerEntity player, World world, Hand hand) {
		if (!world.isClient) {  // Only run this on the server side
			if (player.getItemCooldownManager().isCoolingDown(player.getStackInHand(hand).getItem())) {
				if(player.getStackInHand(hand).getItem().equals(ModItems.ETHER_TABLET)){
					int cooldownSeconds = (int) (player.getItemCooldownManager().getCooldownProgress(player.getStackInHand(hand).getItem(), 1F) * 18000 / 20);
					String cooldownString = "";
					/*if(cooldownSeconds / 60 < 10){
						cooldownString = "0" + cooldownSeconds / 60;
					}*/
					cooldownString = cooldownString + cooldownSeconds / 60;
					if(cooldownSeconds % 60 < 10){
						cooldownString = cooldownString + ":0";
					}else{
						cooldownString = cooldownString + ":";
					}
					cooldownString = cooldownString + cooldownSeconds % 60;
					player.sendMessage(Text.literal("Your connection to the ether realm remains unstable, try again in: " + cooldownString).formatted(Formatting.DARK_RED));
				}
				return TypedActionResult.fail(player.getStackInHand(hand));  // Prevent further action
			}
		}
		return TypedActionResult.pass(player.getStackInHand(hand));  // Allow normal action if no cooldown
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