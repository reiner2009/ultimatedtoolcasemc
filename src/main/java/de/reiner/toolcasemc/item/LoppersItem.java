package de.reiner.toolcasemc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class LoppersItem extends Item {
    public LoppersItem(Item.Properties properties){
        super(properties);
    }

    public static Tool createToolProperties() {
        HolderGetter<Block> registrationLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return new Tool(List.of(Tool.Rule.overrideSpeed(registrationLookup.getOrThrow(BlockTags.SHEARS_EXTREME_BREAKING_SPEED), 15.0F), Tool.Rule.overrideSpeed(registrationLookup.getOrThrow(BlockTags.SHEARS_MAJOR_BREAKING_SPEED), 5.0F), Tool.Rule.overrideSpeed(registrationLookup.getOrThrow(BlockTags.SHEARS_MINOR_BREAKING_SPEED), 2.0F)), 1.0F, 1, true);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        for (int x=pos.getX()-1; x<=pos.getX()+1; x++){
            for(int y=pos.getY()-1; y<=pos.getY()+1; y++){
                for(int z=pos.getZ()-1; z<=pos.getZ()+1; z++){
                    final boolean isTargetBlock = (pos.getX()==x && pos.getY()==y && pos.getZ()==z);
                    boolean canBreak = level.getBlockState(new BlockPos(x,y,z)).is(BlockTags.LEAVES);
                    if(canBreak) {
                        if (state.is(BlockTags.LEAVES)) {
                            level.setBlock(new BlockPos(x,y,z), level.getFluidState(new BlockPos(x,y,z)).createLegacyBlock(), 3, 1);
                            Block.popResource(level, pos, new ItemStack(state.getBlock().asItem()));
                        }
                        else {
                            level.destroyBlock(new BlockPos(x, y, z), isTargetBlock, entity, 512);
                        }
                    }
                }
            }
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

}
