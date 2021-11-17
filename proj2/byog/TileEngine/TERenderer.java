package byog.TileEngine;

import byog.Core.Position;
import byog.Core.World;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

/**
 * Utility class for rendering tiles. You do not need to modify this file. You're welcome
 * to, but be careful. We strongly recommend getting everything else working before
 * messing with this renderer, unless you're trying to do something fancy like
 * allowing scrolling of the screen or tracking the player or something similar.
 */
public class TERenderer implements Serializable {
    private static final int TILE_SIZE = 16;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;

    /**
     * Same functionality as the other initialization method. The only difference is that the xOff
     * and yOff parameters will change where the renderFrame method starts drawing. For example,
     * if you select w = 60, h = 30, xOff = 3, yOff = 4 and then call renderFrame with a
     * TETile[50][25] array, the renderer will leave 3 tiles blank on the left, 7 tiles blank
     * on the right, 4 tiles blank on the bottom, and 1 tile blank on the top.
     *
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h, int xOff, int yOff) {
        this.width = w;
        this.height = h;
        this.xOffset = xOff;
        this.yOffset = yOff;
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /**
     * Initializes StdDraw parameters and launches the StdDraw window. w and h are the
     * width and height of the world in number of tiles. If the TETile[][] array that you
     * pass to renderFrame is smaller than this, then extra blank space will be left
     * on the right and top edges of the frame. For example, if you select w = 60 and
     * h = 30, this method will create a 60 tile wide by 30 tile tall window. If
     * you then subsequently call renderFrame with a TETile[50][25] array, it will
     * leave 10 tiles blank on the right side and 5 tiles blank on the top side. If
     * you want to leave extra space on the left or bottom instead, use the other
     * initializatiom method.
     *
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h) {
        initialize(w, h, 0, 0);
    }

    /**
     * Takes in a 2d array of TETile objects and renders the 2d array to the screen, starting from
     * xOffset and yOffset.
     * <p>
     * If the array is an NxM array, then the element displayed at positions would be as follows,
     * given in units of tiles.
     * <p>
     * positions   xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length
     * <p>
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     * ...    ......  |  ......  |  ......  | .... | ......
     * startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     * startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     * startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     * <p>
     * By varying xOffset, yOffset, and the size of the screen when initialized, you can leave
     * empty space in different places to leave room for other information, such as a GUI.
     * This method assumes that the xScale and yScale have been set such that the max x
     * value is the width of the screen in tiles, and the max y value is the height of
     * the screen in tiles.
     *
     * @param world the 2D TETile[][] array to render
     */

    public void renderFrame(TETile[][] world) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }
        StdDraw.show();
    }

    /**
     * As renderFrame Method, this method is to render the 2D array of tiles.
     * In this method, I add the mouseUI method and remove the StdDraw.show() at the last.
     *  The reason I do this is to avoid the flashing text at the status bar.
     * @param w the world created in the game
     */
    public void renderWorld(World w) {
        int numXTiles = w.getTiles().length;
        int numYTiles = w.getTiles()[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (w.getTiles()[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                w.getTiles()[x][y].draw(x + xOffset, y + yOffset);
            }
        }
        mouseUI(w);
    }

    /**
     * Render the 6 * 6 range 2D tiles around the given position (player)
     * to simulate the view of a player.
     * Notice that the boundary of the world.
     * @param w the world created in the game
     */
    public void renderLight(World w) {
        TETile[][] world = w.getTiles();
        Position p = w.getPlayer();
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        int minX = Math.max(0, p.getX() - 3);
        int maxX = Math.min(numXTiles - 1, p.getX() + 3);
        int minY = Math.max(0, p.getY() - 3);
        int maxY = Math.min(numYTiles - 1, p.getY() + 3);
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = minX; x <= maxX; x += 1) {
            for (int y = minY; y <= maxY; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }
        mouseUI(w);
    }

    /**
     * A method to implement the mouse interaction.
     * Notice that if the position of your mouse is out of the world,
     * it would cause outOfIndexException.
     * @param w  the world created in the game.
     */
    private void mouseUI(World w) {
        int midWidth = 40;
        int top = 30;
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        if (mouseX < 80 && mouseX >= 0 && mouseY >= 0 && mouseY <= 30 - 1) {
            if (w.getTiles()[mouseX][mouseY].equals(Tileset.LOCKED_DOOR)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Locked, you need 3 keys.");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.WALL)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Solid walls");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.FLOOR)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Hallway");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.SNAKE)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Ferocious snake, be careful!");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.TREASURE)
                    || w.getTiles()[mouseX][mouseY].equals(Tileset.EMPTYTREASURE)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Treasure! Some are empty, open it and pass.");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.PLAYER)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "You");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.ATTACKED)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "You are attacked, run!");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.OPENEDTREASURE)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Opened treasure, you can pass.");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.HEALED)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Get healed!");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.HERB)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Herb, it can help you.");
            } else if (w.getTiles()[mouseX][mouseY].equals(Tileset.UNLOCKED_DOOR)) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Now, escape from here!");
            } else {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(midWidth, top, "Outer world");
            }
        }
    }
}
