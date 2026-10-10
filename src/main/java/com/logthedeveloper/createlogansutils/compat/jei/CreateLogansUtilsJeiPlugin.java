package com.logthedeveloper.createlogansutils.compat.jei;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.recipe.FluidConversionRecipe;
import com.logthedeveloper.createlogansutils.recipe.JuicingRecipe;
import com.logthedeveloper.createlogansutils.recipe.ModJuicerRecipes;
import com.logthedeveloper.createlogansutils.recipe.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

@JeiPlugin
public class CreateLogansUtilsJeiPlugin implements IModPlugin {
    public static final RecipeType<FluidConversionRecipe> FLUID_CONVERSION =
            RecipeType.create(CreateLogansUtils.MODID, "fluid_conversion", FluidConversionRecipe.class);
    public static final RecipeType<JuicingRecipe> JUICING =
            RecipeType.create(CreateLogansUtils.MODID, "juicing", JuicingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FluidConversionCategory(helper));
        registration.addRecipeCategories(new JuicingJeiCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var level = Minecraft.getInstance().level;
        if (level == null) return;

        List<FluidConversionRecipe> conversions = level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.FLUID_CONVERSION_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(FLUID_CONVERSION, conversions);

        List<JuicingRecipe> juicing = level.getRecipeManager()
                .getAllRecipesFor(ModJuicerRecipes.JUICING.<RecipeInput, JuicingRecipe>getType())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(JUICING, juicing);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CreateLogansUtils.MECHANICAL_JUICER.get()), JUICING);
    }
}