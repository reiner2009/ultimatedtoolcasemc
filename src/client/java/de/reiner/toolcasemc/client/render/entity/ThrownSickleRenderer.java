package de.reiner.toolcasemc.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.reiner.toolcasemc.UltimatedToolCaseMC;
import de.reiner.toolcasemc.entity.ThrownSickle;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;

public class ThrownSickleRenderer extends EntityRenderer<ThrownSickle, ThrownSickleRenderState> {
    public static final Identifier SICKLE_LOCATION = Identifier.fromNamespaceAndPath(UltimatedToolCaseMC.MOD_ID, "textures/item/sickle.png");
    private final ThrownSickleEntityModel model;

    public ThrownSickleRenderer(final EntityRendererProvider.Context context) {
        super(context);
        this.model = new ThrownSickleEntityModel(context.bakeLayer(ModEntityModelLayers.SICKLE));
    }

    public void submit(final ThrownSickleRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.rotateDegrees(Axis.YP, state.yRot - 90.0F);
        poseStack.rotateDegrees(Axis.ZP, state.xRot + 90.0F);
        submitNodeCollector.submitModel(this.model, state, poseStack, this.SICKLE_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    protected AABB getBoundingBoxForCulling(final ThrownSickle entity, final float partialTicks) {
        return super.getBoundingBoxForCulling(entity, partialTicks).inflate(1.5F);
    }

    public ThrownSickleRenderState createRenderState() {
        return new ThrownSickleRenderState();
    }

    public void extractRenderState(final ThrownSickle entity, final ThrownSickleRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yRot = entity.getYRot(partialTicks);
        state.xRot = entity.getXRot(partialTicks);
    }
}
