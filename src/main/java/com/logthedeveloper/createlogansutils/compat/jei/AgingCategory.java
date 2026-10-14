package com.logthedeveloper.createlogansutils.compat.jei;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.recipe.AgingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class AgingCategory implements IRecipeCategory<AgingRecipe> {
    public static final RecipeType<AgingRecipe> TYPE =
            RecipeType.create(CreateLogansUtils.MODID, "aging", AgingRecipe.class);

    private static final int WIDTH = 120;
    private static final int HEIGHT = 44;

    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public AgingCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(CreateLogansUtils.YOUNG_CHEESE_BIN.get()));
        this.arrow = helper.createAnimatedRecipeArrow(100);
    }

    @Override public RecipeType<AgingRecipe> getRecipeType() { return TYPE; }
    @Override public Component getTitle() { return Component.translatable("category.createlogansutils.aging"); }
    @Override public IDrawable getIcon() { return icon; }

    public int getWidth() { return WIDTH; }
    public int getHeight() { return HEIGHT; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AgingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 14, 10).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 88, 10).addItemStack(recipe.getResult());
    }

    @Override
    public void draw(AgingRecipe recipe, IRecipeSlotsView slots, GuiGraphics gfx, double mouseX, double mouseY) {
        arrow.draw(gfx, 48, 11);
        Font font = Minecraft.getInstance().font;
        Component text = Component.translatable("gui.createlogansutils.aging.time",
                String.format("%.1f", recipe.getTime() / 20.0));
        gfx.drawString(font, text, (WIDTH - font.width(text)) / 2, 32, 0xFF808080, false);
    }
}