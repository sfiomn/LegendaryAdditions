package sfiomn.legendary_additions.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.network.packets.MessageDungeonGateChange;
import sfiomn.legendary_additions.network.packets.MessageDungeonHeartRange;
import sfiomn.legendary_additions.network.packets.MessageObeliskDown;

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
		INSTANCE.registerMessage(id++, MessageDungeonGateChange.class, MessageDungeonGateChange::encode, MessageDungeonGateChange::decode, MessageDungeonGateChange::handle);
		INSTANCE.registerMessage(id++, MessageDungeonHeartRange.class, MessageDungeonHeartRange::encode, MessageDungeonHeartRange::decode, MessageDungeonHeartRange::handle);
	}
}