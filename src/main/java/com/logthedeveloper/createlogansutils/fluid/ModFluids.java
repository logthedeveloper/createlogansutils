package com.logthedeveloper.createlogansutils.fluid;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {

    // fluid types
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(
                    NeoForgeRegistries.Keys.FLUID_TYPES,
                    CreateLogansUtils.MODID
            );

    // fluids
    public static final DeferredRegister<net.minecraft.world.level.material.Fluid> FLUIDS =
            DeferredRegister.create(
                    net.minecraft.core.registries.Registries.FLUID,
                    CreateLogansUtils.MODID
            );

    // fluid blocks
    public static final DeferredRegister.Blocks BLOCKS_FLUID =
            DeferredRegister.createBlocks(CreateLogansUtils.MODID);

    // fluid items
    public static final DeferredRegister.Items ITEMS_FLUID =
            DeferredRegister.createItems(CreateLogansUtils.MODID);


    // redstone

    public static final DeferredHolder<FluidType, FluidType> LIQUID_REDSTONE_TYPE =
            FLUID_TYPES.register("liquid_redstone", () -> new ModFluidType(
                    FluidType.Properties.create()
                            .descriptionId("fluid.createlogansutils.liquid_redstone")
                            .canSwim(false)
                            .canDrown(false)
                            .canPushEntity(false)
                            .canExtinguish(false)
                            .canConvertToSource(false)
                            .supportsBoating(false)
                            .density(2500)
                            .viscosity(3000)
                            .temperature(2000)
                            .lightLevel(10)
                            .sound(
                                    SoundActions.BUCKET_FILL,
                                    SoundEvents.BUCKET_FILL_LAVA
                            )
                            .sound(
                                    SoundActions.BUCKET_EMPTY,
                                    SoundEvents.BUCKET_EMPTY_LAVA
                            )
            ));

    public static final DeferredHolder<
            net.minecraft.world.level.material.Fluid,
            BaseFlowingFluid.Source
            > LIQUID_REDSTONE_SOURCE =
            FLUIDS.register(
                    "liquid_redstone",
                    () -> new BaseFlowingFluid.Source(getRedstoneProperties())
            );

    public static final DeferredHolder<
            net.minecraft.world.level.material.Fluid,
            BaseFlowingFluid.Flowing
            > LIQUID_REDSTONE_FLOWING =
            FLUIDS.register(
                    "flowing_liquid_redstone",
                    () -> new BaseFlowingFluid.Flowing(getRedstoneProperties())
            );

    public static final DeferredBlock<LiquidBlock> LIQUID_REDSTONE_BLOCK =
            BLOCKS_FLUID.register(
                    "liquid_redstone",
                    () -> new LiquidBlock(
                            LIQUID_REDSTONE_SOURCE.get(),
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.COLOR_RED)
                                    .noCollission()
                                    .replaceable()
                                    .noLootTable()
                                    .lightLevel(state -> 10)
                    )
            );

    public static final DeferredItem<BucketItem> LIQUID_REDSTONE_BUCKET =
            ITEMS_FLUID.register(
                    "liquid_redstone_bucket",
                    () -> new BucketItem(
                            LIQUID_REDSTONE_SOURCE.get(),
                            new Item.Properties()
                                    .craftRemainder(net.minecraft.world.item.Items.BUCKET)
                                    .stacksTo(1)
                    )
            );

    private static BaseFlowingFluid.Properties getRedstoneProperties() {
        return new BaseFlowingFluid.Properties(
                LIQUID_REDSTONE_TYPE,
                LIQUID_REDSTONE_SOURCE,
                LIQUID_REDSTONE_FLOWING
        )
                .bucket(LIQUID_REDSTONE_BUCKET)
                .block(LIQUID_REDSTONE_BLOCK);
    }


    // ches

    public static final DeferredHolder<FluidType, FluidType> LIQUID_CHEESE_TYPE =
            FLUID_TYPES.register("liquid_cheese", () -> new ModFluidType(
                    FluidType.Properties.create()
                            .descriptionId("fluid.createlogansutils.liquid_cheese")
                            .canSwim(false)
                            .canDrown(true)
                            .canPushEntity(true)
                            .canExtinguish(false)
                            .canConvertToSource(false)
                            .supportsBoating(false)
                            .density(13000)
                            .viscosity(13500)
                            .temperature(2000)
                            .lightLevel(10)
                            .sound(
                                    SoundActions.BUCKET_FILL,
                                    SoundEvents.BUCKET_FILL
                            )
                            .sound(
                                    SoundActions.BUCKET_EMPTY,
                                    SoundEvents.BUCKET_EMPTY
                            )
            ));


    public static final DeferredHolder<
            net.minecraft.world.level.material.Fluid,
            LiquidCheeseFluid.Source
            > LIQUID_CHEESE_SOURCE =
            FLUIDS.register(
                    "liquid_cheese",
                    () -> new LiquidCheeseFluid.Source(getCheeseProperties())
            );

    public static final DeferredHolder<
            net.minecraft.world.level.material.Fluid,
            LiquidCheeseFluid.Flowing
            > LIQUID_CHEESE_FLOWING =
            FLUIDS.register(
                    "flowing_liquid_cheese",
                    () -> new LiquidCheeseFluid.Flowing(getCheeseProperties())
            );

    public static final DeferredBlock<LiquidBlock> LIQUID_CHEESE_BLOCK =
            BLOCKS_FLUID.register(
                    "liquid_cheese",
                    () -> new LiquidBlock(
                            LIQUID_CHEESE_SOURCE.get(),
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.COLOR_YELLOW)
                                    .noCollission()
                                    .replaceable()
                                    .noLootTable()
                                    .lightLevel(state -> 10)
                    )
            );

    public static final DeferredItem<BucketItem> LIQUID_CHEESE_BUCKET =
            ITEMS_FLUID.register(
                    "liquid_cheese_bucket",
                    () -> new BucketItem(
                            LIQUID_CHEESE_SOURCE.get(),
                            new Item.Properties()
                                    .craftRemainder(net.minecraft.world.item.Items.BUCKET)
                                    .stacksTo(1)
                    )
            );

    private static BaseFlowingFluid.Properties getCheeseProperties() {
        return new BaseFlowingFluid.Properties(
                LIQUID_CHEESE_TYPE,
                LIQUID_CHEESE_SOURCE,
                LIQUID_CHEESE_FLOWING
        )
                .bucket(LIQUID_CHEESE_BUCKET)
                .block(LIQUID_CHEESE_BLOCK);
    }
