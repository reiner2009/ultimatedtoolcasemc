package de.reiner.toolcasemc.client.render.entity.knive;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.reiner.toolcasemc.UltimatedToolCaseMC;
import de.reiner.toolcasemc.client.render.entity.ModEntityModelLayers;
import de.reiner.toolcasemc.entity.ThrownKnive;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;

public class ThrownKniveRenderer extends EntityRenderer<ThrownKnive, ThrownKniveRenderState> {
    public static final Identifier KNIVE_LOCATION = Identifier.fromNamespaceAndPath(UltimatedToolCaseMC.MOD_ID, "textures/item/knive.png");
    private final ThrownKniveEntityModel model;

    public ThrownKniveRenderer(final EntityRendererProvider.Context context) {
        super(context);
        this.model = new ThrownKniveEntityModel(context.bakeLayer(ModEntityModelLayers.KNIVE));
    }

    public void submit(final ThrownKniveRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.rotateDegrees(Axis.YP, state.yRot - 90.0F);
        poseStack.rotateDegrees(Axis.ZP, state.xRot + 90.0F);
        submitNodeCollector.submitModel(this.model, state, poseStack, this.KNIVE_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    protected AABB getBoundingBoxForCulling(final ThrownKnive entity, final float partialTicks) {
        return super.getBoundingBoxForCulling(entity, partialTicks).inflate(1.5F);
    }

    public ThrownKniveRenderState createRenderState() {
        return new ThrownKniveRenderState();
    }

    public void extractRenderState(final ThrownKnive entity, final ThrownKniveRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yRot = entity.getYRot(partialTicks);
        state.xRot = entity.getXRot(partialTicks);
    }
}
