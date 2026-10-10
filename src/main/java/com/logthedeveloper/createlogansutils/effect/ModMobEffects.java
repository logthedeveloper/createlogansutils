package com.logthedeveloper.createlogansutils.effect;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, CreateLogansUtils.MODID);

    public static final DeferredHolder<MobEffect, RedstonedMobEffect> REDSTONED =
            MOB_EFFECTS.register("redstoned", RedstonedMobEffect::new);

    public static void register(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }
}