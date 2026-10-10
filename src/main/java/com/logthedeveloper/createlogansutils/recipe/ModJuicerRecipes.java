package com.logthedeveloper.createlogansutils.recipe;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModJuicerRecipes implements IRecipeTypeInfo {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CreateLogansUtils.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CreateLogansUtils.MODID);

    public static final ModJuicerRecipes JUICING = new ModJuicerRecipes("juicing",
            () -> new StandardProcessingRecipe.Serializer<>(JuicingRecipe::new));

    private final ResourceLocation id;
    private final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> serializer;
    private final DeferredHolder<RecipeType<?>, RecipeType<?>> type;

    private ModJuicerRecipes(String name, Supplier<RecipeSerializer<?>> serializerSupplier) {
        this.id = ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, name);
        this.serializer = SERIALIZERS.register(name, serializerSupplier);
        this.type = TYPES.register(name, () -> RecipeType.simple(id));
    }

    @Override public ResourceLocation getId() { return id; }

    @Override @SuppressWarnings("unchecked")
    public <T extends RecipeSerializer<?>> T getSerializer() { return (T) serializer.get(); }

    @Override @SuppressWarnings("unchecked")
    public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getType() { return (RecipeType<R>) type.get(); }

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
        TYPES.register(bus);
    }
}