package de.reiner.toolcasemc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public class MultiToolItem extends Item {
    private static final Block[] FARMLAND_BLOCKS = {
            Blocks.DIRT,
            Blocks.GRASS_BLOCK,
            Blocks.DIRT_PATH
    };
    public MultiToolItem(Item.Properties properties){
        super(properties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 8.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
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

    private InteractionResult defaultUseOn(UseOnContext context){
        ItemStack stack = context.getItemInHand();
        Holder<BlockTransformer> blockTransformer = (Holder)stack.get(DataComponents.BLOCK_TRANSFORMER);
        return (InteractionResult)(blockTransformer != null ? ((BlockTransformer)blockTransformer.value()).transformBlock(context) : InteractionResult.PASS);
    }

    @Override
    public InteractionResult useOn(UseOnContext context){
        if(!context.getLevel().isClientSide()){
            if(Arrays.asList(FARMLAND_BLOCKS).contains(context.getLevel().getBlockState(context.getClickedPos()).getBlock())) {
                Level level = (ServerLevel) context.getLevel();
                BlockPos blockPos = context.getClickedPos();
                InteractionHand hand = context.getHand();
                Player player = (ServerPlayer) context.getPlayer();
                BlockState blockState = Blocks.FARMLAND.defaultBlockState();
                level.setBlock(blockPos, blockState, Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS, 512);
                player.getItemInHand(hand).hurtAndBreak(1, player, hand);
                level.playSound(null, blockPos, SoundEvents.HOE_TILL.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else {
                return defaultUseOn(context);
            }
        } else {
            if(Arrays.asList(FARMLAND_BLOCKS).contains(context.getLevel().getBlockState(context.getClickedPos()).getBlock())){
                return InteractionResult.SUCCESS;
            } else{
                return defaultUseOn(context);
            }
        }
    }
}
