package sfiomn.legendary_additions.tileentities;

import sfiomn.legendary_additions.registry.TileEntityRegistry;

import java.util.Map;

public class SpiderEggsTileEntity extends AbstractSpawnerTileEntity {
    public SpiderEggsTileEntity() {
        super(TileEntityRegistry.FOREST_DUNGEON_HEART_TILE_ENTITY.get());
    }

    @Override
    int getHorizontalDetectionRangeInBlocks() {
        return 0;
    }

    @Override
    int getYDetectionRangeInBlocks() {
        return 0;
    }

    @Override
    Map<String, Integer> getMobWeightList() {
        return null;
    }

    @Override
    int getSpawnLimit() {
        return 0;
    }
}
