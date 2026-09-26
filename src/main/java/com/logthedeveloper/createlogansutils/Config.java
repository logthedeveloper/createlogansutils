package com.logthedeveloper.createlogansutils;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_INFINITE_LAVA_SOURCE = BUILDER
            .comment("Whether the Infinite Lava Source block is functional and shown in the creative menu")
            .define("enableInfiniteLavaSource", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}