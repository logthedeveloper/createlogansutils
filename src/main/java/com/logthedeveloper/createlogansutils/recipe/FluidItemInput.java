package com.logthedeveloper.createlogansutils.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidType;

public record FluidItemInput(ItemStack stack, FluidType fluidType) implements RecipeInput {
    @Override public ItemStack getItem(int index) { return stack; }
    @Override public int size() { return 1; }
}