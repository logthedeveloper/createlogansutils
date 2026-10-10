package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.block.entity.MechanicalJuicerBlockEntity;
import com.logthedeveloper.createlogansutils.block.entity.ModBlockEntities;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.world.level.material.FogType;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = CreateLogansUtils.MODID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.INFINITE_LAVA_SOURCE.get(), InfiniteLavaSourceRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MECHANICAL_JUICER.get(), MechanicalJuicerRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
                SimpleBlockEntityVisualizer.builder(ModBlockEntities.MECHANICAL_JUICER.get())
                        .factory(MechanicalJuicerVisual::new)
                        .apply());
    }

    private static final ResourceLocation REDSTONE_WASTELANDS =
            ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "redstone_wastelands");

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        // Don't override underwater / lava / powder snow fog
        if (event.getType() != FogType.NONE) return;

        var level = Minecraft.getInstance().level;
        if (level == null) return;

        if (level.getBiome(event.getCamera().getBlockPosition()).is(REDSTONE_WASTELANDS)) {
            event.setNearPlaneDistance(0.0F);
            event.setFarPlaneDistance(32.0F); // lower = thicker fog
            event.setFogShape(FogShape.SPHERE);
            event.setCanceled(true); // required or the values are ignored
        }
    }
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        Item cigarette = BuiltInRegistries.ITEM.get(
                ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "redstone_cigarette"));
        event.registerItem(new CigaretteClientExtensions(), cigarette);
    }
    @SubscribeEvent
    public static void onPlaySound(net.neoforged.neoforge.client.event.sound.PlaySoundEvent event) {
        var sound = event.getSound();
        if (sound == null) return;
        var id = sound.getLocation();
        if (!id.getNamespace().equals("create") || !id.getPath().startsWith("mechanical_press_activation")) return;
        var level = net.minecraft.client.Minecraft.getInstance().level;
        if (level == null) return;
        var pos = net.minecraft.core.BlockPos.containing(sound.getX(), sound.getY(), sound.getZ());
        if (level.getBlockEntity(pos) instanceof MechanicalJuicerBlockEntity)
            event.setSound(null);
    }
}