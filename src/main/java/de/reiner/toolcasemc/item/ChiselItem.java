package de.reiner.toolcasemc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

public class ChiselItem extends Item {
    private static final HashMap<Block, Block> BLOCK_HASH_MAP = new HashMap<>() {{
        put(Blocks.STONE, Blocks.STONE_BRICKS);
        put(Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
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
        put(Blocks.END_STONE, Blocks.END_STONE_BRICKS);
        put(Blocks.SULFUR, Blocks.POLISHED_SULFUR);
        put(Blocks.POLISHED_SULFUR, Blocks.SULFUR_BRICKS);
        put(Blocks.RESIN_BLOCK, Blocks.RESIN_BRICKS);
        put(Blocks.RESIN_BRICKS, Blocks.CHISELED_RESIN_BRICKS);
        put(Blocks.PACKED_MUD, Blocks.MUD_BRICKS);
        put(Blocks.COPPER_BLOCK.weathering().unaffected(), Blocks.CUT_COPPER.weathering().unaffected());
        put(Blocks.CUT_COPPER.weathering().unaffected(), Blocks.CHISELED_COPPER.weathering().unaffected());
        put(Blocks.COPPER_BLOCK.weathering().exposed(), Blocks.CUT_COPPER.weathering().exposed());
        put(Blocks.CUT_COPPER.weathering().exposed(), Blocks.CHISELED_COPPER.weathering().exposed());
        put(Blocks.COPPER_BLOCK.weathering().weathered(), Blocks.CUT_COPPER.weathering().weathered());
        put(Blocks.CUT_COPPER.weathering().weathered(), Blocks.CHISELED_COPPER.weathering().weathered());
        put(Blocks.COPPER_BLOCK.weathering().oxidized(), Blocks.CUT_COPPER.weathering().oxidized());
        put(Blocks.CUT_COPPER.weathering().oxidized(), Blocks.CHISELED_COPPER.weathering().oxidized());
        put(Blocks.COPPER_BLOCK.waxed().unaffected(), Blocks.CUT_COPPER.waxed().unaffected());
        put(Blocks.CUT_COPPER.waxed().unaffected(), Blocks.CHISELED_COPPER.waxed().unaffected());
        put(Blocks.COPPER_BLOCK.waxed().exposed(), Blocks.CUT_COPPER.waxed().exposed());
        put(Blocks.CUT_COPPER.waxed().exposed(), Blocks.CHISELED_COPPER.waxed().exposed());
        put(Blocks.COPPER_BLOCK.waxed().weathered(), Blocks.CUT_COPPER.waxed().weathered());
        put(Blocks.CUT_COPPER.waxed().weathered(), Blocks.CHISELED_COPPER.waxed().weathered());
        put(Blocks.COPPER_BLOCK.waxed().oxidized(), Blocks.CUT_COPPER.waxed().oxidized());
        put(Blocks.CUT_COPPER.waxed().oxidized(), Blocks.CHISELED_COPPER.waxed().oxidized());

    }};
    public ChiselItem(Item.Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult useOn(final UseOnContext context) {
        if(!context.getLevel().isClientSide()){
            BlockPos blockPos = context.getClickedPos();
            Level level = (ServerLevel) context.getLevel();
            InteractionHand hand = context.getHand();
            Player player = (ServerPlayer) context.getPlayer();
            Block keyBlock = level.getBlockState(blockPos).getBlock();
            if (BLOCK_HASH_MAP.containsKey(keyBlock)) {
                BlockState blockState = BLOCK_HASH_MAP.get(keyBlock).defaultBlockState();
                level.setBlock(blockPos, blockState, Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS, 512);
                player.getItemInHand(hand).hurtAndBreak(1, player, hand);
                level.playSound(null, blockPos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            if(BLOCK_HASH_MAP.containsKey(context.getLevel().getBlockState(context.getClickedPos()).getBlock())){
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}
