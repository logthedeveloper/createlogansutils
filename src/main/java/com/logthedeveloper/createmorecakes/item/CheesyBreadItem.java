package com.logthedeveloper.createmorecakes.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CheesyBreadItem extends Item {
    public CheesyBreadItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide) {
            if (entity instanceof Player player) {
                FoodData foodData = player.getFoodData();
                foodData.setFoodLevel(20);
                foodData.setSaturation(20f);
            }

            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0)); // 10 seconds, Regen I

            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.GENERIC_EAT, entity.getSoundSource(), 1.0F, 1.0F);
        }

        return result;
    }
}