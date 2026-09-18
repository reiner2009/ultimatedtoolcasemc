package de.reiner.toolcasemc;

import de.reiner.toolcasemc.item.ModItemGroups;
import de.reiner.toolcasemc.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UltimatedToolCaseMC implements ModInitializer {
	public static final String MOD_ID = "ultimatedtoolcasemc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.init();
        ModItemGroups.init();
		LOGGER.info("Initialize "+MOD_ID);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
