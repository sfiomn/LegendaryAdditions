package sfiomn.legendary_additions.capabilities.death_position;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import sfiomn.legendary_additions.api.death_position.IDeathPositionCapability;

import java.util.Arrays;

public class DeathPositionCapability implements IDeathPositionCapability
{
	private BlockPos deathPosition;
	private boolean positionUsed;

	public DeathPositionCapability()
	{
		this.init();
	}
	
	public void init()
	{
		this.deathPosition = BlockPos.ZERO;
		this.positionUsed = false;
	}

	@Override
	public BlockPos getDeathPosition() {
		return deathPosition;
	}

	@Override
	public void setDeathPosition(BlockPos pos) {
		this.deathPosition = pos;
	}

	@Override
	public boolean hasDeathPosition() {
		return this.deathPosition != BlockPos.ZERO;
	}

	@Override
	public void setPositionUsed(boolean positionUsed) {
		this.positionUsed = positionUsed;
	}

	@Override
	public boolean isPositionUsed() {
		return this.positionUsed;
	}
	
	public CompoundTag writeNBT()
	{
		CompoundTag compound = new CompoundTag();
		
		compound.putIntArray("deathPosition",
				Arrays.asList(this.deathPosition.getX(), this.deathPosition.getY(), this.deathPosition.getZ()));

		compound.putBoolean("positionUsed", this.positionUsed);

		return compound;
	}
	
	public void readNBT(CompoundTag compound)
	{
		this.init();

		if (compound.contains("deathPosition")) {
			int[] pos = compound.getIntArray("deathPosition");
			this.setDeathPosition(new BlockPos(pos[0], pos[1], pos[2]));
		}

		if (compound.contains("positionUsed")) {
			this.setPositionUsed(compound.getBoolean("positionUsed"));
		}
	}
}
