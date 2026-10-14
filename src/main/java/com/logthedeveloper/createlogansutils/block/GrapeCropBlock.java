package com.logthedeveloper.createlogansutils.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.logthedeveloper.createlogansutils.CreateLogansUtils.GRAPE_SEEDS;

public class GrapeCropBlock extends CropBlock {
    public static final MapCodec<GrapeCropBlock> CODEC = simpleCodec(GrapeCropBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    private static final VoxelShape[] SHAPE_BY_AGE = {
        Block.box(0, 0, 0, 16, 2, 16),
        Block.box(0, 0, 0, 16, 4, 16),
        Block.box(0, 0, 0, 16, 8, 16),
        Block.box(0, 0, 0, 16, 12, 16)
    };

    public GrapeCropBlock(Properties props) { super(props); }

    @Override public MapCodec<GrapeCropBlock> codec() { return CODEC; }
    @Override public IntegerProperty getAgeProperty() { return AGE; }
    @Override public int getMaxAge() { return 3; }
    @Override protected ItemLike getBaseSeedId() { return GRAPE_SEEDS.get(); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return SHAPE_BY_AGE[getAge(state)];
    }
}