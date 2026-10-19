package com.logthedeveloper.createlogansutils.block.entity;

import java.util.List;

import com.logthedeveloper.createlogansutils.item.TankUpgradeItem;
import com.logthedeveloper.createlogansutils.menu.UpgradeableTankMenu;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;

public class UpgradeableTankBlockEntity extends BlockEntity implements MenuProvider, IHaveGoggleInformation {
    public static final int BASE_CAPACITY = 16_000;
    public static final int UPGRADE_SLOTS = 3;


    private static final int INFINITE_DRAIN_LIMIT = 1_000_000;

    private final FluidTank tank = new FluidTank(BASE_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            sync();
        }

        @Override
        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {

            if (UpgradeableTankBlockEntity.this.isInfinite() && !isEmpty() && maxDrain > 0) {
                return getFluid().copyWithAmount(Math.min(maxDrain, INFINITE_DRAIN_LIMIT));
            }
            return super.drain(maxDrain, action);
        }
    };

    private final ItemStackHandler upgrades = new ItemStackHandler(UPGRADE_SLOTS) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof TankUpgradeItem;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            refreshCapacity();
            setChanged();
            sync();
        }
    };

    public UpgradeableTankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UPGRADEABLE_TANK.get(), pos, state);
    }

    public FluidTank getTank() {
        return tank;
    }

    public ItemStackHandler getUpgrades() {
        return upgrades;
    }

    /** Multiplies all upgrades together (3x 8x = 512x). Any infinite upgrade maxes it out. */
    public int computeCapacity(int ignoreSlot) {
        long cap = BASE_CAPACITY;
        for (int i = 0; i < UPGRADE_SLOTS; i++) {
            if (i == ignoreSlot) continue;
            if (upgrades.getStackInSlot(i).getItem() instanceof TankUpgradeItem upgrade) {
                if (upgrade.isInfinite()) return Integer.MAX_VALUE;
                cap *= upgrade.getMultiplier();
            }
        }
        return (int) Math.min(cap, Integer.MAX_VALUE);
    }

    public boolean isInfinite() {
        for (int i = 0; i < UPGRADE_SLOTS; i++) {
            if (upgrades.getStackInSlot(i).getItem() instanceof TankUpgradeItem upgrade && upgrade.isInfinite()) {
                return true;
            }
        }
        return false;
    }

    /** Can this upgrade be taken out without the stored fluid no longer fitting? */
    public boolean canRemoveUpgrade(int slot) {
        return tank.getFluidAmount() <= computeCapacity(slot);
    }

    private void refreshCapacity() {
        int cap = computeCapacity(-1);
        tank.setCapacity(cap);
        if (tank.getFluidAmount() > cap) {
            tank.getFluid().setAmount(cap);
        }
    }

    private void sync() {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void dropUpgrades(Level level, BlockPos pos) {
        for (int i = 0; i < UPGRADE_SLOTS; i++) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), upgrades.getStackInSlot(i));
        }
    }



    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (!isInfinite()) {
            return containedFluidTooltip(tooltip, isPlayerSneaking, tank);
        }

        tooltip.add(Component.literal("    ").append(Component.translatable("create.gui.goggles.fluid_container")));

        Component name = tank.isEmpty() ? Component.literal("Empty") : tank.getFluid().getHoverName();
        tooltip.add(Component.literal("    ").append(name.copy().withStyle(ChatFormatting.GRAY)));

        tooltip.add(Component.literal("    ")
                .append(Component.literal(String.format("%,d mB", tank.getFluidAmount())).withStyle(ChatFormatting.GOLD))
                .append(Component.literal(" / ∞").withStyle(ChatFormatting.DARK_GRAY)));
        return true;
    }



    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Upgrades", upgrades.serializeNBT(registries));
        tag.put("Tank", tank.writeToNBT(registries, new CompoundTag()));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        upgrades.deserializeNBT(registries, tag.getCompound("Upgrades"));
        tank.setCapacity(computeCapacity(-1));
        tank.readFromNBT(registries, tag.getCompound("Tank"));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }



    @Override
    public Component getDisplayName() {
        return Component.translatable("block.createlogansutils.upgradeable_tank");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new UpgradeableTankMenu(containerId, inventory, this);
    }
}