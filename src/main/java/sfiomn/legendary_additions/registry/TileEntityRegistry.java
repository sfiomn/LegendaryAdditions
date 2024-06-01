package sfiomn.legendary_additions.registry;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.tileentities.*;

public class TileEntityRegistry {
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LegendaryAdditions.MOD_ID);
    public static RegistryObject<TileEntityType<ForestDungeonGateTileEntity>> FOREST_DUNGEON_GATE_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "forest_dungeon_gate_tile_entity", () -> TileEntityType.Builder.of(
                    ForestDungeonGateTileEntity::new, BlockRegistry.FOREST_DUNGEON_GATE_BLOCK.get()).build(null));
    public static RegistryObject<TileEntityType<ForestDungeonHeartTileEntity>> FOREST_DUNGEON_HEART_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "forest_dungeon_heart_tile_entity", () -> TileEntityType.Builder.of(
                    ForestDungeonHeartTileEntity::new, BlockRegistry.FOREST_DUNGEON_HEART_BLOCK.get()).build(null));
    public static RegistryObject<TileEntityType<ObeliskTileEntity>> OBELISK_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "obelisk_tile_entity", () -> TileEntityType.Builder.of(
                    ObeliskTileEntity::new, BlockRegistry.OBELISK_BLOCK.get()).build(null));

    public static RegistryObject<TileEntityType<MeatRackTileEntity>> MEAT_RACK_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "meat_rack_tile_entity", () -> TileEntityType.Builder.of(
                    MeatRackTileEntity::new, BlockRegistry.MEAT_RACK_BLOCK.get()).build(null));

    public static RegistryObject<TileEntityType<HoneyPondTileEntity>> HONEY_POND_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "honey_pond_tile_entity", () -> TileEntityType.Builder.of(
                    HoneyPondTileEntity::new, BlockRegistry.HONEY_POND_BLOCK.get()).build(null));

    public static RegistryObject<TileEntityType<XpStorageTileEntity>> XP_STORAGE_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "xp_storage_tile_entity", () -> TileEntityType.Builder.of(
                    XpStorageTileEntity::new, BlockRegistry.XP_STORAGE_BLOCK.get()).build(null));

    public static RegistryObject<TileEntityType<SpiderEggsTileEntity>> SPIDER_EGGS_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "spider_eggs_tile_entity", () -> TileEntityType.Builder.of(
                    SpiderEggsTileEntity::new, BlockRegistry.SPIDER_EGGS_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
