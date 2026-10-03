package de.reiner.toolcasemc.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ToolCaseMenu extends AbstractContainerMenu {
    private final Container container;

    public ToolCaseMenu(final int containerId, final Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(54));
    }

    public ToolCaseMenu(final int containerId, final Inventory inventory, final Container container) {
        super(ModMenuType.TOOLCASE, containerId);
        checkContainerSize(container, 54);
        this.container = container;
        container.startOpen(inventory.player);
        this.addToolCaseGrid(container, 8, 18);
        int inventoryTop = 140;
        this.addStandardInventorySlots(inventory, 8, inventoryTop);
    }

    private void addToolCaseGrid(final Container container, final int left, final int top) {
        for (int y = 0; y < 6; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new ToolCaseSlot(container, x + y * 9, left + x * 18, top + y * 18));
            }
        }
    }

    public boolean stillValid(final Player player) {
        return this.container.stillValid(player);
    }

    public ItemStack quickMoveStack(final Player player, final int slotIndex) {
        ItemStack clicked = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            clicked = stack.copy();
            if (slotIndex < 54) {
                if (!this.moveItemStackTo(stack, 54, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, 54, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return clicked;
    }

    public void removed(final Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
}