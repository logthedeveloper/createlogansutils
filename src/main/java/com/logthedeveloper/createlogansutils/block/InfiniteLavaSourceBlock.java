package com.logthedeveloper.createlogansutils.block;

import com.logthedeveloper.createlogansutils.block.entity.InfiniteLavaSourceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// A plain block that just needs a BlockEntity attached so it can expose
// a fluid handler capability. It has no special visual/physical behavior
// of its own - all the "infinite lava" logic lives in the block entity.
public class InfiniteLavaSourceBlock extends Block implements EntityBlock {

    public InfiniteLavaSourceBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfiniteLavaSourceBlockEntity(pos, state);
    }
}
