package com.logthedeveloper.createlogansutils.block;

import com.logthedeveloper.createlogansutils.block.entity.InfiniteLavaSourceBlockEntity;
import com.simibubi.create.content.equipment.wrench.IWrenchable; // 1. Import IWrenchable
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


public class InfiniteLavaSourceBlock extends Block implements EntityBlock, IWrenchable {

    public InfiniteLavaSourceBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfiniteLavaSourceBlockEntity(pos, state);
    }
}