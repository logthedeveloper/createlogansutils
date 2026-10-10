package com.logthedeveloper.createlogansutils.recipe;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;

// Extending BasinRecipe gives you item+fluid inputs/outputs, heat, and basin matching for free.
public class JuicingRecipe extends BasinRecipe {
    public JuicingRecipe(ProcessingRecipeParams params) {
        super(ModJuicerRecipes.JUICING, params);
    }
}