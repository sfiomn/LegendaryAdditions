package sfiomn.legendary_additions.registry;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.entities.HoneyPondTileEntity;
import sfiomn.legendary_additions.entities.MeatRackTileEntity;
import sfiomn.legendary_additions.entities.ObeliskTileEntity;

public class TileEntityRegistry {
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LegendaryAdditions.MOD_ID);

    public static RegistryObject<TileEntityType<ObeliskTileEntity>> OBELISK_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "obelisk_tile_entity", () -> TileEntityType.Builder.of(
                    ObeliskTileEntity::new, BlockRegistry.OBELISK_BLOCK.get()).build(null));

    public static RegistryObject<TileEntityType<MeatRackTileEntity>> MEAT_RACK_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "meat_rack_tile_entity", () -> TileEntityType.Builder.of(
                    MeatRackTileEntity::new, BlockRegistry.MEAT_RACK_BLOCK.get()).build(null));

    public static RegistryObject<TileEntityType<HoneyPondTileEntity>> HONEY_POND_TILE_ENTITY =
            TILE_ENTITIES.register(LegendaryAdditions.MOD_ID + "honey_pond_tile_entity", () -> TileEntityType.Builder.of(
                    HoneyPondTileEntity::new, BlockRegistry.HONEY_POND_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
