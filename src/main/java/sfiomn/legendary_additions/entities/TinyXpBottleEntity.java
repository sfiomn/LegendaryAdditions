package sfiomn.legendary_additions.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

import java.util.Random;

public class TinyXpBottleEntity extends XpBottleEntity {

    public static final Random random = new Random();
    public static final int experienceAmount = 18 + random.nextInt(6);
    public TinyXpBottleEntity(EntityType<? extends TinyXpBottleEntity> entityType, Level level) {
        super(experienceAmount, entityType, level);
    }

    public TinyXpBottleEntity(Level level, LivingEntity entity) {
        super(experienceAmount, EntityTypeRegistry.TINY_XP_BOTTLE_ENTITY.get(), level, entity);
    }

    public TinyXpBottleEntity(Level level, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(experienceAmount, EntityTypeRegistry.TINY_XP_BOTTLE_ENTITY.get(), level, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }

    @Override
    protected int getSpritesColor() {
        return 8838206;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.TINY_XP_BOTTLE_ITEM.get();
    }
}
