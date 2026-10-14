package com.logthedeveloper.createlogansutils.worldgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModOverworldRegion extends Region {
    public ModOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry,
                          Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(Biomes.MUSHROOM_FIELDS, ModBiomes.REDSTONE_WASTELANDS);

            // jungles
            builder.replaceBiome(Biomes.JUNGLE, ModBiomes.REDSTONE_WASTELANDS);
            builder.replaceBiome(Biomes.SPARSE_JUNGLE, ModBiomes.REDSTONE_WASTELANDS);
            builder.replaceBiome(Biomes.BAMBOO_JUNGLE, ModBiomes.REDSTONE_WASTELANDS);

            // spruce
            builder.replaceBiome(Biomes.TAIGA, ModBiomes.REDSTONE_WASTELANDS);
            builder.replaceBiome(Biomes.OLD_GROWTH_PINE_TAIGA, ModBiomes.REDSTONE_WASTELANDS);
            builder.replaceBiome(Biomes.OLD_GROWTH_SPRUCE_TAIGA, ModBiomes.REDSTONE_WASTELANDS);
        });
    }
}