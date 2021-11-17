
package byog.Core;
/*
 * The first version to generate the world
 * The following step:
 * 1: try to construct a room and give some restrictions
 * 2: make a method so that the new room would not overlap the other
 * 3: connect the existed rooms with a hallway (a special room)
 */

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class World extends Game implements Serializable {
    private final int width;
    private final int height;
    private final long seed;
    private final TETile[][] tiles;
    private ArrayList<Room> roomList;
    private final int maxRoomNum = 30;
    private final Random rand;
    private Position player;
    private Position lockedDoor;

    public World(int w, int h, long s) {
        tiles = new TETile[w][h];
        width = w;
        height = h;
        seed = s;
        rand = new Random(s);
    }

    public TETile[][] getTiles() {
        return tiles;
    }

    public long getSeed() {
        return seed;
    }

    public Position getPlayer() {
        return player;
    }

    public Position getLockedDoor() {
        return lockedDoor;
    }

    private void fillWithNothing() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                this.tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* avoid the room would not cross the boundary of world*/
    private boolean haveCapacity(Room r) {
        return r.getBottomLeft().getX() >= 0 && r.getBottomLeft().getX() <= width - 1
                && r.getUpperRight().getX() >= 0 && r.getUpperRight().getX() < width - 1
                && r.getBottomLeft().getY() >= 0 && r.getBottomLeft().getY() <= height - 1
                && r.getUpperRight().getY() >= 0 && r.getUpperRight().getY() < height - 1;
    }

    /*
     * This method is to generate random rooms in the world
     * My solution is:
     * 1: pick two random position
     * 2: check whether it can construct
     * 3: add it to the ArrayList (help to use overlap method)
     */
    private void addRandomRooms() {
        this.roomList = new ArrayList<>(maxRoomNum);
        while (roomList.size() < maxRoomNum) {
            int leftBottomX = rand.nextInt(width);
            int leftBottomY = rand.nextInt(height);
            int rightUpperX = rand.nextInt(width);
            int rightUpperY = rand.nextInt(height);
            Room newRoom = new Room(new Position(leftBottomX, leftBottomY),
                    new Position(rightUpperX, rightUpperY));
            /*
             * set a variable to decide whether to generate the room
             * cuz in the for-loop, change the elements would result an error
             */
            boolean valid = true;
            if (!newRoom.canConstruct() || !this.haveCapacity(newRoom)) {
                continue;
            } else {
                for (Room room : roomList) {
                    if (newRoom.isOverlap(room)) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid) {
                this.roomList.add(newRoom);
                this.constructRoom(newRoom);
            }
        }
    }

    /*
     * Draw the room
     * Make sure that it would not cover the existed floor
     * This would help in the construction of hallway
     */
    private void constructRoom(Room r) {
        for (int i = r.getBottomLeft().getX(); i <= r.getUpperRight().getX(); i += 1) {
            for (int j = r.getBottomLeft().getY(); j <= r.getUpperRight().getY(); j += 1) {
                if (this.tiles[i][j] != Tileset.FLOOR) {
                    if (i == r.getBottomLeft().getX()
                            || i == r.getUpperRight().getX()
                            || j == r.getBottomLeft().getY()
                            || j == r.getUpperRight().getY()) {
                        this.tiles[i][j] = Tileset.WALL;
                    } else {
                        this.tiles[i][j] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    /*
     * A hallway is a special size of room:
     * it only has one line of floor (size 3)
     * so it can construct by using constructRoom method
     * The L-Hallway is kinda intersection of two rooms
     * Revise the constructRoom method so that
     * it would not cover the existed floor
     * Make a corner position to help construct
     */

    private Room addParallelHallway(Position s, Position e) {
        Position bl = new Position(s.getX() - 1, s.getY() - 1);
        Position ur = new Position(e.getX() + 1, e.getY() + 1);
        return new Room(bl, ur);
    }

    private Room addVerticalHallway(Position p1, Position p2) {
        Position s = Position.downPosition(p1, p2);
        Position e = Position.upPosition(p1, p2);
        return addParallelHallway(s, e);
    }


    private void drawLHallway(Room r1, Room r2) {
        int choose = rand.nextInt(2);
        Position p1 = r1.connectPosition(this);
        Position p2 = r2.connectPosition(this);
        Position s = Position.leftPosition(p1, p2);
        Position e = Position.rightPosition(p1, p2);
        switch (choose) {
            case 0: {
                Position corner = new Position(e.getX(), s.getY());
                Room parallelRoom = addParallelHallway(s, corner);
                Room verticalRoom = addVerticalHallway(corner, e);
                constructRoom(parallelRoom);
                constructRoom(verticalRoom);
                break;
            }
            case 1: {
                Position corner = new Position(s.getX(), e.getY());
                Room verticalRoom = addVerticalHallway(s, corner);
                Room parallelRoom = addParallelHallway(corner, e);
                constructRoom(parallelRoom);
                constructRoom(verticalRoom);
                break;
            }
            default: break;
        }
    }

    private void connectRoomList(ArrayList<Room> rooms) {
        for (int i = 0; i < rooms.size() - 1; i += 1) {
            drawLHallway(rooms.get(i), rooms.get(i + 1));
        }
    }


    /*
     * Pick a position randomly and add it with a locked door
     * One side of the position should be blank space
     * and the other should be floor.
     */

    private void addDoorAndPlayer() {
        while (true) {
            Position p = new Position(RandomUtils.uniform(rand, 1, width - 1),
                    RandomUtils.uniform(rand, 1, height - 1));
            Position p2 = new Position(RandomUtils.uniform(rand, 1, width - 1),
                    RandomUtils.uniform(rand, 1, height - 1));
            if (((tiles[p.getX() - 1][p.getY()] == Tileset.NOTHING
                    && tiles[p.getX() + 1][p.getY()] == Tileset.FLOOR)
                    || (tiles[p.getX() + 1][p.getY()] == Tileset.NOTHING
                    && tiles[p.getX() - 1][p.getY()] == Tileset.FLOOR)
                    || (tiles[p.getX()][p.getY() - 1] == Tileset.NOTHING
                    && tiles[p.getX()][p.getY() + 1] == Tileset.FLOOR)
                    || (tiles[p.getX()][p.getY() + 1] == Tileset.NOTHING
                    && tiles[p.getX()][p.getY() - 1] == Tileset.FLOOR))
                    && (tiles[p2.getX()][p2.getY()]) == Tileset.FLOOR
                    && Math.abs(p.getX() - p2.getX()) >= 40
                    && Math.abs(p.getY() - p2.getY()) >= 15) {
                tiles[p.getX()][p.getY()] = Tileset.LOCKED_DOOR;
                tiles[p2.getX()][p2.getY()] = Tileset.PLAYER;
                lockedDoor = p;
                player = p2;
                break;
            }
        }
    }

    /**
     * The following method is to add some elements in the world.
     * In each room, add a treasure (some are empty, others contain the key).
     * Add the snake and herb randomly in the world.
     */
    private void addTreasure() {
        for (Room room : roomList) {
            int choose = rand.nextInt(5);
            int targetX = RandomUtils.uniform(rand, room.getBottomLeft().getX() + 1,
                    room.getUpperRight().getX());
            int targetY = RandomUtils.uniform(rand, room.getBottomLeft().getY() + 1,
                    room.getUpperRight().getY());
            if (choose == 2) {
                tiles[targetX][targetY] = Tileset.TREASURE;
            } else {
                tiles[targetX][targetY] = Tileset.EMPTYTREASURE;
            }
        }
    }

    private void addSnake() {
        int count = 0;
        while (count < 15) {
            Position p = new Position(RandomUtils.uniform(rand, 1, width - 1),
                    RandomUtils.uniform(rand, 1, height - 1));
            if (tiles[p.getX()][p.getY()] == Tileset.FLOOR) {
                tiles[p.getX()][p.getY()] = Tileset.SNAKE;
                count += 1;
            }
        }
    }

    private void addHerb() {
        int count = 0;
        while (count < 10) {
            Position p = new Position(RandomUtils.uniform(rand, 1, width - 1),
                    RandomUtils.uniform(rand, 1, height - 1));
            if (tiles[p.getX()][p.getY()] == Tileset.FLOOR) {
                tiles[p.getX()][p.getY()] = Tileset.HERB;
                count += 1;
            }
        }
    }


    public void generateWorld() {
        fillWithNothing();
        addRandomRooms();
        roomList.sort(Room::compareTo);
        connectRoomList(roomList);
        addDoorAndPlayer();
        addHerb();
        addTreasure();
        addSnake();
    }
}

