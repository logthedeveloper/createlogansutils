package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = CreateLogansUtils.MODID, value = Dist.CLIENT)
public class RedstonedShaderHandler {
    private static boolean active = false;
    private static final ResourceLocation SHADER =
            ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "redstoned");

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean shouldBeActive = mc.player.hasEffect(ModMobEffects.REDSTONED);

        if (shouldBeActive && !active) {
            mc.gameRenderer.loadEffect(SHADER);
            active = true;
        } else if (!shouldBeActive && active) {
            mc.gameRenderer.shutdownEffect();
            active = false;
        }
    }
}