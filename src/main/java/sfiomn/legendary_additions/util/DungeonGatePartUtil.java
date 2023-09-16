package sfiomn.legendary_additions.util;

import net.minecraft.util.Direction;
import net.minecraft.world.IWorld;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.DungeonGateBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DungeonGatePartUtil {
    private final List<IDungeonGatePart> dungeonGateParts;
    private final List<IDungeonGatePart> dungeonGateCloseParts;
    private final List<IDungeonGatePart> dungeonGateOpenParts;
    private IDungeonGatePart mostBottomRight;
    private IDungeonGatePart mostBottomLeft;
    private int height = 0;
    public DungeonGatePartUtil(IDungeonGatePart[] dungeonGateParts) {
        this.dungeonGateParts = Arrays.asList(dungeonGateParts);
        dungeonGateCloseParts = new ArrayList<>();
        dungeonGateOpenParts = new ArrayList<>();
    }

    public List<IDungeonGatePart> dungeonGateParts() {
        return dungeonGateParts;
    }

    public List<IDungeonGatePart> getCloseParts() {
        if (dungeonGateCloseParts.isEmpty()) {
            for (IDungeonGatePart part: this.dungeonGateParts) {
                if (!part.isOpenPart()) {
                    this.dungeonGateCloseParts.add(part);
                }
            }
        }
        return dungeonGateCloseParts;
    }

    public List<IDungeonGatePart> getOpenParts() {
        if (dungeonGateOpenParts.isEmpty()) {
            for (IDungeonGatePart part: this.dungeonGateParts) {
                if (part.isOpenPart()) {
                    this.dungeonGateOpenParts.add(part);
                }
            }
        }
        return dungeonGateOpenParts;
    }

    // direction unchanged if not gateHinge or gateOpen part
    public Direction getOpenDirection(IDungeonGatePart part, Direction gateFacing) {
        Direction gateDirection = gateFacing;
        if (part.isOpenRight() || part.isGateHingeRight()) {
            gateDirection = gateFacing.getClockWise();
        } else if (part.isOpenLeft() || part.isGateHingeLeft()) {
            gateDirection = gateFacing.getCounterClockWise();
        } else if (part.isOpenTop() || part.isGateHingeTop()) {
            gateDirection = Direction.DOWN;
        } else if (part.isOpenBottom() || part.isGateHingeBottom()) {
            gateDirection = Direction.UP;
        }
        return gateDirection;
    }

    public boolean isMostRight(IDungeonGatePart part) {
        return part.offset().getX() == getMostBottomRight().offset().getX();
    }

    public boolean isMostLeft(IDungeonGatePart part) {
        return part.offset().getX() == getMostBottomLeft().offset().getX();
    }

    public boolean isTop(IDungeonGatePart part) {
        for (IDungeonGatePart dungeonGatePart: this.dungeonGateParts) {
            if (dungeonGatePart.offset().getX() == part.offset().getX() && dungeonGatePart.offset().getZ() == part.offset().getZ()) {
                if (dungeonGatePart.offset().getY() > part.offset().getY())
                    return false;
            }
        }
        return true;
    }

    public List<IDungeonGatePart> getNeighbourOffsets(IDungeonGatePart part) {
        List<IDungeonGatePart> neighbourOffsets = new ArrayList<>();
        for (IDungeonGatePart dungeonGatePart: this.dungeonGateParts) {
            if (dungeonGatePart.offset().distManhattan(part.offset()) == 1) {
                neighbourOffsets.add(dungeonGatePart);
            }
        }

        return neighbourOffsets;
    }

    public IDungeonGatePart getMostBottomRight() {
        if (mostBottomRight == null) {
            mostBottomRight = mostBottomRight();
        }
        return mostBottomRight;
    }

    public IDungeonGatePart getMostBottomLeft() {
        if (mostBottomLeft == null) {
            mostBottomLeft = mostBottomLeft();
        }
        return mostBottomLeft;
    }

    public int getHeight() {
        if (this.height == 0) {
            this.height = heightCalculation();
        }
        return this.height;
    }

    public int heightCalculation() {
        int height = 1;
        for (IDungeonGatePart part: this.dungeonGateParts) {
            if (part.offset().getY() + 1 > height) {
                height = part.offset().getY() + 1;
            }
        }
        return height;
    }

    public IDungeonGatePart mostBottomRight() {
        IDungeonGatePart mostBottomRight = null;
        for (IDungeonGatePart part: this.dungeonGateParts) {
            if (part.isBottom() && part.offset().getZ() == 0) {
                if (mostBottomRight == null || mostBottomRight.offset().getX() < part.offset().getX()) {
                    mostBottomRight = part;
                }
            }
        }
        return mostBottomRight;
    }

    public IDungeonGatePart mostBottomLeft() {
        IDungeonGatePart mostBottomLeft = null;
        for (IDungeonGatePart part: this.dungeonGateParts) {
            if (part.isBottom() && part.offset().getZ() == 0) {
                if (mostBottomLeft == null || mostBottomLeft.offset().getX() > part.offset().getX()) {
                    mostBottomLeft = part;
                }
            }
        }
        return mostBottomLeft;
    }
}
