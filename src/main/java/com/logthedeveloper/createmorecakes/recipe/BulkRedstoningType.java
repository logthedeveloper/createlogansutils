package com.logthedeveloper.createmorecakes.recipe;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class BulkRedstoningType implements FanProcessingType {

    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.REDSTONE_BLOCK);
    }

    @Override
    public int getPriority() {
        return 250;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        return level.getRecipeManager()
                .getRecipeFor(ModRecipes.BULK_REDSTONING_TYPE.get(), new SingleRecipeInput(stack), level)
                .isPresent();
    }

    @Override
    @Nullable
    public List<ItemStack> process(ItemStack stack, Level level) {
        Optional<?> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.BULK_REDSTONING_TYPE.get(), new SingleRecipeInput(stack), level);

        return recipe
                .map(r -> ((net.minecraft.world.item.crafting.RecipeHolder<BulkRedstoningRecipe>) r).value())
                .map(r -> RecipeApplier.applyRecipeOn(level, stack, r, false))
                .orElse(null);
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0) return;
        level.addParticle(ParticleTypes.CRIMSON_SPORE, pos.x, pos.y + .25f, pos.z, 0, 1 / 16f, 0);
    }

    @Override
    public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(0x990000, 0xFF3333, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(ParticleTypes.CRIMSON_SPORE, .125f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        // No entity effect for now — purely an item-processing stream
    }
}