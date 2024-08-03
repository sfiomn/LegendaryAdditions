package sfiomn.legendary_additions.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blockentities.*;

public class BlockEntityRegistry {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LegendaryAdditions.MOD_ID);

    public static RegistryObject<BlockEntityType<ObeliskBlockEntity>> OBELISK_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(LegendaryAdditions.MOD_ID + "obelisk_tile_entity", () -> BlockEntityType.Builder
                    .of(ObeliskBlockEntity::new, BlockRegistry.OBELISK_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<MeatRackBlockEntity>> MEAT_RACK_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(LegendaryAdditions.MOD_ID + "meat_rack_tile_entity", () -> BlockEntityType.Builder
                    .of(MeatRackBlockEntity::new, BlockRegistry.MEAT_RACK_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<HoneyPondBlockEntity>> HONEY_POND_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(LegendaryAdditions.MOD_ID + "honey_pond_tile_entity", () -> BlockEntityType.Builder
                    .of(HoneyPondBlockEntity::new, BlockRegistry.HONEY_POND_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<XpStorageBlockEntity>> XP_STORAGE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(LegendaryAdditions.MOD_ID + "xp_storage_tile_entity", () -> BlockEntityType.Builder
                    .of(XpStorageBlockEntity::new, BlockRegistry.XP_STORAGE_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<SpiderEggsBlockEntity>> SPIDER_EGGS_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(LegendaryAdditions.MOD_ID + "spider_eggs_tile_entity", () -> BlockEntityType.Builder
                    .of(SpiderEggsBlockEntity::new, BlockRegistry.SPIDER_EGGS_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
