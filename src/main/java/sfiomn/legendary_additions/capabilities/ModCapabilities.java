package sfiomn.legendary_additions.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.capabilities.death_position.DeathPositionCapability;
import sfiomn.legendary_additions.capabilities.death_position.DeathPositionProvider;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.network.packets.UpdateDeathPositionPacket;
import sfiomn.legendary_additions.util.CapabilityUtil;

@EventBusSubscriber(modid = LegendaryAdditions.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class ModCapabilities
{
	public static final ResourceLocation DEATH_POSITION = new ResourceLocation(LegendaryAdditions.MOD_ID, "death_position");
	
	@SubscribeEvent
	public static void attachCapabilityPlayer(AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof LivingEntity)
		{
			if (event.getObject() instanceof Player player)
			{
				event.addCapability(DEATH_POSITION, new DeathPositionProvider());
			}
		}
	}

	@SubscribeEvent
	public static void cloneHandler(PlayerEvent.Clone event)
	{
		Player orig = event.getOriginal();
		Player player = event.getEntity();

		if (event.isWasDeath())
		{
			if (Config.Baked.deathScrollEnabled)
			{
				if (orig.level().dimension() == Level.OVERWORLD) {
					DeathPositionCapability newCap = CapabilityUtil.getDeathPositionCapability(player);
					newCap.setDeathPosition(orig.blockPosition());

					sendDeathPositionUpdate(player);
				}
			}
		}
		else
		{
			if (Config.Baked.deathScrollEnabled)
			{
				orig.reviveCaps();
				DeathPositionCapability oldCap = CapabilityUtil.getDeathPositionCapability(orig);
				orig.invalidateCaps();

				DeathPositionCapability newCap = CapabilityUtil.getDeathPositionCapability(player);
				newCap.readNBT(oldCap.writeNBT());

				sendDeathPositionUpdate(player);
			}
		}
	}

	@SubscribeEvent
	public static void syncCapsOnDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		Player player = event.getEntity();
		if (Config.Baked.deathScrollEnabled)
			sendDeathPositionUpdate(player);
	}

	@SubscribeEvent
	public static void syncCapsOnLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		Player player = event.getEntity();
		if (Config.Baked.deathScrollEnabled)
			sendDeathPositionUpdate(player);
	}

	private static void sendDeathPositionUpdate(Player player)
	{
		if (!player.level().isClientSide)
		{
			UpdateDeathPositionPacket.sendTo(
					PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
					CapabilityUtil.getDeathPositionCapability(player).writeNBT());
		}
	}
}
