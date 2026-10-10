package com.logthedeveloper.createlogansutils.worldgen;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.joml.Vector3f;

public class ModBiomes {
    public static final ResourceKey<Biome> REDSTONE_WASTELANDS = ResourceKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "redstone_wastelands"));

    public static void bootstrap(BootstrapContext<Biome> ctx) {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
                ctx.lookup(Registries.PLACED_FEATURE), ctx.lookup(Registries.CONFIGURED_CARVER));
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
        BiomeDefaultFeatures.addDefaultOres(gen);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.REDSTONE_TREE);

        ctx.register(REDSTONE_WASTELANDS, new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(1.5f)
                .downfall(0.0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(0x7F2A2A)
                        .fogColor(0x4B0F0F)
                        .waterColor(0x4A1A1A)
                        .waterFogColor(0x1A0505)
                        .ambientParticle(new AmbientParticleSettings(
                                new DustParticleOptions(new Vector3f(1.0f, 0.1f, 0.1f), 1.0f), 0.005f))
                        .build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(gen.build())
                .build());
    }
}