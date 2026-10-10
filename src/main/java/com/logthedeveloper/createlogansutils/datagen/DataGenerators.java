package com.logthedeveloper.createlogansutils.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class DataGenerators {

    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var helper = event.getExistingFileHelper();

        // your existing loot provider
        generator.addProvider(
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

        // new providers
        generator.addProvider(event.includeServer(),
                new ModWorldGenProvider(packOutput, lookupProvider));

        ModBlockTagProvider blockTags = generator.addProvider(event.includeServer(),
                new ModBlockTagProvider(packOutput, lookupProvider, helper));
        generator.addProvider(event.includeServer(),
                new ModItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), helper));

        generator.addProvider(event.includeClient(),
                new ModBlockStateProvider(packOutput, helper));
    }
}