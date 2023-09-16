package sfiomn.legendary_additions.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;

public class MathUtil {
    public static Vector3d rotateYFromSouth(Vector3d vector, Direction toDirection) {
        return vector.yRot(rotationAngleDegrees(toDirection));
    }

    public static Vector3i rotateYFromSouth(Vector3i vector, Direction toDirection) {
        int angle = (int) rotationAngleDegrees(toDirection);
        int x = (int) (Math.cos(angle) * vector.getX() + Math.sin(angle) * vector.getZ());
        int z = (int) (Math.cos(angle) * vector.getZ() - Math.sin(angle) * vector.getX());

        return new Vector3i(x, vector.getY(), z);
    }

    public static float rotationAngleDegrees(Direction facing) {
        switch (facing) {
            case NORTH:
                return 180;
            case EAST:
                return -90;
            case WEST:
                return 90;
            default:
                return 0;
        }

    }

    public static Vector3i oppositeVector3i(Vector3i vector3i) {
        return new Vector3i(-vector3i.getX(), -vector3i.getY(), -vector3i.getZ());
    }
}
