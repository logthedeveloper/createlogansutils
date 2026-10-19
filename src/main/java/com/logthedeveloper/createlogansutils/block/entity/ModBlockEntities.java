package com.logthedeveloper.createlogansutils.block.entity;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.logthedeveloper.createlogansutils.block.entity.InfiniteLavaSourceBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateLogansUtils.MODID);


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MechanicalJuicerBlockEntity>> MECHANICAL_JUICER =
            BLOCK_ENTITIES.register("mechanical_juicer", () ->
                    BlockEntityType.Builder.of(
                            (pos, state) -> new MechanicalJuicerBlockEntity(ModBlockEntities.MECHANICAL_JUICER.get(), pos, state),
                            ModBlocks.MECHANICAL_JUICER.get()
                    ).build(null));
    public static final Supplier<BlockEntityType<UpgradeableTankBlockEntity>> UPGRADEABLE_TANK =
            BLOCK_ENTITIES.register("upgradeable_tank", () ->
                    BlockEntityType.Builder.of(UpgradeableTankBlockEntity::new, ModBlocks.UPGRADEABLE_TANK.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InfiniteLavaSourceBlockEntity>> INFINITE_LAVA_SOURCE =
            BLOCK_ENTITIES.register("infinite_lava_source",
                    () -> BlockEntityType.Builder.of(InfiniteLavaSourceBlockEntity::new, ModBlocks.INFINITE_LAVA_SOURCE.get()).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}