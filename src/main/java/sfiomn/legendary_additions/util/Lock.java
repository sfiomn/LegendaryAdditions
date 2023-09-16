package sfiomn.legendary_additions.util;

import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import sfiomn.legendary_additions.LegendaryAdditions;

public class Lock {
    private final Vector3d positionInBlock;
    private final Vector3i offsetOnBase;
    private final double size;
    private boolean unlocked;
    private boolean inserting;
    private final Item key;

    public Lock(Vector3i offsetOnBase, Vector3d positionInBlock, double size, Item key) {
        this.offsetOnBase = offsetOnBase;
        this.positionInBlock = positionInBlock;
        this.size = size;
        this.key = key;
        this.unlocked = false;
        this.inserting = false;
    }

    public Vector3d getPositionInBlock() {
        return this.positionInBlock;
    }
    public Vector3i getOffsetOnBase() {
        return this.offsetOnBase;
    }
    public Vector3d getLockPosCenterBlock(Direction facing, BlockPos basePos) {
        Vector3d lockPosition = new Vector3d(
                this.getOffsetOnBase().getX(),this.getOffsetOnBase().getY(),this.getOffsetOnBase().getZ() + 0.5)
                .add(this.positionInBlock);
        LegendaryAdditions.LOGGER.debug("offset : " + this.getOffsetOnBase());
        LegendaryAdditions.LOGGER.debug("position in block : " + this.positionInBlock);
        LegendaryAdditions.LOGGER.debug("lock position on base : " + lockPosition);
        LegendaryAdditions.LOGGER.debug("lock position after rotate on base : " + MathUtil.rotateYFromSouth(lockPosition, facing));
        LegendaryAdditions.LOGGER.debug("lock position after rotate in world : " + (MathUtil.rotateYFromSouth(lockPosition, facing).add(basePos.getX(), basePos.getY(), basePos.getZ())));

        return MathUtil.rotateYFromSouth(lockPosition, facing).add(basePos.getX(), basePos.getY(), basePos.getZ());
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

    public boolean isInserting() {
        return this.inserting;
    }

    public boolean tryInsert(Vector3d insertLocation, Direction facing, BlockPos basePos) {
        LegendaryAdditions.LOGGER.debug("insert position : " + insertLocation + ", lock position : " + this.getLockPosCenterBlock(facing, basePos));
        return this.getLockPosCenterBlock(facing, basePos).subtract(insertLocation).length() <= this.getSize() && !this.isInserting() && !this.isUnlocked();
    }

    public boolean canBeUnlocked(Item keyItem) {
        return keyItem == getKey();
    }

    public Item getKey() {
        return key;
    }
}
