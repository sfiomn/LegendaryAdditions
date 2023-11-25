package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.AbstractGateBlock;
import sfiomn.legendary_additions.blocks.ForestDungeonGateBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.SoundRegistry;
import sfiomn.legendary_additions.registry.TileEntityRegistry;
import sfiomn.legendary_additions.util.IGatePart;
import sfiomn.legendary_additions.util.Lock;

public class ForestDungeonGateTileEntity extends AbstractGateTileEntity {
    public ForestDungeonGateTileEntity() {
        super(TileEntityRegistry.FOREST_DUNGEON_GATE_TILE_ENTITY.get(), ForestDungeonGateBlock.ForestDungeonGatePart.values());

        int SLOT_INDEX = 0;
        locks.add(new Lock(SLOT_INDEX++, new Vector3i(0, 1, 0), new Vector3d(0.5, 0.5, 0.5), 0.2, Config.Baked.forestDungeonGateLock1Unlocks));

        this.insertedKeys = NonNullList.withSize(SLOT_INDEX, ItemStack.EMPTY);
    }

    public BlockState getOpenPartBlockState(IGatePart part, BlockPos partPos, Direction gateFacing) {
        if (part instanceof ForestDungeonGateBlock.ForestDungeonGatePart && this.level != null) {
            FluidState fluidState = this.level.getFluidState(partPos);
            return BlockRegistry.FOREST_DUNGEON_GATE_BLOCK.get().defaultBlockState()
                            .setValue(AbstractGateBlock.OPENED, true)
                            .setValue(AbstractGateBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER)
                            .setValue(AbstractGateBlock.FACING, gateFacing)
                            .setValue(ForestDungeonGateBlock.PART, (ForestDungeonGateBlock.ForestDungeonGatePart) part);
        }
        return null;
    }

    @Override
    public void playFailedToOpenSound() {
        if (this.level != null)
            this.level.playSound(null, this.worldPosition, SoundRegistry.OPEN_GATE_FAILED.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }

    @Override
    public void playUnlockSound(Vector3d lockPos) {
        if (this.level != null)
            this.level.playSound(null, lockPos.x, lockPos.y, lockPos.z, SoundRegistry.LOCK_UNLOCKED.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }
}
