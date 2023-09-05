package sfiomn.legendary_additions.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

import java.util.Random;

public class EpicXpBottleEntity extends XpBottleEntity {

    public static final Random random = new Random();
    public static final int experienceAmount = 420 + random.nextInt(60);
    public EpicXpBottleEntity(EntityType<? extends EpicXpBottleEntity> entityType, World world) {
        super(experienceAmount, entityType, world);
    }

    public EpicXpBottleEntity(World world, LivingEntity entity) {
        super(experienceAmount, EntityTypeRegistry.EPIC_XP_BOTTLE_ENTITY.get(), world, entity);
    }

    public EpicXpBottleEntity(World world, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(experienceAmount, EntityTypeRegistry.EPIC_XP_BOTTLE_ENTITY.get(), world, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }

    @Override
    protected int getSpritesColor() {
        return 16728618;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.EPIC_XP_BOTTLE_ITEM.get();
    }
}
