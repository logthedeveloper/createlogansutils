package com.logthedeveloper.createlogansutils.menu;

import com.logthedeveloper.createlogansutils.block.ModBlocks;
import com.logthedeveloper.createlogansutils.block.entity.UpgradeableTankBlockEntity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class UpgradeableTankMenu extends AbstractContainerMenu {
    public final UpgradeableTankBlockEntity be;
    private final ContainerLevelAccess access;

    // Client side: the server sends the block position
    public UpgradeableTankMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory,
                (UpgradeableTankBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    // Server side
    public UpgradeableTankMenu(int containerId, Inventory inventory, UpgradeableTankBlockEntity be) {
        super(ModMenuTypes.UPGRADEABLE_TANK_MENU.get(), containerId);
        this.be = be;
        this.access = ContainerLevelAccess.create(be.getLevel(), be.getBlockPos());

        // Player inventory: slots 0-35
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }

        // Upgrade slots: 36-38
        for (int i = 0; i < UpgradeableTankBlockEntity.UPGRADE_SLOTS; i++) {
            final int slotIndex = i;
            addSlot(new SlotItemHandler(be.getUpgrades(), i, 98 + i * 18, 35) {
                @Override
                public boolean mayPickup(Player player) {
                    return be.canRemoveUpgrade(slotIndex);
                }
            });
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < 36) {
            if (!moveItemStackTo(stack, 36, 39, false)) return ItemStack.EMPTY;
        } else if (!moveItemStackTo(stack, 0, 36, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.UPGRADEABLE_TANK.get());
    }
}