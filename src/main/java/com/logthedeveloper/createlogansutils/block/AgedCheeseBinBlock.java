package com.logthedeveloper.createlogansutils.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AgedCheeseBinBlock extends Block {

    public AgedCheeseBinBlock(Properties properties) {
        super(properties);
    }

    // Handles right-clicking while holding an item
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {

            ItemStack cheese = new ItemStack(ModBlocks.BLOCK_OF_CHEESE.asItem());
            if (!player.getInventory().add(cheese)) {
                player.drop(cheese, false);
            }


            level.setBlockAndUpdate(pos, Blocks.COMPOSTER.defaultBlockState());


            level.playSound(null, pos, SoundEvents.HONEY_BLOCK_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return ItemInteractionResult.SUCCESS;
    }

    // Handles right-clicking with an empty hand
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            ItemStack cheese = new ItemStack(ModBlocks.BLOCK_OF_CHEESE.asItem());
            if (!player.getInventory().add(cheese)) {
                player.drop(cheese, false);
            }

            // Change the block at this position to a Vanilla Composter
            level.setBlockAndUpdate(pos, Blocks.COMPOSTER.defaultBlockState());

            level.playSound(null, pos, SoundEvents.HONEY_BLOCK_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return InteractionResult.SUCCESS;
    }
}