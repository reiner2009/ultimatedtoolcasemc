package de.reiner.toolcasemc.client;

import de.reiner.toolcasemc.block.ModBlockEntityType;
import de.reiner.toolcasemc.client.render.entity.ModEntityModelLayers;
import de.reiner.toolcasemc.client.render.entity.knive.ThrownKniveRenderer;
import de.reiner.toolcasemc.client.render.entity.sickle.ThrownSickleRenderer;
import de.reiner.toolcasemc.client.render.entity.spark.SparkRenderer;
import de.reiner.toolcasemc.client.render.entity.toolcase.ToolCaseRenderer;
import de.reiner.toolcasemc.client.render.gui.ToolCaseScreen;
import de.reiner.toolcasemc.entity.ModEntityTypes;
import de.reiner.toolcasemc.inventory.ModMenuType;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class UltimatedToolCaseMCClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        EntityRenderers.register(ModEntityTypes.SICKLE, ThrownSickleRenderer::new);
        EntityRenderers.register(ModEntityTypes.KNIVE, ThrownKniveRenderer::new);
        EntityRenderers.register(ModEntityTypes.SPARK, SparkRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityType.TOOLCASE_BLOCK_ENTITY, ToolCaseRenderer::new);
        ModEntityModelLayers.registerModelLayers();
        MenuScreens.register(ModMenuType.TOOLCASE, ToolCaseScreen::new);

	}
}