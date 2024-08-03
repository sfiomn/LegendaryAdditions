package sfiomn.legendary_additions.entities;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class XpBottleEntity extends ThrowableItemProjectile {
    private int experienceAmount;
    public XpBottleEntity(int experienceAmount, EntityType<? extends XpBottleEntity> entityType, Level level) {
        super(entityType, level);
        this.experienceAmount = experienceAmount;
    }

    public XpBottleEntity(int experienceAmount, EntityType<? extends XpBottleEntity> entityType, Level level, LivingEntity entity) {
        super(entityType, entity, level);
        this.experienceAmount = experienceAmount;
    }

    public XpBottleEntity(int experienceAmount, EntityType<? extends XpBottleEntity> entityType, Level level, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(entityType, p_i1787_2_, p_i1787_4_, p_i1787_6_, level);
        this.experienceAmount = experienceAmount;
    }

    protected float getGravity() {
        return 0.07F;
    }

    protected int getSpritesColor() {
        return PotionUtils.getColor(Potions.WATER);
    }

    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().levelEvent(2002, this.blockPosition(), getSpritesColor());

            while(this.experienceAmount > 0) {
                int j = ExperienceOrb.getExperienceValue(this.experienceAmount);
                this.experienceAmount -= j;
                this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY(), this.getZ(), j));
            }

            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected abstract @NotNull Item getDefaultItem();

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
