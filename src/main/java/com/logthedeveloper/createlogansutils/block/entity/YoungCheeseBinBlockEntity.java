package com.logthedeveloper.createlogansutils.block.entity;

import com.logthedeveloper.createlogansutils.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class YoungCheeseBinBlockEntity extends BlockEntity {

    public static final int AGE_TIME = 200; // 20 ticks/sec * 10 seconds

    private int ageTicks = 0;

    public YoungCheeseBinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.YOUNG_CHEESE_BIN.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, YoungCheeseBinBlockEntity be) {
        be.ageTicks++;
        if (be.ageTicks >= AGE_TIME) {
            level.setBlockAndUpdate(pos, ModBlocks.AGED_CHEESE_BIN.get().defaultBlockState());
        } else {
            be.setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("AgeTicks", ageTicks);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ageTicks = tag.getInt("AgeTicks");
    }
}