package com.logthedeveloper.createlogansutils.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class CigaretteClientExtensions implements IClientItemExtensions {

    // First person: raise the hand to the face
    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
                                           ItemStack stack, float partialTick, float equipProcess, float swingProcess) {
        if (!player.isUsingItem() || !player.getUseItem().is(stack.getItem())) return false;

        int side = arm == HumanoidArm.RIGHT ? 1 : -1;
        float used = stack.getUseDuration(player) - (player.getUseItemRemainingTicks() - partialTick + 1.0F);
        float raise = Mth.clamp(used / 8.0F, 0.0F, 1.0F);
        raise = raise * raise * (3.0F - 2.0F * raise);          // ease in
        float puff = Mth.sin(used * 0.3F) * 0.015F * raise;     // small inhale motion

        // vanilla base position
        poseStack.translate(side * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
        // move toward the mouth
        poseStack.translate(-side * 0.25F * raise, 0.45F * raise, 0.1F * raise + puff);
        poseStack.mulPose(Axis.XP.rotationDegrees(-60.0F * raise));
        poseStack.mulPose(Axis.YP.rotationDegrees(side * -25.0F * raise));
        return true;
    }

    // Third person: arm raised to the head
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (entity.isUsingItem() && entity.getUsedItemHand() == hand) {
            return HumanoidModel.ArmPose.TOOT_HORN;
        }
        return null;
    }
}