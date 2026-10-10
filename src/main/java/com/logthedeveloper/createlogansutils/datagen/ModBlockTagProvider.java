package com.logthedeveloper.createlogansutils.datagen;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                               @Nullable ExistingFileHelper helper) {
        super(output, lookup, CreateLogansUtils.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Block[] logs = {ModBlocks.REDSTONE_LOG.get(), ModBlocks.STRIPPED_REDSTONE_LOG.get(),
                ModBlocks.REDSTONE_WOOD.get(), ModBlocks.STRIPPED_REDSTONE_WOOD.get()};
        tag(BlockTags.LOGS).add(logs);
        tag(BlockTags.LOGS_THAT_BURN).add(logs);

        tag(BlockTags.PLANKS).add(ModBlocks.REDSTONE_PLANKS.get());
        tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.REDSTONE_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(ModBlocks.REDSTONE_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(ModBlocks.REDSTONE_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.REDSTONE_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(ModBlocks.REDSTONE_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.REDSTONE_TRAPDOOR.get());
        tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.REDSTONE_BUTTON.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.REDSTONE_PRESSURE_PLATE.get());
        tag(BlockTags.STANDING_SIGNS).add(ModBlocks.REDSTONE_SIGN.get());
        tag(BlockTags.WALL_SIGNS).add(ModBlocks.REDSTONE_WALL_SIGN.get());
        tag(BlockTags.CEILING_HANGING_SIGNS).add(ModBlocks.REDSTONE_HANGING_SIGN.get());
        tag(BlockTags.WALL_HANGING_SIGNS).add(ModBlocks.REDSTONE_WALL_HANGING_SIGN.get());
        tag(BlockTags.LEAVES).add(ModBlocks.REDSTONE_LEAVES.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.REDSTONE_SAPLING.get());

        // saplings only survive on blocks in this tag
        tag(BlockTags.DIRT).add(ModBlocks.REDSTONE_DIRT.get(), ModBlocks.REDSTONE_GRASS.get());

        tag(BlockTags.MINEABLE_WITH_AXE).add(logs).add(
                ModBlocks.REDSTONE_PLANKS.get(), ModBlocks.REDSTONE_STAIRS.get(), ModBlocks.REDSTONE_SLAB.get(),
                ModBlocks.REDSTONE_FENCE.get(), ModBlocks.REDSTONE_FENCE_GATE.get(), ModBlocks.REDSTONE_DOOR.get(),
                ModBlocks.REDSTONE_TRAPDOOR.get(), ModBlocks.REDSTONE_BUTTON.get(),
                ModBlocks.REDSTONE_PRESSURE_PLATE.get(), ModBlocks.REDSTONE_SIGN.get(),
                ModBlocks.REDSTONE_WALL_SIGN.get(), ModBlocks.REDSTONE_HANGING_SIGN.get(),
                ModBlocks.REDSTONE_WALL_HANGING_SIGN.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.REDSTONE_LEAVES.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.REDSTONE_DIRT.get(), ModBlocks.REDSTONE_GRASS.get());
    }
}