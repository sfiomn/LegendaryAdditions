package sfiomn.legendary_additions.entities;

import com.google.common.cache.RemovalCause;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;

import java.util.List;

// Retrieved from https://github.com/MrCrayfish/MrCrayfishFurnitureMod/blob/1.16.X/src/main/java/com/mrcrayfish/furniture/entity/SeatEntity.java
public class SeatEntity extends Entity {
    private SeatEntity(World world, BlockPos pos, double yOffset, Direction direction)
    {
        this(EntityTypeRegistry.SEAT_ENTITY.get(), world);
        this.setPos(pos.getX() + 0.5, pos.getY() + yOffset, pos.getZ() + 0.5);
        this.setRot(direction.toYRot(), 0F);
    }
    public SeatEntity(EntityType<? extends SeatEntity> entityType, World world) {
        super(entityType, world);
        this.noPhysics = true;
    }

    @Override
    public void tick()
    {
        super.tick();
        if(!this.level.isClientSide)
        {
            if(this.getPassengers().isEmpty() || this.level.isEmptyBlock(this.blockPosition()))
            {
                this.remove();
                this.level.updateNeighbourForOutputSignal(blockPosition(), this.level.getBlockState(blockPosition()).getBlock());
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {

    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public double getPassengersRidingOffset()
    {
        return 0.0;
    }

    @Override
    protected boolean canRide(Entity entity)
    {
        return true;
    }

    @Override
    public IPacket<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static ActionResultType create(World world, BlockPos pos, double yOffset, PlayerEntity player, Direction direction)
    {
        if(!world.isClientSide())
        {
            List<SeatEntity> seats = world.getEntitiesOfClass(SeatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if(seats.isEmpty())
            {
                SeatEntity seat = new SeatEntity(world, pos, yOffset, direction);
                world.addFreshEntity(seat);
                player.startRiding(seat, false);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public Vector3d getDismountLocationForPassenger(LivingEntity entity)
    {
        Direction original = this.getDirection();
        Direction[] offsets = {original, original.getClockWise(), original.getCounterClockWise(), original.getOpposite()};
        for(Direction dir : offsets)
        {
            Vector3d safeVec = TransportationHelper.findSafeDismountLocation(entity.getType(), this.level, this.blockPosition().relative(dir), false);
            if(safeVec != null)
            {
                return safeVec.add(0, 0.25, 0);
            }
        }
        return super.getDismountLocationForPassenger(entity);
    }

    @Override
    protected void addPassenger(Entity entity)
    {
        super.addPassenger(entity);

        entity.yRot = this.yRot;
        entity.xRot = this.xRot;
    }

    @Override
    public void onPassengerTurned(Entity entity)
    {
        clampRotation(entity);
    }

    // Clamp rotation from Boat onPassengerTurned minecraft impl
    private void clampRotation(Entity passenger)
    {
        passenger.setYBodyRot(this.yRot);
        float f = MathHelper.wrapDegrees(passenger.yRot - this.yRot);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        passenger.yRotO += f1 - f;
        passenger.yRot += f1 - f;
        passenger.setYHeadRot(passenger.yRot);
    }
}
