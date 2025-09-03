package sfiomn.legendary_additions.api.death_position;

import net.minecraft.core.BlockPos;

public interface IDeathPositionCapability {

	BlockPos getDeathPosition();

	void setDeathPosition(BlockPos pos);

	boolean hasDeathPosition();

	void setPositionUsed(boolean positionUsed);

	boolean isPositionUsed();
}
