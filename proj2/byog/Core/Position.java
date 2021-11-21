package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;


public class Position implements Serializable {
    protected int xP;
    protected int yP;
    private TETile previous = Tileset.FLOOR;

    public Position(int x, int y) {
        xP = x;
        yP = y;
    }

    public int getX() {
        return xP;
    }

    public int getY() {
        return yP;
    }

    public static Position leftPosition(Position p1, Position p2) {
        if (p1.xP < p2.xP) {
            return p1;
        }
        return p2;
    }

    public static Position rightPosition(Position p1, Position p2) {
        if (p1.xP >= p2.xP) {
            return p1;
        }
        return p2;
    }

    public static Position downPosition(Position p1, Position p2) {
        if (p1.yP < p2.yP) {
            return p1;
        }
        return p2;
    }

    public static Position upPosition(Position p1, Position p2) {
        if (p1.yP >= p2.yP) {
            return p1;
        }
        return p2;
    }

    /**
     * The method to simulate the movement of the player.
     * Set a position to record the previous tile at the given position.
     * When you move on it, the present position is replaced by the player
     * (original, attacked, or healed).
     * Then you passed away, the start position should be replaced by the previous tile.
     * The herb should not be recorded, because you eat it.
     * And you cannot pass through the wall and the treasure unopened.
     * @param w created world in the game
     */
    public void moveLeft(World w) {
        if (w.tiles[xP - 1][yP].equals(Tileset.SNAKE)) {
            w.tiles[xP - 1][yP] = Tileset.ATTACKED;
            w.tiles[xP][yP] = previous;
            previous = Tileset.SNAKE;
            xP = xP - 1;
        } else if (w.tiles[xP - 1][yP].equals(Tileset.OPENEDTREASURE)) {
            w.tiles[xP - 1][yP] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.OPENEDTREASURE;
            xP = xP - 1;
        } else if (w.tiles[xP - 1][yP].equals(Tileset.FLOOR)) {
            w.tiles[xP - 1][yP] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            xP = xP - 1;
        } else if (w.tiles[xP - 1][yP].equals(Tileset.HERB)) {
            w.tiles[xP - 1][yP] = Tileset.HEALED;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            xP = xP - 1;
        } else if (w.tiles[xP - 1][yP].equals(Tileset.UNLOCKED_DOOR)) {
            w.tiles[xP - 1][yP] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            xP = xP - 1;
        }
    }

    public void moveRight(World w) {
        if (w.tiles[xP + 1][yP].equals(Tileset.SNAKE)) {
            w.tiles[xP + 1][yP] = Tileset.ATTACKED;
            w.tiles[xP][yP] = previous;
            previous = Tileset.SNAKE;
            xP = xP + 1;
        } else if (w.tiles[xP + 1][yP].equals(Tileset.OPENEDTREASURE)) {
            w.tiles[xP + 1][yP] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.OPENEDTREASURE;
            xP = xP + 1;
        } else if (w.tiles[xP + 1][yP].equals(Tileset.FLOOR)) {
            w.tiles[xP + 1][yP] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            xP = xP + 1;
        } else if (w.tiles[xP + 1][yP].equals(Tileset.HERB)) {
            w.tiles[xP + 1][yP] = Tileset.HEALED;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            xP = xP + 1;
        } else if (w.tiles[xP + 1][yP].equals(Tileset.UNLOCKED_DOOR)) {
            w.tiles[xP + 1][yP] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            xP = xP + 1;
        }
    }

    public void moveUp(World w) {
        if (w.tiles[xP][yP + 1].equals(Tileset.SNAKE)) {
            w.tiles[xP][yP + 1] = Tileset.ATTACKED;
            w.tiles[xP][yP] = previous;
            previous = Tileset.SNAKE;
            yP = yP + 1;
        } else if (w.tiles[xP][yP + 1].equals(Tileset.OPENEDTREASURE)) {
            w.tiles[xP][yP + 1] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.OPENEDTREASURE;
            yP = yP + 1;
        } else if (w.tiles[xP][yP + 1].equals(Tileset.FLOOR)) {
            w.tiles[xP][yP + 1] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            yP = yP + 1;
        } else if (w.tiles[xP][yP + 1].equals(Tileset.HERB)) {
            w.tiles[xP][yP + 1] = Tileset.HEALED;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            yP = yP + 1;
        } else if (w.tiles[xP][yP + 1].equals(Tileset.UNLOCKED_DOOR)) {
            w.tiles[xP][yP + 1] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            yP = yP + 1;
        }
    }

    public void moveDown(World w) {
        if (w.tiles[xP][yP - 1].equals(Tileset.SNAKE)) {
            w.tiles[xP][yP - 1] = Tileset.ATTACKED;
            w.tiles[xP][yP] = previous;
            previous = Tileset.SNAKE;
            yP = yP - 1;
        } else if (w.tiles[xP][yP - 1].equals(Tileset.OPENEDTREASURE)) {
            w.tiles[xP][yP - 1] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.OPENEDTREASURE;
            yP = yP - 1;
        } else if (w.tiles[xP][yP - 1].equals(Tileset.FLOOR)) {
            w.tiles[xP][yP - 1] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            yP = yP - 1;
        } else if (w.tiles[xP][yP - 1].equals(Tileset.HERB)) {
            w.tiles[xP][yP - 1] = Tileset.HEALED;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            yP = yP - 1;
        } else if (w.tiles[xP][yP - 1].equals(Tileset.UNLOCKED_DOOR)) {
            w.tiles[xP][yP - 1] = Tileset.PLAYER;
            w.tiles[xP][yP] = previous;
            previous = Tileset.FLOOR;
            yP = yP - 1;
        }
    }
}
