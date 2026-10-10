package com.logthedeveloper.createlogansutils.worldgen;

import net.minecraft.world.level.block.grower.TreeGrower;
import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower REDSTONE = new TreeGrower("redstone",
            Optional.empty(), Optional.of(ModConfiguredFeatures.REDSTONE_TREE), Optional.empty());
}