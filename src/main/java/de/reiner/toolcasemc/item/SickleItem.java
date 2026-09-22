package de.reiner.toolcasemc.item;

import de.reiner.toolcasemc.entity.ThrownSickle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SickleItem extends Item implements ProjectileItem {
    public SickleItem(Item.Properties properties){
        super(properties);
    }

    public static boolean isPlantBlock(int x, int y, int z, Level level){
        Block block = level.getBlockState(new BlockPos(x, y, z)).getBlock();
        return block instanceof VegetationBlock || block == Blocks.SUGAR_CANE;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity){
        for(int x=pos.getX()-1; x<=pos.getX()+1; x++){
            for(int z=pos.getZ()-1; z<=pos.getZ()+1; z++){
                if (isPlantBlock(x, pos.getY(), z, level)){
                    level.destroyBlock(new BlockPos(x, pos.getY(), z), true, entity, 512);
                }
            }
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 72000;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.TRIDENT;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int remainingTime) {
        if (!(entity instanceof Player player)) {
            return false;
        }
        int timeHeld = this.getUseDuration(itemStack, entity) - remainingTime;
        if (timeHeld < 10) {
            return false;
        }
        if (level instanceof ServerLevel serverLevel) {
            ItemStack thrownItemStack = itemStack.consumeAndReturn(1, player);
            thrownItemStack.hurtAndBreak(1, player, player.getUsedItemHand());
            ThrownSickle sickle = new ThrownSickle(serverLevel, player, thrownItemStack);
            sickle.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
            sickle.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            serverLevel.addFreshEntity(sickle);
            if (player.hasInfiniteMaterials()) {
                sickle.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            return true;
        }
        return true;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        ThrownSickle sickle = new ThrownSickle(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
        sickle.pickup = AbstractArrow.Pickup.ALLOWED;
        return sickle;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.nextDamageWillBreak()) {
            return InteractionResult.FAIL;
        } else {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
    }
}
