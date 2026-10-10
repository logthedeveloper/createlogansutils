package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.block.entity.MechanicalJuicerBlockEntity;
import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;

import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import org.joml.Quaternionf;

import java.util.function.Consumer;

public class MechanicalJuicerVisual extends ShaftVisual<MechanicalJuicerBlockEntity> implements SimpleDynamicVisual {
    private final OrientedInstance head;

    public MechanicalJuicerVisual(VisualizationContext ctx, MechanicalJuicerBlockEntity be, float partialTick) {
        super(ctx, be, partialTick);
        head = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(ModPartialModels.JUICER_HEAD))
                .createInstance();
        Quaternionf q = Axis.YP.rotationDegrees(
                AngleHelper.horizontalAngle(blockState.getValue(HorizontalKineticBlock.HORIZONTAL_FACING)));
        head.rotation(q);
        transformModels(partialTick);
    }

    @Override public void beginFrame(DynamicVisual.Context ctx) { transformModels(ctx.partialTick()); }

    private void transformModels(float pt) {
        PressingBehaviour pb = blockEntity.getPressingBehaviour();
        float offset = pb.getRenderedHeadOffset(pt) * pb.mode.headOffset;
        head.position(getVisualPosition()).translatePosition(0, -offset, 0).setChanged();
    }

    @Override public void updateLight(float partialTick) { super.updateLight(partialTick); relight(head); }
    @Override protected void _delete() { super._delete(); head.delete(); }
    @Override public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        consumer.accept(head);
    }
}