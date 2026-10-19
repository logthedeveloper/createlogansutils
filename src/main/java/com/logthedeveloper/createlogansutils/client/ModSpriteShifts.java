package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import net.minecraft.resources.ResourceLocation;

public class ModSpriteShifts {
    public static final CTSpriteShiftEntry CREATIVE_CASING = CTSpriteShifter.getCT(
        AllCTTypes.OMNIDIRECTIONAL,
        ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "block/creative_casing"),
        ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "block/creative_casing_connected"));
}