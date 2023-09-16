package sfiomn.legendary_additions.network.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Dimension;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import sfiomn.legendary_additions.blocks.ObeliskBlock;

import java.util.function.Supplier;

public class MessageObeliskDown
{
    // SERVER side message
    CompoundNBT compound;

    public MessageObeliskDown()
    {
    }

    public MessageObeliskDown(INBT nbt)
    {
        this.compound = (CompoundNBT) nbt;
    }

    public static MessageObeliskDown decode(PacketBuffer buffer)
    {
        return new MessageObeliskDown(buffer.readNbt());
    }

    public static void encode(MessageObeliskDown message, PacketBuffer buffer)
    {
        buffer.writeNbt(message.compound);
    }

    public static void handle(MessageObeliskDown message, Supplier<NetworkEvent.Context> supplier)
    {
        final NetworkEvent.Context context = supplier.get();

        if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER && context.getSender() != null) {

            context.enqueueWork(() -> setObeliskDown(context.getSender().getLevel(), message.compound));
        }
        supplier.get().setPacketHandled(true);
    }

    public static void setObeliskDown(ServerWorld world, CompoundNBT nbt) {
        int x = nbt.getInt("posX");
        int y = nbt.getInt("posY");
        int z = nbt.getInt("posZ");

        world.setBlockAndUpdate(new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)).setValue(ObeliskBlock.OBELISK_DOWN, true));
        world.setBlockAndUpdate(new BlockPos(x, y, z).above(), world.getBlockState(new BlockPos(x, y, z).above()).setValue(ObeliskBlock.OBELISK_DOWN, true));
    }
}
