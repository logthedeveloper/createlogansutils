package com.logthedeveloper.createlogansutils.effect;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RedstonedMobEffect extends MobEffect {

    public RedstonedMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF2222);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20, 0, true, false, false));
        return true;
    }
}