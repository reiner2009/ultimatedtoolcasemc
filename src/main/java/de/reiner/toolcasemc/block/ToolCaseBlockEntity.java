package de.reiner.toolcasemc.block;

import de.reiner.toolcasemc.inventory.ToolCaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.stream.IntStream;

public class ToolCaseBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private static final int[] SLOTS = IntStream.range(0, 54).toArray();
    private static final Component DEFAULT_NAME = Component.translatable("container.toolCase");
    private NonNullList<ItemStack> itemStacks;
    private int openCount;

    public ToolCaseBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        super(ModBlockEntityType.TOOLCASE_BLOCK_ENTITY, worldPosition, blockState);
        this.itemStacks = NonNullList.withSize(54, ItemStack.EMPTY);
    }

    @Override
    public int getContainerSize() {
        return this.itemStacks.size();
    }

    @Override
    public void preRemoveSideEffects(final BlockPos pos, final BlockState state) {
    }

    @Override
    public void startOpen(final ContainerUser containerUser) {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            ++this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount == 1) {
                this.level.gameEvent(containerUser.getLivingEntity(), GameEvent.CONTAINER_OPEN, this.worldPosition);
            }
        }

    }

    public void stopOpen(final ContainerUser containerUser) {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
            --this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount <= 0) {
                this.level.gameEvent(containerUser.getLivingEntity(), GameEvent.CONTAINER_CLOSE, this.worldPosition);
            }
        }

    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected void loadAdditional(final ValueInput input) {
        super.loadAdditional(input);
        this.loadFromTag(input);
    }

    @Override
    protected void saveAdditional(final ValueOutput output) {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, this.itemStacks, false);
        }

    }

    public void loadFromTag(final ValueInput input) {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(input)) {
            ContainerHelper.loadAllItems(input, this.itemStacks);
        }

    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.itemStacks;
    }

    @Override
    protected void setItems(final NonNullList<ItemStack> items) {
        this.itemStacks = items;
    }

    @Override
    public int[] getSlotsForFace(final Direction direction) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(final int slot, final ItemStack itemStack, final @Nullable Direction direction) {
        return !(Block.byItem(itemStack.getItem()) instanceof ToolCaseBlock);
    }

    @Override
    public boolean canTakeItemThroughFace(final int slot, final ItemStack itemStack, final Direction direction) {
        return true;
    }

    @Override
    protected AbstractContainerMenu createMenu(final int containerId, final Inventory inventory) {
        return new ToolCaseMenu(containerId, inventory, this);
    }

}
