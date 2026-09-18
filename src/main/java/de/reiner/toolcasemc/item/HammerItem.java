package de.reiner.toolcasemc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HammerItem extends Item {
    public HammerItem(Item.Properties properties) {
        super(properties);
    }
    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        for (int x=pos.getX()-1; x<=pos.getX()+1; x++){
            for(int y=pos.getY()-1; y<=pos.getY()+1; y++){
                for(int z=pos.getZ()-1; z<=pos.getZ()+1; z++){
                    level.destroyBlock(new BlockPos(x,y,z), true, entity, 512);
                }
            }
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }
}
