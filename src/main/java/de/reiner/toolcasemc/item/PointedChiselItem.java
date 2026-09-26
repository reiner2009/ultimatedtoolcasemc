package de.reiner.toolcasemc.item;

import de.reiner.toolcasemc.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class PointedChiselItem extends Item {
    public PointedChiselItem(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult useOn(final UseOnContext context) {
        if(!context.getLevel().isClientSide()){
            BlockPos blockPos = context.getClickedPos();
            Level level = (ServerLevel) context.getLevel();
            InteractionHand hand = context.getHand();
            Player player = (ServerPlayer) context.getPlayer();
            if (context.getLevel().getBlockState(context.getClickedPos()).is(ModBlockTags.MINEABLE_WITH_POINTED_CHISEL)) {
                level.destroyBlock(blockPos, true, player, 512);
                player.getItemInHand(hand).hurtAndBreak(1, player, hand);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            if(context.getLevel().getBlockState(context.getClickedPos()).is(ModBlockTags.MINEABLE_WITH_POINTED_CHISEL)){
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}
