package com.logthedeveloper.createlogansutils.datagen;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import com.logthedeveloper.createlogansutils.block.MintCropBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootSubProvider extends BlockLootSubProvider {

    public ModBlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // Normal blocks drop themselves
        this.dropSelf(ModBlocks.YOUNG_CHEESE_BIN.get());
        this.dropSelf(ModBlocks.AGED_CHEESE_BIN.get());
        this.dropSelf(ModBlocks.BLOCK_OF_CHEESE.get());
        this.dropSelf(ModBlocks.MINT.get());

        // Mint Crop drops its seeds when young, and mint + seeds when mature (Age 3)
        this.add(ModBlocks.MINT_CROP.get(),
                block -> createCropDrops(
                        ModBlocks.MINT_CROP.get(),
                        CreateLogansUtils.MINT.get(),
                        CreateLogansUtils.MINT_SEEDS.get(),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.MINT_CROP.get())
                                .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(MintCropBlock.AGE, 3))
                )
        );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(DeferredHolder::value)
                .collect(Collectors.toList());
    }
}
