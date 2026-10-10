package com.logthedeveloper.createlogansutils.block;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import com.logthedeveloper.createlogansutils.worldgen.ModTreeGrowers;
import com.logthedeveloper.createlogansutils.block.InfiniteLavaSourceBlock;
import net.minecraft.world.level.material.PushReaction;
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
    public static final DeferredBlock<InfiniteLavaSourceBlock> INFINITE_LAVA_SOURCE =
            BLOCKS.register("infinite_lava_source", () -> new InfiniteLavaSourceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.NETHER)
                            .strength(2.0f)
                            .lightLevel(state -> 15)
            ));
    public static final DeferredBlock<MechanicalJuicerBlock> MECHANICAL_JUICER =
            BLOCKS.register("mechanical_juicer", () -> new MechanicalJuicerBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(2.0F)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()));
    public static final DeferredBlock<Block> BLOCK_OF_CHEESE =
            BLOCKS.registerBlock("block_of_cheese", properties -> new Block(
                    properties.mapColor(MapColor.COLOR_YELLOW)
                            .strength(1.0F)
                            .sound(SoundType.SLIME_BLOCK)
            ));
    public static final DeferredBlock<MintBlock> MINT = BLOCKS.register("mint",
            () -> new MintBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)
            )
    );
    public static final DeferredBlock<MintCropBlock> MINT_CROP =
            BLOCKS.register("mint_crop", () -> new MintCropBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.CROP)
                            .randomTicks()
            ));
    // ---- Redstone Wastelands ----
    public static final DeferredBlock<Block> REDSTONE_DIRT = BLOCKS.register("redstone_dirt",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final DeferredBlock<Block> REDSTONE_GRASS = BLOCKS.register("redstone_grass",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).sound(SoundType.GRASS)));

    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_REDSTONE_LOG = BLOCKS.register("stripped_redstone_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_REDSTONE_WOOD = BLOCKS.register("stripped_redstone_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> REDSTONE_LOG = BLOCKS.register("redstone_log",
            () -> new ModStrippableLog(STRIPPED_REDSTONE_LOG, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<RotatedPillarBlock> REDSTONE_WOOD = BLOCKS.register("redstone_wood",
            () -> new ModStrippableLog(STRIPPED_REDSTONE_WOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> REDSTONE_PLANKS = BLOCKS.register("redstone_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<StairBlock> REDSTONE_STAIRS = BLOCKS.register("redstone_stairs",
            () -> new StairBlock(REDSTONE_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
    public static final DeferredBlock<SlabBlock> REDSTONE_SLAB = BLOCKS.register("redstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<FenceBlock> REDSTONE_FENCE = BLOCKS.register("redstone_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<FenceGateBlock> REDSTONE_FENCE_GATE = BLOCKS.register("redstone_fence_gate",
            () -> new FenceGateBlock(ModWoodTypes.REDSTONE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<DoorBlock> REDSTONE_DOOR = BLOCKS.register("redstone_door",
            () -> new DoorBlock(ModWoodTypes.REDSTONE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<TrapDoorBlock> REDSTONE_TRAPDOOR = BLOCKS.register("redstone_trapdoor",
            () -> new TrapDoorBlock(ModWoodTypes.REDSTONE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<ButtonBlock> REDSTONE_BUTTON = BLOCKS.register("redstone_button",
            () -> new ButtonBlock(ModWoodTypes.REDSTONE_SET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<PressurePlateBlock> REDSTONE_PRESSURE_PLATE = BLOCKS.register("redstone_pressure_plate",
            () -> new PressurePlateBlock(ModWoodTypes.REDSTONE_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    public static final DeferredBlock<StandingSignBlock> REDSTONE_SIGN = BLOCKS.register("redstone_sign",
            () -> new StandingSignBlock(ModWoodTypes.REDSTONE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<WallSignBlock> REDSTONE_WALL_SIGN = BLOCKS.register("redstone_wall_sign",
            () -> new WallSignBlock(ModWoodTypes.REDSTONE,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).dropsLike(REDSTONE_SIGN.get())));
    public static final DeferredBlock<CeilingHangingSignBlock> REDSTONE_HANGING_SIGN = BLOCKS.register("redstone_hanging_sign",
            () -> new CeilingHangingSignBlock(ModWoodTypes.REDSTONE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final DeferredBlock<WallHangingSignBlock> REDSTONE_WALL_HANGING_SIGN = BLOCKS.register("redstone_wall_hanging_sign",
            () -> new WallHangingSignBlock(ModWoodTypes.REDSTONE,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).dropsLike(REDSTONE_HANGING_SIGN.get())));

    public static final DeferredBlock<LeavesBlock> REDSTONE_LEAVES = BLOCKS.register("redstone_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<SaplingBlock> REDSTONE_SAPLING = BLOCKS.register("redstone_sapling",
            () -> new SaplingBlock(ModTreeGrowers.REDSTONE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    /** Called from commonSetup inside enqueueWork. */
    public static void registerFlammability() {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        for (Block b : new Block[]{REDSTONE_LOG.get(), STRIPPED_REDSTONE_LOG.get(),
                REDSTONE_WOOD.get(), STRIPPED_REDSTONE_WOOD.get()}) {
            fire.setFlammable(b, 5, 5);
        }
        for (Block b : new Block[]{REDSTONE_PLANKS.get(), REDSTONE_STAIRS.get(), REDSTONE_SLAB.get(),
                REDSTONE_FENCE.get(), REDSTONE_FENCE_GATE.get()}) {
            fire.setFlammable(b, 5, 20);
        }
        fire.setFlammable(REDSTONE_LEAVES.get(), 30, 60);
    }


    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}