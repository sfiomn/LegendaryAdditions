package sfiomn.legendary_additions.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

import java.util.Random;

public class RareXpBottleEntity extends XpBottleEntity {

    public static final Random random = new Random();
    public static final int experienceAmount = 96 + random.nextInt(18);
    public RareXpBottleEntity(EntityType<? extends RareXpBottleEntity> entityType, Level level) {
        super(experienceAmount, entityType, level);
    }

    public RareXpBottleEntity(Level world, LivingEntity entity) {
        super(experienceAmount, EntityTypeRegistry.RARE_XP_BOTTLE_ENTITY.get(), world, entity);
    }

    public RareXpBottleEntity(Level world, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(experienceAmount, EntityTypeRegistry.RARE_XP_BOTTLE_ENTITY.get(), world, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }

    @Override
    protected int getSpritesColor() {
        return 706303;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.RARE_XP_BOTTLE_ITEM.get();
    }
}
