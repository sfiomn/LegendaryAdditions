package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import sfiomn.legendary_additions.blocks.DungeonGateBlock;
import sfiomn.legendary_additions.blocks.ForestDungeonGateBlock;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;
import sfiomn.legendary_additions.registry.TileEntityRegistry;
import sfiomn.legendary_additions.util.IDungeonGatePart;
import sfiomn.legendary_additions.util.Lock;

public class ForestDungeonGateTileEntity extends AbstractDungeonGateTileEntity {
    public ForestDungeonGateTileEntity() {
        super(TileEntityRegistry.FOREST_DUNGEON_GATE_TILE_ENTITY.get(), ForestDungeonGateBlock.ForestDungeonGatePart.values());
        locks.add(new Lock(new Vector3i(0, 1, 0), new Vector3d(0.5, 0.5, 0), 0.5, ItemRegistry.FOREST_KEY.get()));
    }

    public BlockState getOpenPartBlockState(IDungeonGatePart part, BlockPos partPos, Direction gateFacing) {
        if (part instanceof ForestDungeonGateBlock.ForestDungeonGatePart) {
            FluidState fluidState = this.level.getFluidState(partPos);
            return BlockRegistry.FOREST_DUNGEON_GATE_BLOCK.get().defaultBlockState()
                            .setValue(DungeonGateBlock.OPENED, true)
                            .setValue(DungeonGateBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER)
                            .setValue(DungeonGateBlock.FACING, this.dungeonGatePartUtil.getOpenDirection(part, gateFacing))
                            .setValue(ForestDungeonGateBlock.PART, (ForestDungeonGateBlock.ForestDungeonGatePart) part);
        }
        return null;
    }
}
