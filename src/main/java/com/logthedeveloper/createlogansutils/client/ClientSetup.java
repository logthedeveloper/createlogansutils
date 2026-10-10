package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.block.ModWoodTypes;
import net.minecraft.client.renderer.Sheets;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> Sheets.addWoodType(ModWoodTypes.REDSTONE));
    }
}