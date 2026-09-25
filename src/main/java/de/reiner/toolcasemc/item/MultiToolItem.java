package de.reiner.toolcasemc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MultiToolItem extends Item {
    public MultiToolItem(Item.Properties properties){
        super(properties);
    }

    @Override
    public float getDestroySpeed(final ItemStack itemStack, final BlockState state) {
        return 6.0F;
    }

    @Override
    public boolean mineBlock(final ItemStack itemStack, final Level level, final BlockState state, final BlockPos pos, final LivingEntity owner) {
        if (!level.isClientSide() && state.getDestroySpeed(level, pos) != 0.0F) {
            itemStack.hurtAndBreak(1, owner, EquipmentSlot.MAINHAND);
        }
        boolean dropResource = state.is(BlockTags.MINEABLE_WITH_PICKAXE);
        if (dropResource) {
            Block.dropResources(state, level, pos, level.getBlockEntity(pos), owner, itemStack);
        }
        return level.setBlock(pos, level.getFluidState(pos).createLegacyBlock(), 3, 1);
    }
}
