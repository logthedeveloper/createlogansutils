package com.logthedeveloper.createlogansutils.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MintBlock extends BushBlock {

    public static final BooleanProperty SNOWY = BooleanProperty.create("snowy");

    protected static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 13, 13);

    public static final MapCodec<MintBlock> CODEC = simpleCodec(MintBlock::new);

    public MintBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(SNOWY, false));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SNOWY);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT);
    }

    // When the mint is removed (broken, etc.), leave the snow layer behind
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && state.getValue(SNOWY) && newState.isAir()) {
            level.setBlock(pos, Blocks.SNOW.defaultBlockState(), 3);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}