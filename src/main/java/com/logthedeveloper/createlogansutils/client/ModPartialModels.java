package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class ModPartialModels {
    public static final PartialModel JUICER_HEAD = PartialModel.of(
            ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "block/mechanical_juicer/head"));

    public static void init() {
    }
}