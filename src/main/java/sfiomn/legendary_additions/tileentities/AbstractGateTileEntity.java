package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.AbstractGateBlock;
import sfiomn.legendary_additions.entities.KeyEntity;
import sfiomn.legendary_additions.items.KeyItem;
import sfiomn.legendary_additions.network.NetworkHandler;
import sfiomn.legendary_additions.network.packets.MessageDungeonGateChange;
import sfiomn.legendary_additions.util.GatePartUtil;
import sfiomn.legendary_additions.util.IGatePart;
import sfiomn.legendary_additions.util.Lock;
import sfiomn.legendary_additions.util.MathUtil;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public abstract class AbstractGateTileEntity extends TileEntity implements IAnimatable, ITickableTileEntity, ISidedInventory {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    protected NonNullList<ItemStack> insertedKeys;
    private boolean opened;
    private boolean unlocked;
    private int animation;
    private static final int NO_ANIMATION = 0;
    private static final int OPENING = 1;
    private static final int CLOSING = 2;
    protected final GatePartUtil gatePartUtil;
    protected List<Lock> locks;
    protected static final AnimationBuilder OPENING_ANIM = new AnimationBuilder().addAnimation("opening", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final AnimationBuilder CLOSING_ANIM = new AnimationBuilder().addAnimation("closing", ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    public AbstractGateTileEntity(TileEntityType<?> tileEntityType, IGatePart[] gateParts) {
        super(tileEntityType);
        this.locks = new ArrayList<>();
        this.opened = false;
        this.unlocked = false;

        this.gatePartUtil = new GatePartUtil(gateParts);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "gateAnimController", 5, this::gateAnimController));
    }

    protected <E extends AbstractGateTileEntity> PlayState gateAnimController(final AnimationEvent<E> event) {
        if (this.animation == OPENING) {
            event.getController().setAnimation(OPENING_ANIM);
        } else if (this.animation == CLOSING) {
            event.getController().setAnimation(CLOSING_ANIM);
        }
        if (this.animation == CLOSING || this.animation == OPENING) {
            if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                this.animation = NO_ANIMATION;
                this.sendResetAnimation();
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        if (!this.unlocked && this.locks.size() == 0) {
            this.unlocked = true;
            this.setChanged();
        }

        for (Lock lock: this.locks) {
            lock.updateTimer();

            // Unlock process + drop keys if failed
            if (lock.getInsertTimerTick() == 0 && !this.insertedKeys.get(lock.id).isEmpty()) {
                if (lock.canBeUnlocked(this.insertedKeys.get(lock.id).getItem())) {
                    unlock(lock);
                } else {
                    if (this.level != null) {
                        Vector3d dropPos = Vector3d.atCenterOf(this.worldPosition);
                        if (lock.getInsertedKeyPosition() != Vector3i.ZERO)
                            dropPos = Vector3d.atCenterOf(lock.getInsertedKeyPosition());
                        Entity keyItemEntity = new ItemEntity(this.level, dropPos.x, dropPos.y, dropPos.z, this.insertedKeys.get(lock.id));
                        this.insertedKeys.set(lock.id, ItemStack.EMPTY);
                        this.level.addFreshEntity(keyItemEntity);
                    }
                }
                lock.keyStopInserting();
            }
        }
    }

    abstract BlockState getOpenPartBlockState(IGatePart part, BlockPos partPos, Direction gateFacing);

    public void resetAnimation() {
        this.animation = NO_ANIMATION;
    }

    public boolean openGate() {
        if (this.level != null && this.animation == NO_ANIMATION) {
            if (canOpen()) {
                this.animation = OPENING;
                this.opened = true;
                this.setChanged();

                Direction gateFacing = getGateFacing();
                // Update closed gate parts (gateway + hinges) as opened
                for (IGatePart part : this.gatePartUtil.getCloseParts()) {
                    BlockState partState = this.level.getBlockState(this.worldPosition.offset(part.offset(gateFacing)));
                    this.level.setBlockAndUpdate(this.worldPosition.offset(part.offset(gateFacing)), partState.setValue(AbstractGateBlock.OPENED, true));
                }
                // Create new blocks in the opened gate part locations
                for (IGatePart part : this.gatePartUtil.getOpenParts()) {
                    BlockPos partPos = this.worldPosition.offset(part.offset(gateFacing));
                    this.level.setBlockAndUpdate(partPos, this.getOpenPartBlockState(part, partPos, gateFacing));
                }
                return true;
            } else {
                this.playFailedToOpenSound();
            }
        }
        return false;
    }

    public void closeGate() {
        if (this.level != null && this.animation == NO_ANIMATION) {
            this.animation = CLOSING;
            this.opened = false;
            this.setChanged();

            Direction gateFacing = getGateFacing();
            for (IGatePart part : this.gatePartUtil.getCloseParts()) {
                BlockState partState = this.level.getBlockState(this.worldPosition.offset(part.offset(gateFacing)));
                this.level.setBlockAndUpdate(this.worldPosition.offset(part.offset(gateFacing)), partState.setValue(AbstractGateBlock.OPENED, false));
            }
            // Remove the open gate part blocks
            for (IGatePart part : this.gatePartUtil.getOpenParts()) {
                BlockPos partPos = this.worldPosition.offset(part.offset(gateFacing));
                BlockState partState = this.level.getBlockState(partPos);
                this.level.setBlockAndUpdate(partPos, partState.getValue(AbstractGateBlock.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState());
            }
        }
    }

    public boolean isOpened() {
        return this.opened;
    }

    public boolean canOpen() {
        if (this.level == null)
            return false;
        Direction gateFacing = getGateFacing();

        for (IGatePart part: this.gatePartUtil.getOpenParts()) {
            BlockPos partPos = this.worldPosition.offset(part.offset(gateFacing));
            BlockState partState = this.level.getBlockState(partPos);
            if (!partState.getMaterial().isReplaceable()) {
                return false;
            }
        }
        return true;
    }

    public Direction getGateFacing() {
        return getBlockState().getValue(AbstractGateBlock.FACING);
    }

    public void sendResetAnimation() {
        CompoundNBT posNbt = new CompoundNBT();
        posNbt.putInt("posX", this.worldPosition.getX());
        posNbt.putInt("posY", this.worldPosition.getY());
        posNbt.putInt("posZ", this.worldPosition.getZ());
        MessageDungeonGateChange messageDungeonGateChange = new MessageDungeonGateChange(posNbt);
        NetworkHandler.INSTANCE.sendToServer(messageDungeonGateChange);
    }

    public boolean checkUnlocked() {
        for (Lock lock: this.locks) {
            if (!lock.isUnlocked()) {
                return false;
            }
        }
        return true;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public void dropKeys() {
        if (this.level != null) {
            InventoryHelper.dropContents(this.level, this.worldPosition, this.insertedKeys);
        }
    }

    public boolean insertKey(PlayerEntity player, Vector3d insertLocation, Direction insertDirection) {
        Item insertKey = player.getMainHandItem().getItem();

        if (this.level == null || this.level.isClientSide)
            return false;

        Direction gateFacing = getGateFacing();
        for (Lock lock : this.locks) {
            if (lock.canInsert(insertLocation, gateFacing, this.worldPosition) && this.insertedKeys.get(lock.id).isEmpty()) {
                Vector3d lockPos = lock.getLockPos(gateFacing, this.worldPosition);
                Vector3d insertKeyPos = gateFacing.getAxis() == Direction.Axis.Z ?
                        new Vector3d(lockPos.x, lockPos.y, insertLocation.z) :
                        new Vector3d(insertLocation.x, lockPos.y, lockPos.z);

                boolean keyInserted = false;
                if (insertKey instanceof KeyItem) {
                    insertKeyItem(lock, (KeyItem) insertKey, insertKeyPos, insertDirection);

                    player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                    this.insertedKeys.set(lock.id, new ItemStack(insertKey));
                    keyInserted = true;
                } else {
                    if (lock.canBeUnlocked(insertKey)) {
                        unlock(lock);
                        this.playUnlockSound(lockPos);

                        player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                        this.insertedKeys.set(lock.id, new ItemStack(insertKey));
                        keyInserted = true;
                    }
                }
                this.setChanged();
                return keyInserted;
            }
        }
        return false;
    }

    private void insertKeyItem(Lock lock, KeyItem insertKey, Vector3d insertKeyPos, Direction insertDirection) {
        assert this.level != null;
        KeyEntity newKeyEntity = insertKey.getKeyEntity().create(this.level);
        if (newKeyEntity == null)
            return;

        newKeyEntity.setPos(insertKeyPos.x, insertKeyPos.y, insertKeyPos.z);
        newKeyEntity.yRot = MathUtil.getAngleFromWest(insertDirection);

        if (lock.canBeUnlocked(insertKey)) {
            lock.setInsertedKeyPosition(Vector3i.ZERO);
            lock.setInsertTimerTick(newKeyEntity.getSuccessfulInsertAnimationTicks());
            newKeyEntity.successfulInsertInLock(this.worldPosition);
        } else {
            lock.setInsertedKeyPosition(new Vector3i(insertKeyPos.x, insertKeyPos.y, insertKeyPos.z).relative(insertDirection.getOpposite(), 1));
            lock.setInsertTimerTick(newKeyEntity.getFailedInsertAnimationTicks());
            newKeyEntity.failedInsertInLock(this.worldPosition);
        }

        this.level.addFreshEntity(newKeyEntity);
    }

    protected void unlock(Lock lock) {
        lock.unlocked();
        this.unlocked = this.checkUnlocked();
        this.setChanged();
        if (this.unlocked) {
            if (this.level != null && !this.openGate()) {
                this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    public abstract void playFailedToOpenSound();
    public abstract void playUnlockSound(Vector3d lockPos);

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.worldPosition.offset(-this.gatePartUtil.getWidth() - 1, 0, -this.gatePartUtil.getWidth() - 1), this.worldPosition.offset(this.gatePartUtil.getWidth() + 1, this.gatePartUtil.getHeight() + 1, this.gatePartUtil.getWidth() + 1));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return slot < getContainerSize();
    }

    @Override
    public int getContainerSize() {
        return this.insertedKeys.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.insertedKeys) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.insertedKeys.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStackHelper.removeItem(this.insertedKeys, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStackHelper.takeItem(this.insertedKeys, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.insertedKeys.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return this.level != null
                && this.level.getBlockEntity(this.worldPosition) == this
                && player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64;
    }

    @Override
    public void clearContent() {
        this.insertedKeys.clear();
    }

    private void syncData() {
        this.setChanged();
        if (this.level != null)
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.insertedKeys = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.insertedKeys);
        this.opened = nbt.getBoolean("opened");
        this.unlocked = nbt.getBoolean("unlocked");
        this.animation = nbt.getInt("animation");
        for (Lock lock: this.locks) {
            int[] lockInfo = nbt.getIntArray("lock" + lock.id);
            if (lockInfo[0] == 1)
                lock.unlocked();
            lock.setInsertTimerTick(lockInfo[1]);
            Vector3i insertPos = new Vector3i(lockInfo[2], lockInfo[3], lockInfo[4]);
            lock.setInsertedKeyPosition(insertPos);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, this.insertedKeys);
        nbt.putBoolean("opened", this.opened);
        nbt.putBoolean("unlocked", this.unlocked);
        nbt.putInt("animation", this.animation);
        for (Lock lock: this.locks) {
            List<Integer> lockInfo = new ArrayList<>();
            if (lock.isUnlocked())
                lockInfo.add(1);
            else
                lockInfo.add(0);
            lockInfo.add(lock.getInsertTimerTick());
            lockInfo.add(lock.getInsertedKeyPosition().getX());
            lockInfo.add(lock.getInsertedKeyPosition().getY());
            lockInfo.add(lock.getInsertedKeyPosition().getZ());
            nbt.putIntArray("lock" + lock.id, lockInfo);
        }
        return nbt;
    }

    @Nullable
    @Override
    // Send update to the client
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = this.getUpdateTag();
        ItemStackHelper.saveAllItems(nbt, this.insertedKeys);
        return new SUpdateTileEntityPacket(this.worldPosition, 1, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.putBoolean("opened", this.opened);
        nbt.putBoolean("unlocked", this.unlocked);
        nbt.putInt("animation", this.animation);
        for (Lock lock: this.locks) {
            List<Integer> lockInfo = new ArrayList<>();
            if (lock.isUnlocked())
                lockInfo.add(1);
            else
                lockInfo.add(0);
            lockInfo.add(lock.getInsertTimerTick());
            lockInfo.add(lock.getInsertedKeyPosition().getX());
            lockInfo.add(lock.getInsertedKeyPosition().getY());
            lockInfo.add(lock.getInsertedKeyPosition().getZ());
            nbt.putIntArray("lock" + lock.id, lockInfo);
        }
        return nbt;
    }

    @Override
    // Get update from the server
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.opened = nbt.getBoolean("opened");
        this.unlocked = nbt.getBoolean("unlocked");
        this.animation = nbt.getInt("animation");
        this.insertedKeys = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.insertedKeys);
        for (Lock lock: this.locks) {
            int[] lockInfo = nbt.getIntArray("lock" + lock.id);
            if (lockInfo[0] == 1)
                lock.unlocked();
            lock.setInsertTimerTick(lockInfo[1]);
            Vector3i insertPos = new Vector3i(lockInfo[2], lockInfo[3], lockInfo[4]);
            lock.setInsertedKeyPosition(insertPos);
        }
    }
}
