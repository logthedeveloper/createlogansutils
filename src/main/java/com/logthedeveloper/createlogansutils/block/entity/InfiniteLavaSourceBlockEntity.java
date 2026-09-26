package com.logthedeveloper.createlogansutils.block.entity;

import com.logthedeveloper.createlogansutils.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class InfiniteLavaSourceBlockEntity extends BlockEntity {

    // The "always available" amount, in mB, as requested: 1000mB of lava
    public static final int CAPACITY = 1000;

    public InfiniteLavaSourceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFINITE_LAVA_SOURCE.get(), pos, state);
    }

    // A fluid handler that never actually depletes: every time something is
    // drained, we just hand out lava and forget it ever happened. Nothing is
    // stored in NBT because there's no real state to track.
    // Every method checks the config toggle first - when disabled, this acts
    // like a completely empty, inert tank instead of producing any lava.
    private final IFluidHandler fluidHandler = new IFluidHandler() {
        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            if (!Config.ENABLE_INFINITE_LAVA_SOURCE.get()) return FluidStack.EMPTY;
            return new FluidStack(Fluids.LAVA, CAPACITY);
        }

        @Override
        public int getTankCapacity(int tank) {
            return CAPACITY;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            // This block only ever outputs fluid, it never accepts any.
            return false;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (!Config.ENABLE_INFINITE_LAVA_SOURCE.get()) return FluidStack.EMPTY;
            if (resource.getFluid().isSame(Fluids.LAVA)) {
                return new FluidStack(Fluids.LAVA, Math.min(resource.getAmount(), CAPACITY));
            }
            return FluidStack.EMPTY;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (!Config.ENABLE_INFINITE_LAVA_SOURCE.get()) return FluidStack.EMPTY;
            return new FluidStack(Fluids.LAVA, Math.min(maxDrain, CAPACITY));
        }
    };

    // Called from the capability registration in RegisterCapabilitiesEvent.
    // The Direction param is unused since it doesn't matter what side is pumping.
    public IFluidHandler getFluidHandler(@Nullable Direction side) {
        return fluidHandler;
    }
}