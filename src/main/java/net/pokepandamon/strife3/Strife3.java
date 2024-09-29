package net.pokepandamon.strife3;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.effects.MorphineEffect;
import net.pokepandamon.strife3.items.ArmorMaterialInit;
import net.pokepandamon.strife3.items.ModItemGroups;
import net.pokepandamon.strife3.items.ModItems;
import net.pokepandamon.strife3.items.custom.DemonSword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Strife3 implements ModInitializer {
	public static final String MOD_ID = "strife3";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final StatusEffect MORPHINE;

	static {
		MORPHINE = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "morphine"), new MorphineEffect());
	}

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ArmorMaterialInit.load();
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

	public static Identifier id(String path) { return Identifier.of(MOD_ID, path);}

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