package sfiomn.legendary_additions.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import sfiomn.legendary_additions.blocks.ObeliskBlock;

import java.util.function.Supplier;

public class MessageObeliskDown
{
    // SERVER side message
    CompoundTag compound;

    public MessageObeliskDown()
    {
    }

    public MessageObeliskDown(Tag nbt)
    {
        this.compound = (CompoundTag) nbt;
    }

    public static MessageObeliskDown decode(FriendlyByteBuf buffer)
    {
        return new MessageObeliskDown(buffer.readNbt());
    }

    public static void encode(MessageObeliskDown message, FriendlyByteBuf buffer)
    {
        buffer.writeNbt(message.compound);
    }

    public static void handle(MessageObeliskDown message, Supplier<NetworkEvent.Context> supplier)
    {
        final NetworkEvent.Context context = supplier.get();

        if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER && context.getSender() != null) {

            context.enqueueWork(() -> setObeliskDown(context.getSender().serverLevel(), message.compound));
        }
        supplier.get().setPacketHandled(true);
    }

    public static void setObeliskDown(ServerLevel level, CompoundTag nbt) {
        int x = nbt.getInt("posX");
        int y = nbt.getInt("posY");
        int z = nbt.getInt("posZ");

        level.setBlockAndUpdate(new BlockPos(x, y, z), level.getBlockState(new BlockPos(x, y, z)).setValue(ObeliskBlock.OBELISK_DOWN, true));
        level.setBlockAndUpdate(new BlockPos(x, y, z).above(), level.getBlockState(new BlockPos(x, y, z).above()).setValue(ObeliskBlock.OBELISK_DOWN, true));
    }
}
