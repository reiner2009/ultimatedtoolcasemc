package de.reiner.toolcasemc.inventory;

import de.reiner.toolcasemc.tag.ModTags;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ToolCaseSlot extends Slot {
    public ToolCaseSlot(final Container container, final int slot, final int x, final int y) {
        super(container, slot, x, y);
    }

    public boolean mayPlace(final ItemStack stack) {
        return (stack.is(ModTags.TOOLS));
    }
}
