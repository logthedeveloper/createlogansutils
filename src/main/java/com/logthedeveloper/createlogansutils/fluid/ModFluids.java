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

    // fuid types
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



    // liquiod redstone fluid type


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


    //liquid redstone fludis

    public static final DeferredHolder<
            net.minecraft.world.level.material.Fluid,
            BaseFlowingFluid.Source
            > LIQUID_REDSTONE_SOURCE =
            FLUIDS.register(
                    "liquid_redstone",
                    () -> new BaseFlowingFluid.Source(getProperties())
            );

    public static final DeferredHolder<
            net.minecraft.world.level.material.Fluid,
            BaseFlowingFluid.Flowing
            > LIQUID_REDSTONE_FLOWING =
            FLUIDS.register(
                    "flowing_liquid_redstone",
                    () -> new BaseFlowingFluid.Flowing(getProperties())
            );


    // liquid redstone bolck

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


    // bucket o liccy redstone

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


    // props for the flooid

    private static BaseFlowingFluid.Properties getProperties() {
        return new BaseFlowingFluid.Properties(
                LIQUID_REDSTONE_TYPE,
                LIQUID_REDSTONE_SOURCE,
                LIQUID_REDSTONE_FLOWING
        )
                .bucket(LIQUID_REDSTONE_BUCKET)
                .block(LIQUID_REDSTONE_BLOCK);
    }


    // registering shi

    public static void register(IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
        BLOCKS_FLUID.register(modEventBus);
        ITEMS_FLUID.register(modEventBus);
    }
}