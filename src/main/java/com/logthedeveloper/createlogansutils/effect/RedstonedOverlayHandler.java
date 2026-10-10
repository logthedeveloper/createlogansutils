package com.logthedeveloper.createlogansutils.effect;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = CreateLogansUtils.MODID, value = Dist.CLIENT)
public class RedstonedOverlayHandler {

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        MobEffectInstance instance = mc.player.getEffect(ModMobEffects.REDSTONED);
        if (instance == null) return;

        int amplifier = instance.getAmplifier(); // 0..4
        float alpha = 0.05f + (amplifier * 0.05f); // 0.05 to 0.25

        GuiGraphics graphics = event.getGuiGraphics();
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        int color = ((int) (alpha * 255) << 24) | 0xFF0000; // translucent red, ARGB
        graphics.fill(0, 0, width, height, color);
    }
}