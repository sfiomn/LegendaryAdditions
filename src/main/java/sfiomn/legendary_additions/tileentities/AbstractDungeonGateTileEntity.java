package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import sfiomn.legendary_additions.blocks.DungeonGateBlock;
import sfiomn.legendary_additions.blocks.ForestDungeonGateBlock;
import sfiomn.legendary_additions.entities.KeyEntity;
import sfiomn.legendary_additions.items.KeyItem;
import sfiomn.legendary_additions.util.DungeonGatePartUtil;
import sfiomn.legendary_additions.util.IDungeonGatePart;
import sfiomn.legendary_additions.util.Lock;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDungeonGateTileEntity extends TileEntity implements IAnimatable, ITickableTileEntity {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public boolean opened;
    private boolean openingDone;
    public final DungeonGatePartUtil dungeonGatePartUtil;
    protected List<Lock> locks;

    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final AnimationBuilder OPENING = new AnimationBuilder().addAnimation("opening", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final AnimationBuilder CLOSING = new AnimationBuilder().addAnimation("closing", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final AnimationBuilder OPENED = new AnimationBuilder().addAnimation("opened", ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    public AbstractDungeonGateTileEntity(TileEntityType<?> tileEntityType, IDungeonGatePart[] dungeonGateParts) {
        super(tileEntityType);
        this.locks = new ArrayList<>();
        this.opened = false;
        this.openingDone = false;

        this.dungeonGatePartUtil = new DungeonGatePartUtil(dungeonGateParts);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "gateAnimController", 0, this::gateAnimController));
    }

    protected <E extends AbstractDungeonGateTileEntity> PlayState gateAnimController(final AnimationEvent<E> event) {
        if (this.opened) {
            if (!openingDone) {
                event.getController().setAnimation(OPENING);
                if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                    openingDone = true;
                }
            } else {
                event.getController().setAnimation(OPENED);
                if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                    return PlayState.STOP;
                }
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
    }

    abstract BlockState getOpenPartBlockState(IDungeonGatePart part, BlockPos partPos, Direction gateFacing);

    public void openGate() {
        this.opened = true;
        if (this.level != null) {
            Direction facing = getBlockState().getValue(DungeonGateBlock.FACING);
            // Update closed gate parts as opened + change facing direction for hinges
            for (IDungeonGatePart part : this.dungeonGatePartUtil.getCloseParts()) {
                BlockState partState = this.level.getBlockState(this.worldPosition.offset(part.offset(facing)));
                this.level.setBlockAndUpdate(this.worldPosition.offset(part.offset(facing)), partState.setValue(DungeonGateBlock.OPENED, true));
            }
            // Create new blocks in the opened gate part locations
            for (IDungeonGatePart part : this.dungeonGatePartUtil.getOpenParts()) {
                BlockPos partPos = this.worldPosition.offset(part.offset(facing));
                this.level.setBlockAndUpdate(partPos, this.getOpenPartBlockState(part, partPos, facing));
            }
            this.setChanged();
        }
    }

    public void closeGate() {
        this.opened = false;
        if (this.level != null) {
            Direction facing = getBlockState().getValue(DungeonGateBlock.FACING);
            for (IDungeonGatePart part : this.dungeonGatePartUtil.getCloseParts()) {
                BlockState partState = this.level.getBlockState(this.worldPosition.offset(part.offset(facing)));
                this.level.setBlockAndUpdate(this.worldPosition.offset(part.offset(facing)), partState.setValue(DungeonGateBlock.OPENED, false));
            }
            // Remove the open gate part blocks
            for (IDungeonGatePart part : this.dungeonGatePartUtil.getOpenParts()) {
                BlockState partState = this.level.getBlockState(this.worldPosition.offset(part.offset(facing)));
                this.level.setBlockAndUpdate(this.worldPosition.offset(part.offset(facing)), partState.getValue(DungeonGateBlock.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState());
            }
            this.setChanged();
        }
    }

    public boolean isOpened() {
        return this.opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
        this.setChanged();
    }

    public void unlock(Lock lockToUnlock) {
        for (Lock lock: this.locks) {
            if (lock == lockToUnlock) {
                lock.unlocked();
                return;
            }
        }
    }

    public boolean isUnlocked() {
        boolean unlocked = true;
        for (Lock lock: this.locks) {
            if (!lock.isUnlocked()) {
                unlocked = false;
                break;
            }
        }
        return unlocked;
    }

    public void dropKeys() {
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            Direction direction = state.getValue(ForestDungeonGateBlock.FACING);
            for (Lock lock: this.locks) {
                if (lock.isUnlocked()) {
                    Vector3d pos = lock.getPositionInBlock();
                    pos.add(direction.getNormal().getX() * 0.3, direction.getNormal().getY() * 0.3, direction.getNormal().getZ() * 0.3);
                    ItemEntity key = new ItemEntity(this.level, pos.x, pos.y, pos.z, new ItemStack(lock.getKey()));
                    this.level.addFreshEntity(key);
                }
            }
        }
    }

    public void insertKey(PlayerEntity player, Vector3d insertLocation) {
        Item insertKey = player.getMainHandItem().getItem();

        if (this.level == null || !(insertKey instanceof KeyItem))
            return;

        Entity newEntity = ((KeyItem) insertKey).getKeyEntity(player.level);
        if (!(newEntity instanceof KeyEntity))
            return;

        BlockState state = this.level.getBlockState(this.worldPosition);
        Direction direction = state.getValue(ForestDungeonGateBlock.FACING);

        for (Lock lock : this.locks) {
            if (lock.tryInsert(insertLocation, direction, this.worldPosition)) {
                /*
                KeyEntity newKeyEntity = (KeyEntity) newEntity;
                Vector3d spawnKeyPos = lock.getLockPosCenterBlock(direction, this.worldPosition);
                spawnKeyPos.add(direction.getNormal().getX() * 0.3, 0, direction.getNormal().getZ() * 0.3);
                newKeyEntity.moveTo(spawnKeyPos.x, spawnKeyPos.y, spawnKeyPos.z);
                newKeyEntity.insertInLock(lock, this);

                player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                this.level.addFreshEntity(newKeyEntity);*/
                this.unlock(lock);
                return;
            }
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.opened = nbt.getBoolean("opened");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putBoolean("opened", this.opened);
        return nbt;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = this.getUpdateTag();
        return new SUpdateTileEntityPacket(this.worldPosition, 1, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.putBoolean("opened", this.opened);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.opened = nbt.getBoolean("opened");
    }
}
