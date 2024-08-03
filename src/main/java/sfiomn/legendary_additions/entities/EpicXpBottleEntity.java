package sfiomn.legendary_additions.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

import java.util.Random;

public class EpicXpBottleEntity extends XpBottleEntity {

    public static final Random random = new Random();
    public static final int experienceAmount = 420 + random.nextInt(60);
    public EpicXpBottleEntity(EntityType<? extends EpicXpBottleEntity> entityType, Level level) {
        super(experienceAmount, entityType, level);
    }

    public EpicXpBottleEntity(Level level, LivingEntity entity) {
        super(experienceAmount, EntityTypeRegistry.EPIC_XP_BOTTLE_ENTITY.get(), level, entity);
    }

    public EpicXpBottleEntity(Level level, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(experienceAmount, EntityTypeRegistry.EPIC_XP_BOTTLE_ENTITY.get(), level, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }

    @Override
    protected int getSpritesColor() {
        return 16728618;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.EPIC_XP_BOTTLE_ITEM.get();
    }
}
