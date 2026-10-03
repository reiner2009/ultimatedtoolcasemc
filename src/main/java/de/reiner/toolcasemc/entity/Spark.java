package de.reiner.toolcasemc.entity;


import de.reiner.toolcasemc.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityEvent.Value;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Spark extends ThrowableItemProjectile {
    public Spark(final EntityType<? extends de.reiner.toolcasemc.entity.Spark> type, final Level level) {
        super(type, level);
    }

    public Spark(final Level level, final LivingEntity mob, final ItemStack itemStack) {
        super(ModEntityTypes.SPARK, mob, level, itemStack);
    }

    public Spark(final Level level, final double x, final double y, final double z, final ItemStack itemStack) {
        super(ModEntityTypes.SPARK, x, y, z, level, itemStack);
    }

    @Override
    protected Item getDefaultItem(){
        return ModItems.IGNITION_HOOK;
    }

    private ParticleOptions getParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    public void handleEntityEvent(final @Value byte id) {
        if (id == 3) {
            ParticleOptions particle = this.getParticle();
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
            }
        }

    }

    @Override
    protected void onHitEntity(final EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        int damage = entity instanceof SnowGolem ? 3 : 0;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float)damage);
    }

    @Override
    protected void onHit(final HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.level().isClientSide()) {
            if (hitResult instanceof BlockHitResult blockHitResult) {
                BlockPos pos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
                BlockState fireState = BaseFireBlock.getState(this.level(), pos);
                if (fireState.is(Blocks.FIRE)) {
                    this.level().setBlock(pos,fireState, Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);
                }
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public void tick(){
        if( this.level().getBlockState(this.blockPosition()).getBlock().equals(Blocks.WATER) ||this.level().getBlockState(this.blockPosition()).getBlock().equals(Blocks.WATER_CAULDRON)){
            this.level().playSound((Entity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.NEUTRAL, 0.5F, 1.0F);
            this.discard();
        }
        if( this.level().getBlockState(this.blockPosition()).getBlock().equals(Blocks.LAVA) || this.level().getBlockState(this.blockPosition()).getBlock().equals(Blocks.LAVA_CAULDRON)){
            this.discard();
        }
        super.tick();
    }
}
