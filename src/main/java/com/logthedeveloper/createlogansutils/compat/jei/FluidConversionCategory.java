package com.logthedeveloper.createlogansutils.compat.jei;

import com.logthedeveloper.createlogansutils.recipe.FluidConversionRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;

public class FluidConversionCategory implements IRecipeCategory<FluidConversionRecipe> {
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    private final IDrawable icon;

    public FluidConversionCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableItemStack(
                new ItemStack(BuiltInRegistries.ITEM.get(
                        ResourceLocation.fromNamespaceAndPath("create", "rose_quartz"))));
    }

    @Override public RecipeType<FluidConversionRecipe> getRecipeType() { return CreateLogansUtilsJeiPlugin.FLUID_CONVERSION; }
    @Override public Component getTitle() { return Component.translatable("category.createlogansutils.fluid_conversion"); }
    @Override public int getWidth() { return WIDTH; }
    @Override public int getHeight() { return HEIGHT; }
    @Override public IDrawable getIcon() { return icon; }

    @Override
    public void draw(FluidConversionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FluidConversionRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 12).addIngredients(recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 12)
                .addIngredient(NeoForgeTypes.FLUID_STACK, new FluidStack(recipe.fluid(), FluidType.BUCKET_VOLUME));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 12).addItemStack(recipe.result());
    }
}