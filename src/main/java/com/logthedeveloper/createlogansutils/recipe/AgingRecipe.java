package com.logthedeveloper.createlogansutils.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class AgingRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final int time;

    public AgingRecipe(Ingredient ingredient, ItemStack result, int time) {
        this.ingredient = ingredient;
        this.result = result;
        this.time = time;
    }

    public Ingredient getIngredient() { return ingredient; }
    public ItemStack getResult() { return result; }
    public int getTime() { return time; }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return ingredient.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return true; }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) { return result; }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, ingredient);
    }

    @Override
    public boolean isSpecial() { return true; }

    @Override
    public RecipeSerializer<?> getSerializer() { return ModRecipes.AGING_SERIALIZER.get(); }

    @Override
    public RecipeType<?> getType() { return ModRecipes.AGING_TYPE.get(); }

    public static class Serializer implements RecipeSerializer<AgingRecipe> {
        public static final MapCodec<AgingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(AgingRecipe::getIngredient),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(AgingRecipe::getResult),
                Codec.INT.optionalFieldOf("time", 100).forGetter(AgingRecipe::getTime)
        ).apply(i, AgingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AgingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, AgingRecipe::getIngredient,
                        ItemStack.STREAM_CODEC, AgingRecipe::getResult,
                        ByteBufCodecs.VAR_INT, AgingRecipe::getTime,
                        AgingRecipe::new);

        @Override public MapCodec<AgingRecipe> codec() { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, AgingRecipe> streamCodec() { return STREAM_CODEC; }
    }
}