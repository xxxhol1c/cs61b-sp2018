package byog.Core;

import java.io.Serializable;
import java.util.Random;

/**
 * To create the room, you need at least one position as the start point.
 * To help determine the size of a room, I set the two position.
 * Add some methods to help judge the room is valid.
 */
public class Room implements Comparable<Room>, Serializable {
    protected final Position bottomLeft;
    protected final Position upperRight;

    public Room(Position bl, Position ur) {
        bottomLeft = bl;
        upperRight = ur;
    }

    public boolean canConstruct() {
        int maxHeight = 6;
        int maxWidth = 8;
        return  bottomLeft.xP < upperRight.xP - 2
                && bottomLeft.yP < upperRight.yP - 2
                && this.getWidth() <= maxWidth
                && this.getHeight() <= maxHeight;
    }

    public boolean isOverlap(Room r) {
        return !((this.upperRight.xP < r.bottomLeft.xP)
                || (r.upperRight.xP < this.bottomLeft.xP)
                || (this.upperRight.yP < r.bottomLeft.yP)
                || (r.upperRight.yP < this.bottomLeft.yP));
    }


    public Position connectPosition(World world) {
        Random rand = new Random(world.seed);
        return new Position(
                RandomUtils.uniform(rand, bottomLeft.xP + 1, upperRight.xP),
                RandomUtils.uniform(rand, bottomLeft.yP + 1, upperRight.yP));
    }

    public int getWidth() {
        return this.upperRight.xP - this.bottomLeft.xP;
    }

    public int getHeight() {
        return this.upperRight.yP - this.bottomLeft.yP;
    }

    @Override
    public int compareTo(Room room) {
        return this.bottomLeft.xP - room.bottomLeft.xP;
    }

}

