package sfiomn.legendary_additions.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.network.packets.MessageObeliskDown;
import sfiomn.legendary_additions.network.packets.ResetTradeMessage;
import sfiomn.legendary_additions.network.packets.UpdateDeathPositionPacket;

public class NetworkHandler
{
	private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(LegendaryAdditions.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);
	
	public static void register()
	{
		int id = -1;

		INSTANCE.registerMessage(id++, MessageObeliskDown.class, MessageObeliskDown::encode, MessageObeliskDown::decode, MessageObeliskDown::handle);
		INSTANCE.registerMessage(id++, ResetTradeMessage.class, ResetTradeMessage::encode, ResetTradeMessage::decode, ResetTradeMessage::handle);
		INSTANCE.registerMessage(id++, UpdateDeathPositionPacket.class, UpdateDeathPositionPacket::encode, UpdateDeathPositionPacket::decode, UpdateDeathPositionPacket::handle);
	}
}