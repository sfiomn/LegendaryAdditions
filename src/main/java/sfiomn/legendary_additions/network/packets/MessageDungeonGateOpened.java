package sfiomn.legendary_additions.network.packets;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import sfiomn.legendary_additions.tileentities.AbstractDungeonGateTileEntity;

import java.util.function.Supplier;

public class MessageDungeonGateOpened
{
    // SERVER side message
    CompoundNBT compound;

    public MessageDungeonGateOpened()
    {
    }

    public MessageDungeonGateOpened(INBT nbt)
    {
        this.compound = (CompoundNBT) nbt;
    }

    public static MessageDungeonGateOpened decode(PacketBuffer buffer)
    {
        return new MessageDungeonGateOpened(buffer.readNbt());
    }

    public static void encode(MessageDungeonGateOpened message, PacketBuffer buffer)
    {
        buffer.writeNbt(message.compound);
    }

    public static void handle(MessageDungeonGateOpened message, Supplier<NetworkEvent.Context> supplier)
    {
        final NetworkEvent.Context context = supplier.get();

        if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER && context.getSender() != null) {

            context.enqueueWork(() -> setDungeonGateOpened(context.getSender().getLevel(), message.compound));
        }
        supplier.get().setPacketHandled(true);
    }

    public static void setDungeonGateOpened(ServerWorld world, CompoundNBT nbt) {
        int x = nbt.getInt("posX");
        int y = nbt.getInt("posY");
        int z = nbt.getInt("posZ");

        TileEntity tileEntity = world.getBlockEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof AbstractDungeonGateTileEntity) {
            AbstractDungeonGateTileEntity dungeonGateTileEntity = (AbstractDungeonGateTileEntity) tileEntity;
            dungeonGateTileEntity.setOpened(!dungeonGateTileEntity.opened);
        }
    }
}
