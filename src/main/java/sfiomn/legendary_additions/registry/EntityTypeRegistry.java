package sfiomn.legendary_additions.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.entities.*;
import sfiomn.legendary_additions.tileentities.LegendaryXpBottleEntity;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, LegendaryAdditions.MOD_ID);

    public static final RegistryObject<EntityType<TinyXpBottleEntity>> TINY_XP_BOTTLE_ENTITY = ENTITY_TYPES.register( "tiny_xp_bottle",
            () -> EntityType.Builder.of((EntityType.IFactory<TinyXpBottleEntity>) TinyXpBottleEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryAdditions.MOD_ID, "tiny_xp_bottle").toString()));
    public static final RegistryObject<EntityType<CommonXpBottleEntity>> COMMON_XP_BOTTLE_ENTITY = ENTITY_TYPES.register("common_xp_bottle",
            () -> EntityType.Builder.of((EntityType.IFactory<CommonXpBottleEntity>) CommonXpBottleEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryAdditions.MOD_ID, "common_xp_bottle").toString()));
    public static final RegistryObject<EntityType<RareXpBottleEntity>> RARE_XP_BOTTLE_ENTITY = ENTITY_TYPES.register("rare_xp_bottle",
            () -> EntityType.Builder.of((EntityType.IFactory<RareXpBottleEntity>) RareXpBottleEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryAdditions.MOD_ID, "rare_xp_bottle").toString()));
    public static final RegistryObject<EntityType<EpicXpBottleEntity>> EPIC_XP_BOTTLE_ENTITY = ENTITY_TYPES.register("epic_xp_bottle",
            () -> EntityType.Builder.of((EntityType.IFactory<EpicXpBottleEntity>) EpicXpBottleEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryAdditions.MOD_ID, "epic_xp_bottle").toString()));
    public static final RegistryObject<EntityType<LegendaryXpBottleEntity>> LEGENDARY_XP_BOTTLE_ENTITY = ENTITY_TYPES.register("legendary_xp_bottle",
            () -> EntityType.Builder.of((EntityType.IFactory<LegendaryXpBottleEntity>) LegendaryXpBottleEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryAdditions.MOD_ID, "legendary_xp_bottle").toString()));

    public static final RegistryObject<EntityType<ForestKeyEntity>> FOREST_KEY_ENTITY = ENTITY_TYPES.register("forest_key",
            () -> EntityType.Builder.of(ForestKeyEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(32)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryAdditions.MOD_ID, "forest_key").toString()));

    public static final RegistryObject<EntityType<DesertKeyEntity>> DESERT_KEY_ENTITY = ENTITY_TYPES.register("desert_key",
            () -> EntityType.Builder.of(DesertKeyEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(32)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryAdditions.MOD_ID, "desert_key").toString()));


    public static final RegistryObject<EntityType<SeatEntity>> SEAT_ENTITY = ENTITY_TYPES.register("seat_entity",
            () -> EntityType.Builder.of((EntityType.IFactory<SeatEntity>) SeatEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .build(new ResourceLocation(LegendaryAdditions.MOD_ID, "seat_entity").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
