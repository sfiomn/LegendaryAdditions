package sfiomn.legendary_additions.util;

import net.minecraft.util.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GatePartUtil {
    private final List<IGatePart> dungeonGateParts;
    private final List<IGatePart> dungeonGateCloseParts;
    private final List<IGatePart> dungeonGateOpenParts;
    private IGatePart mostBottomRight;
    private IGatePart mostBottomLeft;
    private int height = 0;
    private int width = 0;
    public GatePartUtil(IGatePart[] dungeonGateParts) {
        this.dungeonGateParts = Arrays.asList(dungeonGateParts);
        this.dungeonGateCloseParts = new ArrayList<>();
        this.dungeonGateOpenParts = new ArrayList<>();
    }

    public List<IGatePart> gateParts() {
        return dungeonGateParts;
    }

    public List<IGatePart> getCloseParts() {
        if (dungeonGateCloseParts.isEmpty()) {
            for (IGatePart part: this.dungeonGateParts) {
                if (!part.isOpenPart()) {
                    this.dungeonGateCloseParts.add(part);
                }
            }
        }
        return dungeonGateCloseParts;
    }

    public List<IGatePart> getOpenParts() {
        if (dungeonGateOpenParts.isEmpty()) {
            for (IGatePart part: this.dungeonGateParts) {
                if (part.isOpenPart()) {
                    this.dungeonGateOpenParts.add(part);
                }
            }
        }
        return dungeonGateOpenParts;
    }

    // direction unchanged if not gateHinge or gateOpen parts
    public Direction getOpenDirection(IGatePart part, Direction gateFacing) {
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

    public boolean isMostRight(IGatePart part) {
        return part.offset().getX() == getMostBottomRight().offset().getX();
    }

    public boolean isMostLeft(IGatePart part) {
        return part.offset().getX() == getMostBottomLeft().offset().getX();
    }

    public boolean isTop(IGatePart part) {
        for (IGatePart dungeonGatePart: this.dungeonGateParts) {
            if (dungeonGatePart.offset().getX() == part.offset().getX() && dungeonGatePart.offset().getZ() == part.offset().getZ()) {
                if (dungeonGatePart.offset().getY() > part.offset().getY())
                    return false;
            }
        }
        return true;
    }

    public List<IGatePart> getNeighbourOffsets(IGatePart part) {
        List<IGatePart> neighbourOffsets = new ArrayList<>();
        for (IGatePart dungeonGatePart: this.dungeonGateParts) {
            if (dungeonGatePart.offset().distManhattan(part.offset()) == 1) {
                neighbourOffsets.add(dungeonGatePart);
            }
        }

        return neighbourOffsets;
    }

    public IGatePart getMostBottomRight() {
        if (mostBottomRight == null) {
            mostBottomRight = mostBottomRight();
        }
        return mostBottomRight;
    }

    public IGatePart getMostBottomLeft() {
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

    public int getWidth() {
        if (this.width == 0) {
            this.width = widthCalculation();
        }
        return this.width;
    }

    public int widthCalculation() {
        int left = 0;
        int right = 0;
        for (IGatePart part: this.dungeonGateParts) {
            if (part.offset().getX() > right) {
                right = part.offset().getX();
            } else if (part.offset().getX() < left) {
                left = part.offset().getX();
            }
        }
        return right - left + 1;
    }

    public int heightCalculation() {
        int height = 1;
        for (IGatePart part: this.dungeonGateParts) {
            if (part.offset().getY() + 1 > height) {
                height = part.offset().getY() + 1;
            }
        }
        return height;
    }

    public IGatePart mostBottomRight() {
        IGatePart mostBottomRight = null;
        for (IGatePart part: this.dungeonGateParts) {
            if (part.isBottom() && part.offset().getZ() == 0) {
                if (mostBottomRight == null || mostBottomRight.offset().getX() < part.offset().getX()) {
                    mostBottomRight = part;
                }
            }
        }
        return mostBottomRight;
    }

    public IGatePart mostBottomLeft() {
        IGatePart mostBottomLeft = null;
        for (IGatePart part: this.dungeonGateParts) {
            if (part.isBottom() && part.offset().getZ() == 0) {
                if (mostBottomLeft == null || mostBottomLeft.offset().getX() > part.offset().getX()) {
                    mostBottomLeft = part;
                }
            }
        }
        return mostBottomLeft;
    }
}
