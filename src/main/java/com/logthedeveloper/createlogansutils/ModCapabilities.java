package com.logthedeveloper.createlogansutils;

import com.logthedeveloper.createlogansutils.block.entity.ModBlockEntities;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {

    // Registered on the mod event bus (see CreateLogansUtils constructor).
    // This is what tells Create's pump - or any other fluid pipe mod - that
    // this block entity can be drained as a fluid source.
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.INFINITE_LAVA_SOURCE.get(),
                (blockEntity, side) -> blockEntity.getFluidHandler(side)
        );
    }
}
