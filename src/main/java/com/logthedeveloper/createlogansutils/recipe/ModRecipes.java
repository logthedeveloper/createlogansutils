package com.logthedeveloper.createlogansutils.recipe;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.loot.MusicDiscLootModifier;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.api.registry.CreateRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CreateLogansUtils.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CreateLogansUtils.MODID);
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CreateLogansUtils.MODID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FluidConversionRecipe>> FLUID_CONVERSION_TYPE =
            RECIPE_TYPES.register("fluid_conversion", () -> new RecipeType<FluidConversionRecipe>() {
                @Override public String toString() { return "createlogansutils:fluid_conversion"; }
            });

    public static final DeferredHolder<RecipeSerializer<?>, FluidConversionRecipeSerializer> FLUID_CONVERSION_SERIALIZER =
            RECIPE_SERIALIZERS.register("fluid_conversion", () -> FluidConversionRecipeSerializer.INSTANCE);




    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<MusicDiscLootModifier>> MUSIC_DISC_LOOT_MODIFIER =
            LOOT_MODIFIER_SERIALIZERS.register("music_disc_barkfart", () -> MusicDiscLootModifier.CODEC);

    public static void register(IEventBus modEventBus) {
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

    }


    }
