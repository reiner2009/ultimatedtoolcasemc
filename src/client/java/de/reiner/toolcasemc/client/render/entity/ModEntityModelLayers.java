package de.reiner.toolcasemc.client.render.entity;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModEntityModelLayers {
    public static final ModelLayerLocation SICKLE = createMain("sickle");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(UltimatedToolCaseMC.id(name), "main");
    }

    public static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SICKLE, ThrownSickleEntityModel::createLayer);
    }
}