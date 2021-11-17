package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;
    private static final long SEED = 28731;
    private static final Random RANDOM = new Random(SEED);
    // set the start position to add pattern
    private static class Position {
        int xP;
        int yP;
        private Position(int x, int y) {
            xP = x;
            yP = y;
        }
    }

    public static void fillWithNothing(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    // calculate the width of each line with the given size
    private static int hexagonWidth(int lineNum, int size) {
        int width;
        if (lineNum < size) {
            width = size + 2 * lineNum;
        } else {
            width = (size - 1) * 2 + size - 2 * (lineNum - size);
        }
        return width;
    }

    // add the pattern in the row
    private static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int i = 0; i < width; i += 1) {
            world[p.xP + i][p.yP] = t;
        }
    }

    // test the width method
    @Test
    public void testWidth() {
        assertEquals(2, hexagonWidth(0, 2));
        assertEquals(4, hexagonWidth(1, 2));
        assertEquals(4, hexagonWidth(2, 2));
        assertEquals(2, hexagonWidth(3, 2));
        assertEquals(5, hexagonWidth(0, 5));
        assertEquals(11, hexagonWidth(3, 5));
        assertEquals(13, hexagonWidth(4, 5));
        assertEquals(13, hexagonWidth(5, 5));
        assertEquals(11, hexagonWidth(6, 5));
        assertEquals(5, hexagonWidth(9, 5));
    }

    private static int xRest(int lineNum, int size) {
        if (lineNum < size) {
            return -lineNum;
        } else {
            return  lineNum - 2 * size + 1;
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int size, TETile t) {
        for (int lineNum = 0; lineNum < 2 * size; lineNum += 1) {
            int width = hexagonWidth(lineNum, size);
            int start = p.xP + xRest(lineNum, size);
            Position startPosition = new Position(start, p.yP + lineNum);
            addRow(world, startPosition, width, t);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(7);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.GRASS;
            case 3:
                return Tileset.LOCKED_DOOR;
            case 4:
                return Tileset.UNLOCKED_DOOR;
            case 5:
                return Tileset.TREE;
            case 6:
                return Tileset.WATER;
            default:
                return Tileset.NOTHING;
        }
    }

    // draw hexagons from the bottom
    private static void drawVertical(TETile[][] world, Position p, int size, int yRange) {
        Position nextPosition = new Position(p.xP, p.yP);
        for (int i = 0; i < yRange; i += 1) {
            addHexagon(world, nextPosition, size, randomTile());
            nextPosition.yP += 2 * size;
        }
    }

    // stupid way to expand the pattern
    private static void tesselationHexagons(TETile[][] world, Position p, int size,
                                        int xRange, int yRange) {
        drawVertical(world, p, size, yRange);
        Position newLeft = new Position(p.xP, p.yP);
        Position newRight = new Position(p.xP, p.yP);
        for (int i = 0; i <= xRange; i += 1) {
            drawVertical(world, newLeft, size, yRange);
            newLeft.xP = newLeft.xP - 2 * size + 1;
            newLeft.yP = newLeft.yP + size;
            drawVertical(world, newRight, size, yRange);
            newRight.xP = newRight.xP + 2 * size - 1;
            newRight.yP = newRight.yP + size;
            yRange -= 1;
        }

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile [WIDTH][HEIGHT];
        Position p = new Position(45, 30);
        fillWithNothing(world);
        // addRow(world, p, 2, t);
        // addHexagon(world, p, 4, t);
        // drawVertical(world, p, 3, 3);
        tesselationHexagons(world, p, 4, 2, 5);
        ter.renderFrame(world);
    }
}
