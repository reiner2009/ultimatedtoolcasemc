package de.reiner.toolcasemc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.HashMap;

public class ChiselItem extends Item {
    public static final HashMap<Block, Block> BLOCK_HASH_MAP = new HashMap<>() {{
        put(Blocks.STONE, Blocks.STONE_BRICKS);
        put(Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
        put(Blocks.COBBLESTONE, Blocks.STONE);
        put(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE);
        put(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE);
        put(Blocks.NETHER_BRICKS, Blocks.CHISELED_NETHER_BRICKS);
        put(Blocks.DEEPSLATE, Blocks.POLISHED_DEEPSLATE);
        put(Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);
        put(Blocks.DEEPSLATE_BRICKS, Blocks.CHISELED_DEEPSLATE);
        put(Blocks.BLACKSTONE, Blocks.POLISHED_BLACKSTONE);
        put(Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS);
        put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CHISELED_POLISHED_BLACKSTONE);
        put(Blocks.TUFF, Blocks.POLISHED_TUFF);
        put(Blocks.POLISHED_TUFF, Blocks.TUFF_BRICKS);
        put(Blocks.TUFF_BRICKS, Blocks.CHISELED_TUFF_BRICKS);
        put(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_BRICKS);
        put(Blocks.QUARTZ_BRICKS, Blocks.CHISELED_QUARTZ_BLOCK);
        put(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR);
        put(Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS);
        put(Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE);
        put(Blocks.GRANITE, Blocks.POLISHED_GRANITE);
        put(Blocks.DIORITE, Blocks.POLISHED_DIORITE);
        put(Blocks.ANDESITE, Blocks.POLISHED_ANDESITE);
    }};
    public ChiselItem(Item.Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand){
        HitResult hit = player.pick(5.0D, 0.0F, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
            Block keyBlock=level.getBlockState(blockPos).getBlock();
            if(BLOCK_HASH_MAP.containsKey(keyBlock)){
                BlockState blockState=BLOCK_HASH_MAP.get(keyBlock).defaultBlockState();
                level.setBlock(blockPos, blockState, Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS, 512);
                player.getItemInHand(hand).hurtAndBreak(1, player, hand);
                level.playSound(null, blockPos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }else{
                return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }
}
