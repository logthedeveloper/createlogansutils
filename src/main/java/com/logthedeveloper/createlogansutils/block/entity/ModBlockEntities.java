package com.logthedeveloper.createlogansutils.block.entity;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateLogansUtils.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<YoungCheeseBinBlockEntity>> YOUNG_CHEESE_BIN =
            BLOCK_ENTITIES.register("young_cheese_bin", () -> BlockEntityType.Builder.of(
                    YoungCheeseBinBlockEntity::new,
                    ModBlocks.YOUNG_CHEESE_BIN.get()
            ).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}