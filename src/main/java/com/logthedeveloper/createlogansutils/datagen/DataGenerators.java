package com.logthedeveloper.createlogansutils.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class DataGenerators {

    public static void gatherData(GatherDataEvent event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        var lookupProvider = event.getLookupProvider();

        event.getGenerator().addProvider(
                event.includeServer(),
                new LootTableProvider(
                        packOutput,
                        Set.of(),
                        List.of(new LootTableProvider.SubProviderEntry(
                                ModBlockLootSubProvider::new,
                                LootContextParamSets.BLOCK
                        )),
                        lookupProvider
                )
        );
    }
}
