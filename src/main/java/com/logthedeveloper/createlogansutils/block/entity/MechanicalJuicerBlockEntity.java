package com.logthedeveloper.createlogansutils.block.entity;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.recipe.ModJuicerRecipes;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.content.kinetics.press.PressingBehaviour.Mode;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.SmartInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;

public class MechanicalJuicerBlockEntity extends BasinOperatingBlockEntity
        implements PressingBehaviour.PressingBehaviourSpecifics {

    private static final Object RECIPE_KEY = new Object();
    public PressingBehaviour pressingBehaviour;

    public MechanicalJuicerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).expandTowards(0, -1.5, 0).expandTowards(0, 1, 0);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        pressingBehaviour = new PressingBehaviour(this);
        behaviours.add(pressingBehaviour);
    }

    public PressingBehaviour getPressingBehaviour() { return pressingBehaviour; }

    // ---- Basin processing ----
    @Override
    public boolean tryProcessInBasin(boolean simulate) {
        if (!simulate && level != null) {
            level.playSound(null, worldPosition, CreateLogansUtils.JUICER_HIT_SOUND.get(),
                    net.minecraft.sounds.SoundSource.BLOCKS, 0.7f,
                    0.9f + level.random.nextFloat() * 0.2f);
        }
        applyBasinRecipe();
        Optional<BasinBlockEntity> basin = getBasin();
        if (basin.isPresent()) {
            SmartInventory inputs = basin.get().getInputInventory();
            for (int slot = 0; slot < inputs.getSlots(); slot++) {
                ItemStack stack = inputs.getItem(slot);
                if (!stack.isEmpty())
                    pressingBehaviour.particleItems.add(stack);
            }
        }
        return true;
    }

    // Basin-only machine: no belt / dropped-item juicing
    @Override public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) { return false; }
    @Override public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> out, boolean simulate) { return false; }
    @Override public boolean canProcessInBulk() { return false; }

    @Override
    public void onPressingCompleted() {
        if (pressingBehaviour.onBasin() && matchBasinRecipe(currentRecipe)
                && getBasin().filter(BasinBlockEntity::canContinueProcessing).isPresent())
            startProcessingBasin();
        else
            basinChecker.scheduleUpdate();
    }

    @Override
    protected boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> recipe) {
        return recipe.value().getType() == ModJuicerRecipes.JUICING.getType();
    }

    @Override public float getKineticSpeed() { return getSpeed(); }
    @Override protected Object getRecipeCacheKey() { return RECIPE_KEY; }
    @Override public int getParticleAmount() { return 15; }

    @Override
    public void startProcessingBasin() {
        if (pressingBehaviour.running && pressingBehaviour.runningTicks <= PressingBehaviour.CYCLE / 2)
            return;
        super.startProcessingBasin();
        pressingBehaviour.start(Mode.BASIN);
    }

    @Override
    protected void onBasinRemoved() {
        pressingBehaviour.particleItems.clear();
        pressingBehaviour.running = false;
        pressingBehaviour.runningTicks = 0;
        sendData();
    }
    @Override
    protected boolean updateBasin() {
        if (level != null && !level.isClientSide) {
            long loaded = level.getRecipeManager().getRecipes().stream()
                    .filter(h -> h.value().getType() == ModJuicerRecipes.JUICING.getType())
                    .count();
            com.logthedeveloper.createlogansutils.CreateLogansUtils.LOGGER.info(
                    "[juicer] speed={} running={} basin={} matches={} juicingRecipesLoaded={}",
                    getSpeed(), isRunning(), getBasin().isPresent(), getMatchingRecipes().size(), loaded);
        }
        return super.updateBasin();
    }

    @Override protected boolean isRunning() { return pressingBehaviour.running; }
}