package de.reiner.toolcasemc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RakeItem extends Item {
    public RakeItem(Item.Properties properties) {
        super(properties);
    }

    private static boolean canTurnToDirtPath(Level level, BlockPos pos){
        return level.getBlockState(pos.above()).isAir() || SickleItem.isPlantBlock(pos.getX(), pos.getY()+1, pos.getZ(), level);
    }

    @Override
    public InteractionResult useOn(UseOnContext context){
        if(!context.getLevel().isClientSide()){
            Level level=(ServerLevel)context.getLevel();
            BlockPos pos=context.getClickedPos();
            BlockState blockState=level.getBlockState(pos);
            Player player=(ServerPlayer)context.getPlayer();
            InteractionHand hand=context.getHand();
            if((blockState.is(BlockTags.TURNS_INTO_DIRT_PATH) && canTurnToDirtPath(level, pos)) || (blockState.getBlock() instanceof VegetationBlock && level.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).is(BlockTags.TURNS_INTO_DIRT_PATH))){
                if (blockState.getBlock() instanceof VegetationBlock){
                    pos=new BlockPos(pos.getX(), pos.getY()-1, pos.getZ());
                }
                for(int x=pos.getX()-1; x<=pos.getX()+1; x++) {
                    for(int y=pos.getY()-1; y<=pos.getY()+1; y++){
                        for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
                            if (canTurnToDirtPath(level, new BlockPos(x, y, z)) && level.getBlockState(new BlockPos(x, y, z)).is(BlockTags.TURNS_INTO_DIRT_PATH)) {
                                level.setBlock(new BlockPos(x, y, z), Blocks.DIRT_PATH.defaultBlockState(), Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS, 512);
                            }
                        }
                    }
                }
                player.getItemInHand(hand).hurtAndBreak(1, player, hand);
                level.playSound(null, pos, SoundEvents.HOE_TILL.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            if((context.getLevel().getBlockState(context.getClickedPos()).is(BlockTags.TURNS_INTO_DIRT_PATH) && canTurnToDirtPath(context.getLevel(), context.getClickedPos())) || (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof VegetationBlock && context.getLevel().getBlockState(new BlockPos(context.getClickedPos().getX(), context.getClickedPos().getY()-1, context.getClickedPos().getZ())).is(BlockTags.TURNS_INTO_DIRT_PATH))){
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}
