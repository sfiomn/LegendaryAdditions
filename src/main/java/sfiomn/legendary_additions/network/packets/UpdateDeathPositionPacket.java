package sfiomn.legendary_additions.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import sfiomn.legendary_additions.capabilities.death_position.DeathPositionCapability;
import sfiomn.legendary_additions.network.NetworkHandler;
import sfiomn.legendary_additions.util.CapabilityUtil;

import java.util.function.Supplier;

public class UpdateDeathPositionPacket
{
	private CompoundTag compound;

	public UpdateDeathPositionPacket(Tag compound)
	{
		this.compound = (CompoundTag) compound;
	}

	public UpdateDeathPositionPacket() {}

	public static void encode(UpdateDeathPositionPacket message, FriendlyByteBuf buffer)
	{
		buffer.writeNbt(message.compound);
	}
	
	public static UpdateDeathPositionPacket decode(FriendlyByteBuf buffer)
	{
		return new UpdateDeathPositionPacket(buffer.readNbt());
	}
	
	public static void handle(UpdateDeathPositionPacket message, Supplier<NetworkEvent.Context> supplier)
	{
		final NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> syncDeathPosition(message.compound)));
		
		supplier.get().setPacketHandled(true);
	}
	
	public static DistExecutor.SafeRunnable syncDeathPosition(CompoundTag compound)
	{
		return new DistExecutor.SafeRunnable()
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void run()
			{
				LocalPlayer player = Minecraft.getInstance().player;

				if (player != null) {
					DeathPositionCapability deathPositionCapability = CapabilityUtil.getDeathPositionCapability(player);

					deathPositionCapability.readNBT(compound);
				}
			}
		};
	}

	public static void sendTo(PacketDistributor.PacketTarget packetDistributor, Tag compound) {
		NetworkHandler.INSTANCE.send(packetDistributor, new UpdateDeathPositionPacket(compound));
	}
}
