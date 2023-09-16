package sfiomn.legendary_additions.util;

import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.vector.Vector3i;
import sfiomn.legendary_additions.blocks.ForestDungeonGateBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface
IDungeonGatePart extends IStringSerializable {

    String getSerializedName();
    Vector3i offset();
    boolean isBase();
    DungeonGatePartTypeEnum partType();

    // By default, offset x+1 = right block, offset x-1 = left block
    // Facing south (z+) => offset x+1 = right block, offset x-1 = left bl  ock
    // Facing north (z-) => offset x-1 = right block, offset x+1 = left block
    // Facing west (x-) => offset z+1 = right block, offset z-1 = left block
    // Facing east (x+) => offset z-1 = right block, offset z+1 = left block
    default Vector3i offset(Direction facing) {
        return MathUtil.rotateYFromSouth(this.offset(), facing);
    }

    default Vector3i reverseOffset(Direction facing) {
        return MathUtil.oppositeVector3i(MathUtil.rotateYFromSouth(this.offset(), facing));
    }

    default boolean isGateway() {
        return this.partType() == DungeonGatePartTypeEnum.GATEWAY;
    }

    default boolean isGateHinge() {
        return this.partType().isHinge();
    }
    default boolean isGateHingeRight() {
        return this.partType() == DungeonGatePartTypeEnum.HINGE_RIGHT;
    }

    default boolean isGateHingeLeft() {
        return this.partType() == DungeonGatePartTypeEnum.HINGE_LEFT;
    }

    default boolean isGateHingeTop() {
        return this.partType() == DungeonGatePartTypeEnum.HINGE_TOP;
    }

    default boolean isGateHingeBottom() {
        return this.partType() == DungeonGatePartTypeEnum.HINGE_BOTTOM;
    }
    default boolean isOpenRight() {
        return this.partType() == DungeonGatePartTypeEnum.OPEN_GATEWAY_RIGHT;
    }

    default boolean isOpenLeft() {
        return this.partType() == DungeonGatePartTypeEnum.OPEN_GATEWAY_LEFT;
    }

    default boolean isOpenTop() {
        return this.partType() == DungeonGatePartTypeEnum.OPEN_GATEWAY_TOP;
    }

    default boolean isOpenBottom() {
        return this.partType() == DungeonGatePartTypeEnum.OPEN_GATEWAY_BOTTOM;
    }

    default boolean isBottom() {
        return this.offset().getY() == 0;
    }

    default boolean isOpenPart() {
        return this.partType().isOpen();
    }
}
