package de.reiner.toolcasemc.client;

import de.reiner.toolcasemc.client.render.entity.ModEntityModelLayers;
import de.reiner.toolcasemc.client.render.entity.knive.ThrownKniveRenderer;
import de.reiner.toolcasemc.client.render.entity.sickle.ThrownSickleRenderer;
import de.reiner.toolcasemc.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class UltimatedToolCaseMCClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        EntityRenderers.register(ModEntityTypes.SICKLE, ThrownSickleRenderer::new);
        EntityRenderers.register(ModEntityTypes.KNIVE, ThrownKniveRenderer::new);
        ModEntityModelLayers.registerModelLayers();
	}
}