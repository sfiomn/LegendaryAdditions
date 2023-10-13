package sfiomn.legendary_additions.util;

import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class Lock {
    public final int id;
    private final Vector3d positionInBlock;
    private final Vector3i offsetOnBase;
    private final double size;
    private boolean unlocked;
    private Vector3i insertedKeyPosition;
    private int insertTimerTick;
    private final List<String> keyNames;

    public Lock(int lockId, Vector3i offsetOnBase, Vector3d positionInBlock, double size, List<String> keyNames) {
        this.id = lockId;
        this.offsetOnBase = offsetOnBase;
        this.positionInBlock = positionInBlock;
        this.size = size;
        this.keyNames = keyNames;
        this.unlocked = false;
        this.insertedKeyPosition = Vector3i.ZERO;
        this.insertTimerTick = -1;
    }

    public Vector3d getPositionInBlock() {
        return this.positionInBlock;
    }
    public Vector3i getOffsetOnBase() {
        return this.offsetOnBase;
    }
    public Vector3d getLockPos(Direction facing, BlockPos basePos) {
        Vector3d positionInBlockFacing = MathUtil.rotateYFromSouth(this.positionInBlock, facing);

        return Vector3d.atLowerCornerOf(MathUtil.rotateYFromSouth(this.offsetOnBase, facing)).add(positionInBlockFacing).add(basePos.getX(), basePos.getY(), basePos.getZ());
    }
    public double getSize() {
        return this.size;
    }

    public void unlocked() {
        this.unlocked = true;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public boolean keyInserting() {
        return this.insertTimerTick > -1;
    }
    public void keyStopInserting() {
        this.insertTimerTick = -1;
        this.insertedKeyPosition = Vector3i.ZERO;
    }

    public void updateTimer() {
        if (this.insertTimerTick >= 0)
            this.insertTimerTick -= 1;
    }

    public void setInsertTimerTick(int insertTimerTick) {
        this.insertTimerTick = insertTimerTick;
    }

    public int getInsertTimerTick() {
        return insertTimerTick;
    }

    public boolean canInsert(Vector3d insertLocation, Direction facing, BlockPos basePos) {
        Vector3d centerInsertLocation = facing.getAxis() == Direction.Axis.Z ?
                        new Vector3d(insertLocation.x, insertLocation.y, MathHelper.floor(insertLocation.z) + 0.5) :
                        new Vector3d(MathHelper.floor(insertLocation.x) + 0.5, insertLocation.y, insertLocation.z);
        return this.getLockPos(facing, basePos).subtract(centerInsertLocation).length() <= this.getSize() && !this.keyInserting() && !this.isUnlocked();
    }

    public boolean canBeUnlocked(Item item) {
        for (String keyName: getKeyNames()) {
            Item keyItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(keyName));
            if (keyItem != null) {
                if (keyItem == item) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getKeyNames() {
        return keyNames;
    }

    public Vector3i getInsertedKeyPosition() {
        return insertedKeyPosition;
    }

    public void setInsertedKeyPosition(Vector3i insertedKeyPosition) {
        this.insertedKeyPosition = insertedKeyPosition;
    }
}
