package com.logthedeveloper.createlogansutils.worldgen;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public class ModTerraBlender {
    public static void register() {
        Regions.register(new ModOverworldRegion(
                ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "overworld"), 2));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,
                CreateLogansUtils.MODID, ModSurfaceRules.makeRules());
    }
}