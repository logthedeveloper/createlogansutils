package com.logthedeveloper.createlogansutils.worldgen;

import com.logthedeveloper.createlogansutils.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource dirt = state(ModBlocks.REDSTONE_DIRT.get());
        SurfaceRules.RuleSource grass = state(ModBlocks.REDSTONE_GRASS.get());
        SurfaceRules.ConditionSource aboveWater = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource top = SurfaceRules.sequence(
                SurfaceRules.ifTrue(aboveWater, grass), dirt);

        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.REDSTONE_WASTELANDS),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, top),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, dirt)));
    }

    private static SurfaceRules.RuleSource state(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}