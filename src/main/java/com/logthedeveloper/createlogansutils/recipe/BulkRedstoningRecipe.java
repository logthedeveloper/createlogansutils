package com.logthedeveloper.createlogansutils.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;

public class BulkRedstoningRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient ingredient;
    private final ItemStack result;

    public BulkRedstoningRecipe(Ingredient ingredient, ItemStack result) {
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return ingredient.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return ModRecipes.BULK_REDSTONING_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return ModRecipes.BULK_REDSTONING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<BulkRedstoningRecipe> {
        public static final MapCodec<BulkRedstoningRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.ingredient),
                        ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result)
                ).apply(instance, BulkRedstoningRecipe::new));

        public static final StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, BulkRedstoningRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.ingredient,
                        ItemStack.STREAM_CODEC, r -> r.result,
                        BulkRedstoningRecipe::new);

        @Override
        public MapCodec<BulkRedstoningRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, BulkRedstoningRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
