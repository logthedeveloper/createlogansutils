package com.logthedeveloper.createlogansutils.worldgen;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> REDSTONE_TREE = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "redstone_tree_placed"));

    public static void bootstrap(BootstrapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> features = ctx.lookup(Registries.CONFIGURED_FEATURE);
        ctx.register(REDSTONE_TREE, new PlacedFeature(
                features.getOrThrow(ModConfiguredFeatures.REDSTONE_TREE),
                VegetationPlacements.treePlacement(
                        PlacementUtils.countExtra(0, 0.05f, 1),   // sparse: wasteland
                        ModBlocks.REDSTONE_SAPLING.get())));
    }
}