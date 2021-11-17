package byog.Core;

import java.io.Serializable;
import java.util.Random;

/**
 * To create the room, you need at least one position as the start point.
 * To help determine the size of a room, I set the two position.
 * Add some methods to help judge the room is valid.
 */
public class Room implements Comparable<Room>, Serializable {
    private final Position bottomLeft;
    private final Position upperRight;

    public Position getBottomLeft() {
        return bottomLeft;
    }

    public Position getUpperRight() {
        return upperRight;
    }

    public Room(Position bl, Position ur) {
        bottomLeft = bl;
        upperRight = ur;
    }

    public boolean canConstruct() {
        int maxHeight = 6;
        int maxWidth = 8;
        return  bottomLeft.getX() < upperRight.getX() - 2
                && bottomLeft.getY() < upperRight.getY() - 2
                && this.getWidth() <= maxWidth
                && this.getHeight() <= maxHeight;
    }

    public boolean isOverlap(Room r) {
        return !((this.getUpperRight().getX() < r.getBottomLeft().getX())
                || (r.getUpperRight().getX() < this.getBottomLeft().getX())
                || (this.getUpperRight().getY() < r.getBottomLeft().getY())
                || (r.getUpperRight().getY() < this.getBottomLeft().getY()));
    }


    public Position connectPosition(World world) {
        Random rand = new Random(world.getSeed());
        return new Position(
                RandomUtils.uniform(rand, bottomLeft.getX() + 1, upperRight.getX()),
                RandomUtils.uniform(rand, bottomLeft.getY() + 1, upperRight.getY()));
    }

    public int getWidth() {
        return this.upperRight.getX() - this.bottomLeft.getX();
    }

    public int getHeight() {
        return this.upperRight.getY() - this.bottomLeft.getY();
    }

    @Override
    public int compareTo(Room room) {
        return this.bottomLeft.getX() - room.bottomLeft.getX();
    }

}

