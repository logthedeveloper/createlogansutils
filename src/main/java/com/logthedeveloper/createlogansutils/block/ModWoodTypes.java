package com.logthedeveloper.createlogansutils.block;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final BlockSetType REDSTONE_SET =
            BlockSetType.register(new BlockSetType("createlogansutils_redstone"));
    public static final WoodType REDSTONE =
            WoodType.register(new WoodType("createlogansutils:redstone", REDSTONE_SET));
}