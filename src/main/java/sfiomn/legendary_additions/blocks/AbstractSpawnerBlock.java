package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendary_additions.blockentities.SpiderEggsBlockEntity;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;

abstract class AbstractSpawnerBlock extends BaseEntityBlock {
    public AbstractSpawnerBlock(Properties properties) {
        super(properties);
    }

    abstract public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState);

    abstract boolean isBase(BlockState state);

    abstract BlockPos getBasePos(BlockState state, BlockPos pos);

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState p_153213_, BlockEntityType<T> entityType) {
        return level.isClientSide ? null : createTickerHelper(entityType, BlockEntityRegistry.SPIDER_EGGS_BLOCK_ENTITY.get(), SpiderEggsBlockEntity::serverTick);
    }
}
