package sfiomn.legendary_additions.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;

public class MathUtil {
    public static Vector3i rotateYFromSouth(Vector3i vector, Direction toDirection) {
        switch (toDirection) {
            case NORTH:
                return new Vector3i(-vector.getX(), vector.getY(), -vector.getZ());
            case EAST:
                return new Vector3i(vector.getZ(), vector.getY(), -vector.getX());
            case WEST:
                return new Vector3i(-vector.getZ(), vector.getY(), vector.getX());
            default:
                return vector;
        }
    }

    public static Vector3d rotateYFromSouth(Vector3d vector, Direction toDirection) {
        switch (toDirection) {
            case NORTH:
                return new Vector3d(1 - vector.x, vector.y, 1 - vector.z);
            case EAST:
                return new Vector3d(vector.z, vector.y, 1 - vector.x);
            case WEST:
                return new Vector3d(1 - vector.z, vector.y, vector.x);
            default:
                return vector;
        }
    }

    public static float getAngleFromWest(Direction toDirection) {
        switch (toDirection) {
            case NORTH:
                return 270.0F;
            case EAST:
                return 180.0f;
            case WEST:
                return 0.0f;
            default:
                return 90.0f;
        }
    }

    public static float getAngleFromNorth(Direction toDirection) {
        switch (toDirection) {
            case NORTH:
                return 0.0f;
            case EAST:
                return 90.0f;
            case WEST:
                return -90.0f;
            default:
                return 180.0F;
        }
    }

    public static Vector3i oppositeVector3i(Vector3i vector3i) {
        return new Vector3i(-vector3i.getX(), -vector3i.getY(), -vector3i.getZ());
    }
}
