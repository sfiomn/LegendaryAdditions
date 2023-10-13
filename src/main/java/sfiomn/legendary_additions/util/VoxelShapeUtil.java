package sfiomn.legendary_additions.util;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import sfiomn.legendary_additions.LegendaryAdditions;

public class VoxelShapeUtil {
    public static VoxelShape xCentered(double thickness) {
        return Block.box((16 - thickness) * 0.5, 0.0D, 0.0D, ((16 - thickness) * 0.5 + thickness), 16.0D, 16.0D);
    }

    public static VoxelShape yCenteredShape(double thickness) {
        return Block.box(0.0D, (16 - thickness) * 0.5, 0.0D, 16.0D, ((16 - thickness) * 0.5 + thickness), 16.0D);
    }

    public static VoxelShape zCentered(double thickness) {
        return Block.box(0.0D, 0.0D, (16 - thickness) * 0.5, 16.0D, 16.0D, ((16 - thickness) * 0.5 + thickness));
    }

    public static VoxelShape oppositeFacingBox(double thickness, Direction facing) {
        return new VoxelShape[]
                {
                        Block.box(0.0d, 16.0d - thickness, 0.0d, 16.0d, 16.0d, 16.0d), // DOWN
                        Block.box(0.0d, 0.0d, 0.0d, 16.0d, thickness, 16.0d), // UP
                        Block.box(0.0D, 0.0D, 16.0d - thickness, 16.0D, 16.0D, 16.0D), // NORTH
                        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, thickness), // SOUTH
                        Block.box(16.0d - thickness, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), // WEST
                        Block.box(0.0D, 0.0D, 0.0D, thickness, 16.0D, 16.0D), // EAST
                }[facing.get3DDataValue()];
    }

    public static VoxelShape moveTo(VoxelShape shape, Direction facing, double moveValue) {
        Vector3d moveVector = Vector3d.atLowerCornerOf(facing.getNormal()).multiply(moveValue / 16.0, moveValue / 16.0, moveValue / 16.0);
        AxisAlignedBB bounds = shape.bounds().move(moveVector);
        return shape.isEmpty() ? shape: Block.box(Math.min(bounds.minX, 1.0D) * 16, Math.min(bounds.minY, 1.0D) * 16, Math.min(bounds.minZ, 1.0D) * 16, Math.min(bounds.maxX, 1.0D) * 16, Math.min(bounds.maxY, 1.0D) * 16, Math.min(bounds.maxZ, 1.0D) * 16);
    }
}
