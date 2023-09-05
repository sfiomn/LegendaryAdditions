package sfiomn.legendary_additions.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceBottleEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class XpBottleEntity extends ProjectileItemEntity {
    private int experienceAmount;
    public XpBottleEntity(int experienceAmount, EntityType<? extends XpBottleEntity> entityType, World world) {
        super(entityType, world);
        this.experienceAmount = experienceAmount;
    }

    public XpBottleEntity(int experienceAmount, EntityType<? extends XpBottleEntity> entityType, World world, LivingEntity entity) {
        super(entityType, entity, world);
        this.experienceAmount = experienceAmount;
    }

    public XpBottleEntity(int experienceAmount, EntityType<? extends XpBottleEntity> entityType, World world, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(entityType, p_i1787_2_, p_i1787_4_, p_i1787_6_, world);
        this.experienceAmount = experienceAmount;
    }

    protected float getGravity() {
        return 0.07F;
    }

    protected int getSpritesColor() {
        return PotionUtils.getColor(Potions.WATER);
    }

    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        if (!this.level.isClientSide) {
            this.level.levelEvent(2002, this.blockPosition(), getSpritesColor());

            while(this.experienceAmount > 0) {
                int j = ExperienceOrbEntity.getExperienceValue(this.experienceAmount);
                this.experienceAmount -= j;
                this.level.addFreshEntity(new ExperienceOrbEntity(this.level, this.getX(), this.getY(), this.getZ(), j));
            }

            this.remove();
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
