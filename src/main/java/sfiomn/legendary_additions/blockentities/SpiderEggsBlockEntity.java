package sfiomn.legendary_additions.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;

import java.util.Map;

public class SpiderEggsBlockEntity extends AbstractSpawnerBlockEntity {
    public SpiderEggsBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.OBELISK_BLOCK_ENTITY.get(), blockPos, blockState);
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
