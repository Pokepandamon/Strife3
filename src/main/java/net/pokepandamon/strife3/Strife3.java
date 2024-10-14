package net.pokepandamon.strife3;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pokepandamon.strife3.effects.HeavyDiverDebuffEffect;
import net.pokepandamon.strife3.effects.HeavyDiverRegenEffect;
import net.pokepandamon.strife3.effects.MorphineEffect;
import net.pokepandamon.strife3.items.CustomMaterialInit;
import net.pokepandamon.strife3.items.ModItemGroups;
import net.pokepandamon.strife3.items.ModItems;
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
		protectedAreas.add(new Area(-10,-10,-10,10,-12,10));

		UseBlockCallback.EVENT.register(this::onBlockUse);
		PlayerBlockBreakEvents.BEFORE.register(this::onBlockBreak);
		UseEntityCallback.EVENT.register(this::onEntityUse);
		AttackEntityCallback.EVENT.register(this::onEntityAttack);
		//Registry.register(Registry.SOUND_EVENT, CUSTOM_MUSIC, CUSTOM_MUSIC_EVENT);

		//ServerBlockEntityEvents

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

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("givetesteffect")
				.executes(context -> {
					context.getSource().getPlayer().addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 50, 0, false, false));
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

	private ActionResult onBlockUse(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult){
		//LOGGER.info("Ran this");
		for(Area i : protectedAreas){
			if(i.inArea(hitResult.getBlockPos())){
				return ActionResult.FAIL;
			}
		}

		return ActionResult.PASS;
	}

	private boolean onBlockBreak(World world, PlayerEntity player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {
		//LOGGER.info("Ran this");
		for(Area i : protectedAreas){
			if(i.inArea(blockPos)){
				//LOGGER.info("Ran that");
				return false;
				//return ActionResult.FAIL;
			}
		}
		return true;
		//return ActionResult.PASS;
	}

	private ActionResult onEntityUse(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
		for(Area i : protectedAreas){
			if(i.inArea(entity)){
				return ActionResult.FAIL;
			}
		}
		return ActionResult.SUCCESS;
	}

	private ActionResult onEntityAttack(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
		if(entity instanceof ItemFrameEntity) {
			for (Area i : protectedAreas) {
				if (i.inArea(entity)) {
					return ActionResult.FAIL;
				}
			}
		}
		return ActionResult.SUCCESS;
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