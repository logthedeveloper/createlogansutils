package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.block.entity.MechanicalJuicerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalJuicerRenderer extends KineticBlockEntityRenderer<MechanicalJuicerBlockEntity> {

    public MechanicalJuicerRenderer(BlockEntityRendererProvider.Context context) { super(context); }

    @Override public boolean shouldRenderOffScreen(MechanicalJuicerBlockEntity be) { return true; }

    @Override
    protected void renderSafe(MechanicalJuicerBlockEntity be, float partialTicks, PoseStack ms,
                              MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (VisualizationManager.supportsVisualization(be.getLevel()))
            return;

        BlockState state = be.getBlockState();
        PressingBehaviour pb = be.getPressingBehaviour();
        float offset = pb.getRenderedHeadOffset(partialTicks) * pb.mode.headOffset;

        SuperByteBuffer head = CachedBuffers.partialFacing(ModPartialModels.JUICER_HEAD, state,
                state.getValue(HorizontalKineticBlock.HORIZONTAL_FACING));
        head.translate(0, -offset, 0).light(light).renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

    @Override
    protected BlockState getRenderedBlockState(MechanicalJuicerBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }
}