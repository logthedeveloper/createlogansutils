package com.logthedeveloper.createlogansutils.block;

import com.logthedeveloper.createlogansutils.block.entity.ModBlockEntities;
import com.logthedeveloper.createlogansutils.block.entity.YoungCheeseBinBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class YoungCheeseBinBlock extends BaseEntityBlock {

    public static final MapCodec<YoungCheeseBinBlock> CODEC = simpleCodec(YoungCheeseBinBlock::new);

    public YoungCheeseBinBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new YoungCheeseBinBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null :
                createTickerHelper(type, ModBlockEntities.YOUNG_CHEESE_BIN.get(), YoungCheeseBinBlockEntity::serverTick);
    }
}