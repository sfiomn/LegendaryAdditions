package sfiomn.legendary_additions.network.packets;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.ObeliskBlock;
import sfiomn.legendary_additions.tileentities.AbstractDungeonHeartTileEntity;
import sfiomn.legendary_additions.tileentities.AbstractGateTileEntity;

import java.util.Objects;
import java.util.function.Supplier;

public class MessageDungeonHeartRange
{
    // SERVER side message
    CompoundNBT compound;

    public MessageDungeonHeartRange()
    {
    }

    public MessageDungeonHeartRange(INBT nbt)
    {
        this.compound = (CompoundNBT) nbt;
    }

    public static MessageDungeonHeartRange decode(PacketBuffer buffer)
    {
        return new MessageDungeonHeartRange(buffer.readNbt());
    }

    public static void encode(MessageDungeonHeartRange message, PacketBuffer buffer)
    {
        buffer.writeNbt(message.compound);
    }

    public static void handle(MessageDungeonHeartRange message, Supplier<NetworkEvent.Context> supplier)
    {
        final NetworkEvent.Context context = supplier.get();

        if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER && context.getSender() != null) {
            context.enqueueWork(() -> updateDungeonHeartRange(context.getSender().getLevel(), message.compound));
        }
        supplier.get().setPacketHandled(true);
    }

    public static void updateDungeonHeartRange(ServerWorld world, CompoundNBT nbt) {
        int x = nbt.getInt("posX");
        int y = nbt.getInt("posY");
        int z = nbt.getInt("posZ");

        TileEntity tileEntity = world.getBlockEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof AbstractDungeonHeartTileEntity) {
            String rangeDirection = nbt.getString("rangeDirection");
            int rangeValue = nbt.getInt("rangeValue");

            AbstractDungeonHeartTileEntity dungeonHeartTileEntity = (AbstractDungeonHeartTileEntity) tileEntity;
            dungeonHeartTileEntity.setRange(Objects.requireNonNull(Direction.byName(rangeDirection)), rangeValue);
        }
    }
}
