package com.logthedeveloper.createlogansutils;

import com.logthedeveloper.createlogansutils.block.entity.ModBlockEntities;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {

    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.INFINITE_LAVA_SOURCE.get(),
                (blockEntity, side) -> blockEntity.getFluidHandler(side)
        );

        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.UPGRADEABLE_TANK.get(),
                (blockEntity, side) -> blockEntity.getTank()
        );
    }

}