package sfiomn.legendary_additions.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.text.Color;
import net.minecraft.world.World;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

import java.awt.*;
import java.util.Random;

public class TinyXpBottleEntity extends XpBottleEntity {

    public static final Random random = new Random();
    public static final int experienceAmount = 18 + random.nextInt(6);
    public TinyXpBottleEntity(EntityType<? extends TinyXpBottleEntity> entityType, World world) {
        super(experienceAmount, entityType, world);
    }

    public TinyXpBottleEntity(World world, LivingEntity entity) {
        super(experienceAmount, EntityTypeRegistry.TINY_XP_BOTTLE_ENTITY.get(), world, entity);
    }

    public TinyXpBottleEntity(World world, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(experienceAmount, EntityTypeRegistry.TINY_XP_BOTTLE_ENTITY.get(), world, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }

    @Override
    protected int getSpritesColor() {
        return 8838206;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.TINY_XP_BOTTLE_ITEM.get();
    }
}
