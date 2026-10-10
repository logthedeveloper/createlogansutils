package com.logthedeveloper.createlogansutils.compat.jei;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.client.ModPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;


public class AnimatedJuicer extends AnimatedKinetics {

    private static final int SCALE = 24;

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(xOffset, yOffset, 200);
        pose.mulPose(Axis.XP.rotationDegrees(-15.5f));
        pose.mulPose(Axis.YP.rotationDegrees(22.5f));

       // shaft
        blockElement(shaft(Direction.Axis.Z))
                .rotateBlock(0, 0, getCurrentAngle())
                .scale(SCALE)
                .render(graphics);

        // body of the juicer
        blockElement(CreateLogansUtils.MECHANICAL_JUICER.get().getBlock().defaultBlockState())
                .scale(SCALE)
                .render(graphics);

        //  head
        blockElement(ModPartialModels.JUICER_HEAD)
                .atLocal(0, -getAnimatedHeadOffset(), 0)
                .scale(SCALE)
                .render(graphics);

        pose.popPose();
    }


    private float getAnimatedHeadOffset() {
        float cycle = (AnimationTickHolder.getRenderTime() - offset * 8) % 30;
        if (cycle < 10) {
            float progress = cycle / 10;
            return -(progress * progress * progress);
        }
        if (cycle < 15)
            return -1;
        if (cycle < 20)
            return -1 + (1 - ((20 - cycle) / 5));
        return 0;
    }
}