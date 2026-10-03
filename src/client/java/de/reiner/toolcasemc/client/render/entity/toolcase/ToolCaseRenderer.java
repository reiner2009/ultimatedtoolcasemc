package de.reiner.toolcasemc.client.render.entity.toolcase;

import com.mojang.blaze3d.vertex.PoseStack;
import de.reiner.toolcasemc.block.ToolCaseBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.Vec3;

public class ToolCaseRenderer implements BlockEntityRenderer<ToolCaseBlockEntity, ToolCaseRenderState> {

    public ToolCaseRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public ToolCaseRenderState createRenderState() {
        return new ToolCaseRenderState();
    }

    @Override
    public void extractRenderState(ToolCaseBlockEntity blockEntity, ToolCaseRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
    }

    @Override
    public void submit(ToolCaseRenderState state, PoseStack poseStack, SubmitNodeCollector queue, CameraRenderState cameraState) {
    }
}