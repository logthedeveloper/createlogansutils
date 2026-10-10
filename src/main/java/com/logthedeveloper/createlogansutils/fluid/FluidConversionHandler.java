package com.logthedeveloper.createlogansutils.fluid;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.recipe.FluidConversionRecipe;
import com.logthedeveloper.createlogansutils.recipe.FluidItemInput;
import com.logthedeveloper.createlogansutils.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = CreateLogansUtils.MODID)
public class FluidConversionHandler {

    @SubscribeEvent
    public static void onItemTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;
        Level level = itemEntity.level();
        if (level.isClientSide()) return;
        if (itemEntity.tickCount % 5 != 0) return; // throttle, don't check every single tick

        BlockPos pos = itemEntity.blockPosition();
        FluidState fluidState = level.getFluidState(pos);
        if (fluidState.isEmpty()) return;

        ItemStack stack = itemEntity.getItem();
        FluidItemInput input = new FluidItemInput(stack, fluidState.getType().getFluidType());

        for (RecipeHolder<FluidConversionRecipe> holder :
                level.getRecipeManager().getAllRecipesFor(ModRecipes.FLUID_CONVERSION_TYPE.get())) {
            FluidConversionRecipe recipe = holder.value();
            if (recipe.matches(input, level)) {
                ItemStack result = recipe.assemble(input, level.registryAccess());
                result.setCount(stack.getCount());
                itemEntity.setItem(result);
                level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 0.6f, 1.2f);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.WITCH,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            8, 0.2, 0.2, 0.2, 0.01);
                }
                break;
            }
        }
    }
}