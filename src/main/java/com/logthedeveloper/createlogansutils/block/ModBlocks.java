package com.logthedeveloper.createlogansutils.block;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateLogansUtils.MODID);

    public static final DeferredBlock<YoungCheeseBinBlock> YOUNG_CHEESE_BIN =
            BLOCKS.register("young_cheese_bin", () -> new YoungCheeseBinBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_YELLOW)
                            .strength(0.6F)
                            .sound(SoundType.WOOD)
                            .noOcclusion()));

    public static final DeferredBlock<AgedCheeseBinBlock> AGED_CHEESE_BIN =
            BLOCKS.register("aged_cheese_bin", () -> new AgedCheeseBinBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_YELLOW)
                            .strength(0.6F)
                            .sound(SoundType.WOOD)
                            .noOcclusion()));

    public static final DeferredBlock<Block> BLOCK_OF_CHEESE =
            BLOCKS.registerBlock("block_of_cheese", properties -> new Block(
                    properties.mapColor(MapColor.COLOR_YELLOW)
                            .strength(1.0F)
                            .sound(SoundType.SLIME_BLOCK)
            ));


    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}