package com.logthedeveloper.createlogansutils.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public record FluidConversionRecipe(Ingredient ingredient, Fluid fluid, ItemStack result) implements Recipe<FluidItemInput> {

    public static final MapCodec<FluidConversionRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(FluidConversionRecipe::ingredient),
            BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(FluidConversionRecipe::fluid),
            ItemStack.CODEC.fieldOf("result").forGetter(FluidConversionRecipe::result)
    ).apply(inst, FluidConversionRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidConversionRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, FluidConversionRecipe::ingredient,
            ByteBufCodecs.registry(Registries.FLUID), FluidConversionRecipe::fluid,
            ItemStack.STREAM_CODEC, FluidConversionRecipe::result,
            FluidConversionRecipe::new
    );

    @Override
    public boolean matches(FluidItemInput input, Level level) {
        return ingredient.test(input.stack()) && input.fluidType() == fluid.getFluidType();
    }

    @Override
    public ItemStack assemble(FluidItemInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return true; }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) { return result; }

    @Override
    public RecipeSerializer<? extends Recipe<FluidItemInput>> getSerializer() {
        return ModRecipes.FLUID_CONVERSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<FluidItemInput>> getType() {
        return ModRecipes.FLUID_CONVERSION_TYPE.get();
    }
}