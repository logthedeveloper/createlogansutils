package com.logthedeveloper.createlogansutils.compat.jei;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.recipe.JuicingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.Arrays;

public class JuicingJeiCategory implements IRecipeCategory<JuicingRecipe> {
    private static final int WIDTH = 180;
    private static final int OUTPUT_X = 117;

    private final IDrawable icon;
    private final AnimatedJuicer juicer = new AnimatedJuicer();

    public JuicingJeiCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableItemStack(new ItemStack(CreateLogansUtils.MECHANICAL_JUICER.get()));
    }

    @Override public RecipeType<JuicingRecipe> getRecipeType() { return CreateLogansUtilsJeiPlugin.JUICING; }
    @Override public Component getTitle() { return Component.translatable("emi.category.createlogansutils.juicing"); }
    @Override public IDrawable getIcon() { return icon; }
    @Override public int getWidth() { return WIDTH; }
    @Override public int getHeight() { return 70; }

    @Override
    public void draw(JuicingRecipe recipe, IRecipeSlotsView slots, GuiGraphics g, double mx, double my) {
       // shadow
        AllGuiTextures.JEI_SHADOW.render(g, WIDTH / 2 - 29, 41);
        juicer.draw(g, WIDTH / 2 - 17, 22);

        var font = Minecraft.getInstance().font;
        if (recipe.getRequiredHeat() == HeatCondition.HEATED)
            g.drawString(font, Component.translatable("createlogansutils.recipe.heated"), 0, 58, 0xFFAA00);
        else if (recipe.getRequiredHeat() == HeatCondition.SUPERHEATED)
            g.drawString(font, Component.translatable("createlogansutils.recipe.superheated"), 0, 58, 0x55FFFF);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, JuicingRecipe recipe, IFocusGroup focuses) {
        // juicer is now drawn by the animation, so no catalyst slot is needed for it
        if (recipe.getRequiredHeat() != HeatCondition.NONE) {
            builder.addSlot(RecipeIngredientRole.CATALYST, OUTPUT_X, 50)
                    .addItemStack(new ItemStack(BuiltInRegistries.ITEM.get(
                            ResourceLocation.fromNamespaceAndPath("create", "blaze_burner"))));
        }
        int i = 0;
        for (Ingredient ing : recipe.getIngredients()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 1 + (i % 3) * 18, 1 + (i / 3) * 18).addIngredients(ing);
            i++;
        }
        for (SizedFluidIngredient fluid : recipe.getFluidIngredients()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 1 + (i % 3) * 18, 1 + (i / 3) * 18)
                    .addIngredients(NeoForgeTypes.FLUID_STACK, Arrays.asList(fluid.getFluids()));
            i++;
        }
        int o = 0;
        for (ProcessingOutput out : recipe.getRollableResults()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_X + (o % 3) * 18, 1 + (o / 3) * 18)
                    .addItemStack(out.getStack());
            o++;
        }
        for (FluidStack fs : recipe.getFluidResults()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_X + (o % 3) * 18, 1 + (o / 3) * 18)
                    .addIngredient(NeoForgeTypes.FLUID_STACK, fs);
            o++;
        }
    }
}