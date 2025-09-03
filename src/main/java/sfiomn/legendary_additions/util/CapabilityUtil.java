package sfiomn.legendary_additions.util;

import net.minecraft.world.entity.player.Player;
import sfiomn.legendary_additions.capabilities.death_position.DeathPositionCapability;
import sfiomn.legendary_additions.capabilities.death_position.DeathPositionProvider;

public final class CapabilityUtil
{
	private CapabilityUtil() {}
	
	/**
	 * Gets the death position capability of the given player.
	 * @param player Player
	 * @return The Death Position capability of the given player if it exists, or a new dummy capability if it doesn't.
	 */
	public static DeathPositionCapability getDeathPositionCapability(Player player)
	{
		return player.getCapability(DeathPositionProvider.DEATH_POSITION_CAPABILITY).orElse(new DeathPositionCapability());
	}
}
