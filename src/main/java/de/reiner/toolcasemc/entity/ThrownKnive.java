package de.reiner.toolcasemc.entity;

import de.reiner.toolcasemc.damageType.ModDamageType;
import de.reiner.toolcasemc.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class ThrownKnive extends AbstractArrow {
    private boolean dealtDamage = false;

    public ThrownKnive(ServerLevel level, LivingEntity owner, ItemStack stack) {
        super(ModEntityTypes.KNIVE, level);
        this.setOwner(owner);
        this.setPickupItemStack(stack);
    }

    public ThrownKnive(EntityType<ThrownKnive> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownKnive(Level level, double x, double y, double z, ItemStack stack) {
        super(ModEntityTypes.KNIVE, level);
        this.setPos(x, y, z);
        this.setPickupItemStack(stack);
    }

    @Override
    protected void defineSynchedData(final SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        super.tick();
    }

    @Override
    protected @Nullable EntityHitResult findHitEntity(final Vec3 from, final Vec3 to) {
        return this.dealtDamage ? null : super.findHitEntity(from, to);
    }

    @Override
    protected Collection<EntityHitResult> findHitEntities(final Vec3 from, final Vec3 to) {
        EntityHitResult e = this.findHitEntity(from, to);
        return e != null ? List.of(e) : List.of();
    }

    @Override
    protected void onHitEntity(final EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        float dmg = 6.0F;
        Entity currentOwner = this.getOwner();
        DamageSource damageSource = new DamageSource(level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).get(ModDamageType.KNIVE_DAMAGE.identifier()).orElseThrow(), this, currentOwner);
        Level var7 = this.level();
        if (var7 instanceof ServerLevel serverLevel) {
            dmg = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, dmg);
        }
        this.dealtDamage = true;
        boolean wasHurt = entity.hurtOrSimulate(damageSource, dmg);
        if (wasHurt) {
            Level var8 = this.level();
            if (var8 instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)var8;
                EnchantmentHelper.doPostAttackEffectsWithItemSourceOnBreak(serverLevel, entity, damageSource, this.getWeaponItem(), (weapon) -> this.kill(serverLevel));
            }
            if (entity instanceof LivingEntity) {
                LivingEntity mob = (LivingEntity)entity;
                this.doKnockback(mob, damageSource);
                this.doPostHurtEffects(mob);
            }
        }
        if (entity.projectileReceivesSideEffectsOnHit(wasHurt)) {
            this.deflect(ProjectileDeflection.REVERSE, entity, this.owner, false, new Vec3(0.02, 0.2, 0.02));
        }

    }

    @Override
    protected void hitBlockEnchantmentEffects(final ServerLevel level, final BlockHitResult hitResult, final ItemStack weapon) {
        Vec3 compensatedHitPosition = hitResult.getBlockPos().clampLocationWithin(hitResult.getLocation());
        Entity var6 = this.getOwner();
        LivingEntity var10002;
        if (var6 instanceof LivingEntity livingOwner) {
            var10002 = livingOwner;
        } else {
            var10002 = null;
        }
        EnchantmentHelper.onHitBlock(level, weapon, var10002, this, null, compensatedHitPosition, level.getBlockState(hitResult.getBlockPos()), (item) -> this.kill(level));
    }

    @Override
    public ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }

    @Override
    protected boolean tryPickup(final Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.THROWING_KNIVES);
    }

    @Override
    public void playerTouch(final Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }

    }

    @Override
    protected void readAdditionalSaveData(final ValueInput input) {
        super.readAdditionalSaveData(input);
        this.dealtDamage = input.getBooleanOr("DealtDamage", false);
    }

    @Override
    protected void addAdditionalSaveData(final ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(final double camX, final double camY, final double camZ) {
        return true;
    }
}