// mint

    public static final DeferredHolder<FluidType, FluidType> LIQUID_MINT_TYPE =
            FLUID_TYPES.register("liquid_mint", () -> new ModFluidType(
                    FluidType.Properties.create()
                            .descriptionId("fluid.createlogansutils.liquid_mint")
                            .canSwim(true)
                            .canDrown(true)
                            .canPushEntity(true)
                            .canExtinguish(true)
                            .canConvertToSource(false)
                            .supportsBoating(false)
                            .density(1000)
                            .viscosity(1000)
                            .temperature(280)
                            .sound(
                                    SoundActions.BUCKET_FILL,
                                    SoundEvents.BUCKET_FILL
                            )
                            .sound(
                                    SoundActions.BUCKET_EMPTY,
                                    SoundEvents.BUCKET_EMPTY
                            )
            ));

    public static final DeferredHolder<
    net.minecraft.world.level.material.Fluid,
    BaseFlowingFluid.Source
        > LIQUID_MINT_SOURCE =
            FLUIDS.register(
            "liquid_mint",
            () -> new BaseFlowingFluid.Source(getMintProperties())
            );

    public static final DeferredHolder<
    net.minecraft.world.level.material.Fluid,
    BaseFlowingFluid.Flowing
        > LIQUID_MINT_FLOWING =
            FLUIDS.register(
            "flowing_liquid_mint",
            () -> new BaseFlowingFluid.Flowing(getMintProperties())
            );

    public static final DeferredBlock<LiquidBlock> LIQUID_MINT_BLOCK =
            BLOCKS_FLUID.register(
                    "liquid_mint",
                    () -> new LiquidBlock(
                            LIQUID_MINT_SOURCE.get(),
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.COLOR_GREEN)
                                    .noCollission()
                                    .replaceable()
                                    .noLootTable()
                    )
            );

    public static final DeferredItem<BucketItem> LIQUID_MINT_BUCKET =
            ITEMS_FLUID.register(
                    "liquid_mint_bucket",
                    () -> new BucketItem(
                            LIQUID_MINT_SOURCE.get(),
                            new Item.Properties()
                                    .craftRemainder(net.minecraft.world.item.Items.BUCKET)
                                    .stacksTo(1)
                    )
            );

    private static BaseFlowingFluid.Properties getMintProperties() {
        return new BaseFlowingFluid.Properties(
                LIQUID_MINT_TYPE,
                LIQUID_MINT_SOURCE,
                LIQUID_MINT_FLOWING
        )
                .bucket(LIQUID_MINT_BUCKET)
                .block(LIQUID_MINT_BLOCK);
    }
    // reg

    public static void register(IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
        BLOCKS_FLUID.register(modEventBus);
        ITEMS_FLUID.register(modEventBus);
    }
}
