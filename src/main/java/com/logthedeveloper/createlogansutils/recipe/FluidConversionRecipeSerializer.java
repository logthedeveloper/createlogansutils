package com.logthedeveloper.createlogansutils.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FluidConversionRecipeSerializer implements RecipeSerializer<FluidConversionRecipe> {
    public static final FluidConversionRecipeSerializer INSTANCE = new FluidConversionRecipeSerializer();

    @Override public MapCodec<FluidConversionRecipe> codec() { return FluidConversionRecipe.CODEC; }
    @Override public StreamCodec<RegistryFriendlyByteBuf, FluidConversionRecipe> streamCodec() { return FluidConversionRecipe.STREAM_CODEC; }
}