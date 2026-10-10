package com.logthedeveloper.createlogansutils.effect;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.attachment.ModAttachments;
import com.logthedeveloper.createlogansutils.fluid.ModFluids;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CreateLogansUtils.MODID)
public class RedstoneExposureHandler {
    public static final int MAX_LEVEL = 4; // caps the amplifier at 4 (level 5)
    private static final int TICKS_PER_LEVEL = 100; // ~5 seconds submerged = +1 level
    private static final int DECAY_INTERVAL = 600; // ~30s clean = -1 level

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) return;

        FluidState fluidState = level.getFluidState(player.blockPosition());
        boolean inRedstone = !fluidState.isEmpty()
                && fluidState.getType().getFluidType() == ModFluids.LIQUID_REDSTONE_TYPE.get();

        int exposure = player.getData(ModAttachments.REDSTONE_EXPOSURE);

        if (inRedstone) {
            if (player.tickCount % TICKS_PER_LEVEL == 0 && exposure < MAX_LEVEL) {
                exposure++;
                player.setData(ModAttachments.REDSTONE_EXPOSURE, exposure);
            }
            applyRedstoned(player, exposure);
        } else if (exposure > 0 && player.tickCount % DECAY_INTERVAL == 0) {
            player.setData(ModAttachments.REDSTONE_EXPOSURE, exposure - 1);
        }
    }

    public static void applyRedstoned(Player player, int amplifier) {
        player.addEffect(new MobEffectInstance(ModMobEffects.REDSTONED, 400, Math.min(amplifier, MAX_LEVEL)));
    }
}