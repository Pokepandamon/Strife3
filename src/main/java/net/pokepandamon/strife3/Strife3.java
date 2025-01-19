package net.pokepandamon.strife3;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
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
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.sound.AmbientSoundLoops;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockPredicateArgumentType;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.FillCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockBox;
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
import org.spongepowered.asm.mixin.Unique;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;

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
	public static ArrayList<Strife3Doors> doors = new ArrayList<>();
	public static final Identifier WORLD_UPDATE_PACKET = Identifier.of("strife3", "world_update");
	public static final ArrayList<SchematicReference> schematics = new ArrayList<>();
	public static final String folderPath = "C:/Users/swane/Documents/Moding/Strife 3/src/main/resources/data/strife3/schematics";
	private static Path schematicsPath;
	private static Path modResourcesPath = FabricLoader.getInstance().getModContainer(Strife3.MOD_ID)
			.orElseThrow(() -> new RuntimeException("Could not find your mod container!"))
			.getRootPaths().get(0);
	//@Unique private ArrayList<>

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

		File folder = new File(folderPath);

		// Create an ArrayList to store the files
		ArrayList<File> fileList = new ArrayList<>();

		// Check if the folder exists and is a directory
		if (folder.exists() && folder.isDirectory()) {
			// Iterate through all files in the folder
			for (File file : folder.listFiles()) {
				// Add each file to the ArrayList
				fileList.add(file);
			}
		} else {
			System.out.println("The provided path is not a valid folder.");
		}

		schematicsPath = modResourcesPath.resolve("data/strife3/schematics");
	}

	@Override
	public void onInitialize() {
		//
		ServerWorldEvents.LOAD.register((server, world) -> {
			if (world.getRegistryKey() == ServerWorld.OVERWORLD) {
				// Get the spawn position from level.dat or specify custom coordinates

				// Set the world spawn directly
				BlockPos customSpawn = world.getLevelProperties().getSpawnPos();
				world.setSpawnPos(customSpawn, 0.0f); // Second argument is spawn angle (optional)
			}
		});

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

		// You can also register the item color provider if needed
		/*ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
						0x62C744, // Default color for inventory item if you want it green
				ModBlocks.kelpGrowth
		);*/
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

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("door")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.argument("from1", BlockPosArgumentType.blockPos())
				.then(CommandManager.argument("to1", BlockPosArgumentType.blockPos())
				.then(CommandManager.argument("from2", BlockPosArgumentType.blockPos())
				.then(CommandManager.argument("to2", BlockPosArgumentType.blockPos())
				.then(CommandManager.argument("identifier", StringArgumentType.string())
						.executes((context -> {
							Area initialDoorPos = new Area(BlockPosArgumentType.getLoadedBlockPos(context, "from1"), BlockPosArgumentType.getLoadedBlockPos(context, "to1"));
							Area endDoorPos = new Area(BlockPosArgumentType.getLoadedBlockPos(context, "from2"), BlockPosArgumentType.getLoadedBlockPos(context, "to2"));
							doors.add(new Strife3Doors(initialDoorPos, endDoorPos, context.getSource().getWorld(), StringArgumentType.getString(context, "identifier")));
							context.getSource().sendFeedback(() -> Text.literal("Created door " + StringArgumentType.getString(context, "identifier")), true);
							return 1;
						})))))))));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("open")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.argument("identifier", StringArgumentType.string())
				.executes(context -> {
					String result = "";
					for(Strife3Doors door : doors){
						if(StringArgumentType.getString(context, "identifier").equals(door.getIdentifier())) {
							door.open();
							if(result.isEmpty()){
								result = "Opened " + StringArgumentType.getString(context, "identifier");
							}
						}
						//Strife3.LOGGER.info(door.getBlockStateArray());
					}

					if(result.isEmpty()){
						result = "No door found";
					}

					String finalResult = result;

					context.getSource().sendFeedback(() -> Text.literal(finalResult), true);
					return 1;
				}))));

		/*dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("fill")
				.requires((source) -> source.hasPermissionLevel(2)))
				.then(CommandManager.argument("from", BlockPosArgumentType.blockPos())
						.then(CommandManager.argument("to", BlockPosArgumentType.blockPos())
								.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("block", BlockStateArgumentType.blockState(commandRegistryAccess))
										.executes((context) -> execute((ServerCommandSource)context.getSource(), BlockBox.create(BlockPosArgumentType.getLoadedBlockPos(context, "from"), BlockPosArgumentType.getLoadedBlockPos(context, "to")), BlockStateArgumentType.getBlockState(context, "block"), FillCommand.Mode.REPLACE, (Predicate)null))).then(((LiteralArgumentBuilder)CommandManager.literal("replace").executes((context) -> execute((ServerCommandSource)context.getSource(), BlockBox.create(BlockPosArgumentType.getLoadedBlockPos(context, "from"), BlockPosArgumentType.getLoadedBlockPos(context, "to")), BlockStateArgumentType.getBlockState(context, "block"), FillCommand.Mode.REPLACE, (Predicate)null))).then(CommandManager.argument("filter", BlockPredicateArgumentType.blockPredicate(commandRegistryAccess)).executes((context) -> execute((ServerCommandSource)context.getSource(), BlockBox.create(BlockPosArgumentType.getLoadedBlockPos(context, "from"), BlockPosArgumentType.getLoadedBlockPos(context, "to")), BlockStateArgumentType.getBlockState(context, "block"), FillCommand.Mode.REPLACE, BlockPredicateArgumentType.getBlockPredicate(context, "filter")))))).then(CommandManager.literal("keep").executes((context) -> execute((ServerCommandSource)context.getSource(), BlockBox.create(BlockPosArgumentType.getLoadedBlockPos(context, "from"), BlockPosArgumentType.getLoadedBlockPos(context, "to")), BlockStateArgumentType.getBlockState(context, "block"), FillCommand.Mode.REPLACE, (pos) -> pos.getWorld().isAir(pos.getBlockPos()))))).then(CommandManager.literal("outline").executes((context) -> execute((ServerCommandSource)context.getSource(), BlockBox.create(BlockPosArgumentType.getLoadedBlockPos(context, "from"), BlockPosArgumentType.getLoadedBlockPos(context, "to")), BlockStateArgumentType.getBlockState(context, "block"), FillCommand.Mode.OUTLINE, (Predicate)null)))).then(CommandManager.literal("hollow").executes((context) -> execute((ServerCommandSource)context.getSource(), BlockBox.create(BlockPosArgumentType.getLoadedBlockPos(context, "from"), BlockPosArgumentType.getLoadedBlockPos(context, "to")), BlockStateArgumentType.getBlockState(context, "block"), FillCommand.Mode.HOLLOW, (Predicate)null)))).then(CommandManager.literal("destroy").executes((context) -> execute((ServerCommandSource)context.getSource(), BlockBox.create(BlockPosArgumentType.getLoadedBlockPos(context, "from"), BlockPosArgumentType.getLoadedBlockPos(context, "to")), BlockStateArgumentType.getBlockState(context, "block"), FillCommand.Mode.DESTROY, (Predicate)null)))))));*/
		PlayerBlockBreakEvents.BEFORE.register(this::onBlockBreak);

		//Registry.register(Registry.SOUND_EVENT, CUSTOM_MUSIC, CUSTOM_MUSIC_EVENT);
		//ServerBlockEntityEvents  // Ensure you are writing the boolean first
		//responseBuf.writeBlockPos(blockPos);    // If you are sending more data, make sure the order is the same as on the server
		PayloadTypeRegistry.playC2S().register(AdminBooleanRequestC2SPayload.ID, AdminBooleanRequestC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(WorldUpdateC2SPayload.ID, WorldUpdateC2SPayload.CODEC);
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

		ServerPlayNetworking.registerGlobalReceiver(WorldUpdateC2SPayload.ID, (payload, context) -> {
            try {
                ((ServerPlayerMixinInterface) context.player()).startWorldUpdater();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //context.player().sendMessage(Text.literal("It worked!"));
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

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("checkschematic")
				.executes(context -> {
					//context.getSource().getPlayer().playSoundToPlayer(Ambient.ABOVE_WATER, SoundCategory.MASTER, 1F, 1F);
					//context.getSource().getPlayer().playSound(Ambient.ABOVE_WATER, 1F, 1F);
					/*if(schematics.isEmpty()){
						for (File file : schematicsPath.toFile().listFiles()) {
							NbtSizeTracker sizeTracker = new NbtSizeTracker(2097152, 500);
							try {
								Strife3.LOGGER.info(file.getName());
								if (replacementKeys.containsKey(file.getName())) {
									this.schematics.add(new SchematicReference(NbtIo.readCompressed(new FileInputStream(file), sizeTracker), globalReplacementKeys, replacementKeys.get(file.getName()),file.getName()));
								} else {
									this.schematics.add(new SchematicReference(NbtIo.readCompressed(new FileInputStream(file), sizeTracker), globalReplacementKeys,file.getName()));
								}
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
						}
					}
					for(SchematicReference schematic : schematics){
						schematic.checkPosition(context.getSource().getPlayer().getBlockPos(), context.getSource().getWorld());
					}*/
					((ServerPlayerMixinInterface) context.getSource().getPlayer()).testSchematics();
					return 1;
				})));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
				dispatcher.register(CommandManager.literal("checktransformation")
						.then(CommandManager.argument("schematicNumber", IntegerArgumentType.integer())
								.executes(context -> {
									// Get the player and schematic number from the command context
									int schematicNumber = IntegerArgumentType.getInteger(context, "schematicNumber");
									((ServerPlayerMixinInterface) context.getSource().getPlayer()).testTransformations(schematicNumber);
									return 1; // Return a success value
								}))
				)
		);


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