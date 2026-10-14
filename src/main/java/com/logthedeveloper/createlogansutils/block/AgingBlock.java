package com.logthedeveloper.createlogansutils.block;

import com.logthedeveloper.createlogansutils.recipe.AgingRecipe;
import com.logthedeveloper.createlogansutils.recipe.ModRecipes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class AgingBlock extends Block {
    public static final MapCodec<AgingBlock> CODEC = simpleCodec(AgingBlock::new);

    public AgingBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    private Optional<RecipeHolder<AgingRecipe>> findRecipe(Level level, BlockState state) {
        return level.getRecipeManager().getRecipeFor(
                ModRecipes.AGING_TYPE.get(),
                new SingleRecipeInput(new ItemStack(state.getBlock())),
                level);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (level.isClientSide || oldState.is(state.getBlock())) return;
        findRecipe(level, state).ifPresent(r -> level.scheduleTick(pos, this, r.value().getTime()));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        findRecipe(level, state).ifPresent(r -> {
            ItemStack out = r.value().getResultItem(level.registryAccess());
            if (out.getItem() instanceof BlockItem blockItem) {
                level.setBlockAndUpdate(pos, blockItem.getBlock().defaultBlockState());
            }
        });
    }
}