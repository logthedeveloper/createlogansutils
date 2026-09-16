package com.logthedeveloper.createlogansutils.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import com.logthedeveloper.createlogansutils.CreateLogansUtils;

public class MintTeaItem extends Item {

    public MintTeaItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide) {
            // Regeneration V (amplifier 4) for 15 seconds (15 * 20 = 300 ticks)
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 4));

            // Jump Boost II (amplifier 1) for 5 seconds (5 * 20 = 100 ticks)
            entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1));
        }

        // Handles returning the empty cup (or keeping stack sizes accurate in Creative mode)
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(CreateLogansUtils.EMPTY_CUP.get());
            }
            player.getInventory().add(new ItemStack(CreateLogansUtils.EMPTY_CUP.get()));
        }

        return result;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}