package de.reiner.toolcasemc.client.render.entity.sickle;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
public class ThrownSickleEntityModel extends EntityModel<ThrownSickleRenderState> {

    public ThrownSickleEntityModel(final ModelPart root) {
        super(root, RenderTypes::entityCutout);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("sickle", CubeListBuilder.create().texOffs(-32, 0).addBox(-16.0F, 8.0F, -16.0F, 32.0F, 0.0F, 32.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 90.0F, 90.0F, 0.0F));
        return LayerDefinition.create(mesh, 32, 32);
    }
}