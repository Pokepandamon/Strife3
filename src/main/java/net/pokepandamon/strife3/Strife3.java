package net.pokepandamon.strife3;

import net.fabricmc.api.ModInitializer;

import net.pokepandamon.strife3.items.ModItemGroups;
import net.pokepandamon.strife3.items.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Strife3 implements ModInitializer {
	public static final String MOD_ID = "strife3";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
	}
}