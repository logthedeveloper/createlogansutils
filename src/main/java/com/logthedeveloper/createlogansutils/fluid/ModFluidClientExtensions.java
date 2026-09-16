package com.logthedeveloper.createlogansutils.fluid;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class ModFluidClientExtensions {

    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "block/lava_still");
            }
            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "block/lava_flow");
            }
            @Override
            public int getTintColor() {
                return 0xFFFF3333;
            }
        }, ModFluids.LIQUID_REDSTONE_TYPE.get());
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still");
            }
            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow");
            }
            @Override
            public int getTintColor() {
                return 0xFF7FE080; // minty green
            }
        }, ModFluids.LIQUID_MINT_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still");
            }
            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow");
            }
            @Override
            public int getTintColor() {
                return 0xFFFFFF00; // fuh you we do water
            }
        }, ModFluids.LIQUID_CHEESE_TYPE.get());
    }
}