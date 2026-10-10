package com.logthedeveloper.createlogansutils.item;

import com.logthedeveloper.createlogansutils.attachment.ModAttachments;
import com.logthedeveloper.createlogansutils.effect.RedstoneExposureHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RedstoneCigaretteItem extends Item {
    public RedstoneCigaretteItem(Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 0.6F, 1.0F);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    // Puffs of smoke near the face while "smoking"
    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide && remainingUseDuration % 4 == 0) {
            Vec3 pos = entity.getEyePosition()
                    .add(entity.getLookAngle().scale(0.4))
                    .add(0, -0.15, 0);
            level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0, 0.02, 0);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            int exposure = Math.min(
                    player.getData(ModAttachments.REDSTONE_EXPOSURE) + 1,
                    RedstoneExposureHandler.MAX_LEVEL
            );
            player.setData(ModAttachments.REDSTONE_EXPOSURE, exposure);
            RedstoneExposureHandler.applyRedstoned(player, exposure);
        }
        if (!(entity instanceof Player player) || !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) { return 32; }

    // NONE = no vanilla chewing bob or crumb particles
    @Override
    public UseAnim getUseAnimation(ItemStack stack) { return UseAnim.NONE; }
}