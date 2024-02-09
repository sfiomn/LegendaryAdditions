package sfiomn.legendary_additions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

abstract class AbstractSpawnerBlock extends Block {
    public AbstractSpawnerBlock(Properties properties) {
        super(properties);
    }

    abstract public TileEntity createTileEntity(BlockState state, IBlockReader world);

    abstract boolean isBase(BlockState state);

    abstract BlockPos getBasePos(BlockState state, BlockPos pos);

    @Override
    public boolean hasTileEntity(BlockState state) {
        return isBase(state);
    }
}
