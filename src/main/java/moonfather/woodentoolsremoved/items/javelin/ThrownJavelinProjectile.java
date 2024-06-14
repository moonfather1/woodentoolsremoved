package moonfather.woodentoolsremoved.items.javelin;

import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class ThrownJavelinProjectile extends AbstractArrow
{
    private ItemStack tridentItem = new ItemStack(RegistryManager.ItemJavelin.get());
    private boolean dealtDamage;



    public ThrownJavelinProjectile(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_) {
        super(RegistryManager.ThrownJavelinProjectileET.get(), p_37570_, p_37569_, p_37571_, null);
        this.tridentItem = p_37571_.copy();
    }

    public ThrownJavelinProjectile(EntityType<ThrownJavelinProjectile> thrownJavelinProjectileEntityType, Level level) {
        super(thrownJavelinProjectileEntityType, level);
        this.tridentItem = new ItemStack(RegistryManager.ItemJavelin.get());
    }

    @Override
    protected ItemStack getDefaultPickupItem()
    {
        return new ItemStack(RegistryManager.ItemJavelin.get());
    }

    ////////////////////////////////////

    public static float GetScale()
    {
        return 0.50f;
    }


    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        super.tick();
    }



    @Override
    protected ItemStack getPickupItem() {
        return this.tridentItem.copy();
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 p_37575_, Vec3 p_37576_) {
        return this.dealtDamage ? null : super.findHitEntity(p_37575_, p_37576_);
    }



    @Override
    protected void onHitEntity(EntityHitResult p_37573_) {
        Entity entity = p_37573_.getEntity();
        float f = 7.0F;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, (Entity)(entity1 == null ? this : entity1));
        if (this.level() instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        this.dealtDamage = true;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.level() instanceof ServerLevel serverlevel1) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingentity) {
                this.doKnockback(livingentity, damagesource);
                this.doPostHurtEffects(livingentity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }




    @Override
    public ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }

    @Override
    protected boolean tryPickup(Player p_150196_) {
        return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player p_37580_) {
        if (this.ownedBy(p_37580_) || this.getOwner() == null) {
            super.playerTouch(p_37580_);
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Trident", 10)) {
            this.tridentItem = ItemStack.parse(this.registryAccess(), tag.getCompound("Trident")).orElse(this.getDefaultPickupItem());
        }
        this.dealtDamage = tag.getBoolean("DealtDamage");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_37582_) {
        super.addAdditionalSaveData(p_37582_);
        p_37582_.put("Trident", this.tridentItem.save(this.registryAccess(), new CompoundTag()));
        p_37582_.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double p_37588_, double p_37589_, double p_37590_) {
        return true;
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 1200*6) {
            this.remove(RemovalReason.DISCARDED); // 6x the arrow life
        }
    }
    private int life = 0;
}
