package sfiomn.legendary_additions.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

import java.util.Random;

public class LegendaryXpBottleEntity extends XpBottleEntity {

    public static final Random random = new Random();
    public static final int experienceAmount = 1020 + random.nextInt(120);
    public LegendaryXpBottleEntity(EntityType<? extends LegendaryXpBottleEntity> entityType, World world) {
        super(experienceAmount, entityType, world);
    }

    public LegendaryXpBottleEntity(World world, LivingEntity entity) {
        super(experienceAmount, EntityTypeRegistry.LEGENDARY_XP_BOTTLE_ENTITY.get(), world, entity);
    }

    public LegendaryXpBottleEntity(World world, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(experienceAmount, EntityTypeRegistry.LEGENDARY_XP_BOTTLE_ENTITY.get(), world, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }

    @Override
    protected int getSpritesColor() {
        return 16768839;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.LEGENDARY_XP_BOTTLE_ITEM.get();
    }
}
