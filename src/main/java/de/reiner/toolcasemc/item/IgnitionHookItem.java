package de.reiner.toolcasemc.item;

import de.reiner.toolcasemc.entity.Spark;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class IgnitionHookItem extends Item implements ProjectileItem {
    public IgnitionHookItem(Item.Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        if (player.getCooldowns().isOnCooldown(player.getItemInHand(hand))){
            return InteractionResult.PASS;
        } else {
            ItemStack itemStack = player.getItemInHand(hand);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (level instanceof ServerLevel serverLevel) {
                Projectile.spawnProjectileFromRotation(Spark::new, serverLevel, itemStack, player, 0.0F, 1.5F, 1.0F);
            }
            player.getItemInHand(hand).hurtAndBreak(1, player, hand);
            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(player.getItemInHand(hand), 30);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public Projectile asProjectile(final Level level, final Position position, final ItemStack itemStack, final Direction direction) {
        return new Spark(level, position.x(), position.y(), position.z(), itemStack);
    }
}
