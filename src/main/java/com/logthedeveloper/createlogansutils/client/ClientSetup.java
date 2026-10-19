package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import com.logthedeveloper.createlogansutils.block.ModWoodTypes;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.CreateClient;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(ModWoodTypes.REDSTONE);

            CreateClient.MODEL_SWAPPER.getCustomBlockModels().register(
                    ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "creative_casing"),
                    model -> new CTModel(model, new EncasedCTBehaviour(ModSpriteShifts.CREATIVE_CASING)));

            CreateClient.CASING_CONNECTIVITY.make(
                    ModBlocks.CREATIVE_CASING.get(), ModSpriteShifts.CREATIVE_CASING);
        });
    }
}