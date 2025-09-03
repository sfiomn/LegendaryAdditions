package sfiomn.legendary_additions.capabilities.death_position;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class DeathPositionProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag>
{
	public static Capability<DeathPositionCapability> DEATH_POSITION_CAPABILITY = CapabilityManager.get(new CapabilityToken<DeathPositionCapability>() { });
	private final LazyOptional<DeathPositionCapability> instance = LazyOptional.of(this::getInstance);
	private DeathPositionCapability deathPositionCapability = null;

	private DeathPositionCapability getInstance() {
		if (this.deathPositionCapability == null) {
			this.deathPositionCapability = new DeathPositionCapability();
		}
		return this.deathPositionCapability;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction side)
	{
		if (capability == DEATH_POSITION_CAPABILITY)
			return instance.cast();
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT()
	{
		return getInstance().writeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		getInstance().readNBT(tag);
	}
}
