package de.reiner.toolcasemc;

import de.reiner.toolcasemc.block.ModBlockEntityType;
import de.reiner.toolcasemc.block.ModBlocks;
import de.reiner.toolcasemc.entity.ModEntityTypes;
import de.reiner.toolcasemc.inventory.ModMenuType;
import de.reiner.toolcasemc.item.ModItemGroups;
import de.reiner.toolcasemc.item.ModItems;
import de.reiner.toolcasemc.stats.ModStats;
import de.reiner.toolcasemc.tag.ModTags;
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
        ModEntityTypes.init();
        ModTags.init();
        ModBlocks.init();
        ModBlockEntityType.init();
        ModStats.init();
        ModMenuType.init();
		LOGGER.info("Initialize "+MOD_ID);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
