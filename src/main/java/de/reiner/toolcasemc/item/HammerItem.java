package de.reiner.toolcasemc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HammerItem extends Item {
    private TagKey mineable;
    private int color;
    public HammerItem(Item.Properties properties, TagKey mineableTag, int textColor) {
        super(properties);
        this.mineable=mineableTag;
        this.color=textColor;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        for (int x=pos.getX()-1; x<=pos.getX()+1; x++){
            for(int y=pos.getY()-1; y<=pos.getY()+1; y++){
                for(int z=pos.getZ()-1; z<=pos.getZ()+1; z++){
                    final boolean isTargetBlock = (pos.getX()==x && pos.getY()==y && pos.getZ()==z);
                    boolean canBreak = level.getBlockState(new BlockPos(x,y,z)).is(this.mineable);
                    if(canBreak) {
                        level.destroyBlock(new BlockPos(x, y, z), isTargetBlock, entity, 512);
                    }
                }
            }
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy()
                .withStyle(style -> style
                        .withColor(this.color)
                        .withBold(true)
                        .withItalic(false)
                );
    }
}
