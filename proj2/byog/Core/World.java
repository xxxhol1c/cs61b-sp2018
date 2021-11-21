
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
    protected final long seed;
    protected final TETile[][] tiles;
    private ArrayList<Room> roomList;
    private final int maxRoomNum = 30;
    private final Random rand;
    protected Position player;
    protected Position lockedDoor;

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

    public Position getPlayer() {
        return player;
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
        return r.bottomLeft.xP >= 0 && r.bottomLeft.xP <= width - 1
                && r.upperRight.xP >= 0 && r.upperRight.xP < width - 1
                && r.bottomLeft.yP >= 0 && r.bottomLeft.yP <= height - 1
                && r.upperRight.yP >= 0 && r.upperRight.yP < height - 1;
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
        for (int i = r.bottomLeft.xP; i <= r.upperRight.xP; i += 1) {
            for (int j = r.bottomLeft.yP; j <= r.upperRight.yP; j += 1) {
                if (this.tiles[i][j] != Tileset.FLOOR) {
                    if (i == r.bottomLeft.xP
                            || i == r.upperRight.xP
                            || j == r.bottomLeft.yP
                            || j == r.upperRight.yP) {
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
        Position bl = new Position(s.xP - 1, s.yP - 1);
        Position ur = new Position(e.xP + 1, e.yP + 1);
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
                Position corner = new Position(e.xP, s.yP);
                Room parallelRoom = addParallelHallway(s, corner);
                Room verticalRoom = addVerticalHallway(corner, e);
                constructRoom(parallelRoom);
                constructRoom(verticalRoom);
                break;
            }
            case 1: {
                Position corner = new Position(s.xP, e.yP);
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
            if (((tiles[p.xP - 1][p.yP] == Tileset.NOTHING
                    && tiles[p.xP + 1][p.yP] == Tileset.FLOOR)
                    || (tiles[p.xP + 1][p.yP] == Tileset.NOTHING
                    && tiles[p.xP - 1][p.yP] == Tileset.FLOOR)
                    || (tiles[p.xP][p.yP - 1] == Tileset.NOTHING
                    && tiles[p.xP][p.yP + 1] == Tileset.FLOOR)
                    || (tiles[p.xP][p.yP + 1] == Tileset.NOTHING
                    && tiles[p.xP][p.yP - 1] == Tileset.FLOOR))
                    && (tiles[p2.xP][p2.yP]) == Tileset.FLOOR
                    && Math.abs(p.xP - p2.xP) >= 40
                    && Math.abs(p.yP - p2.yP) >= 15) {
                tiles[p.xP][p.yP] = Tileset.LOCKED_DOOR;
                tiles[p2.xP][p2.yP] = Tileset.PLAYER;
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
            int targetX = RandomUtils.uniform(rand, room.bottomLeft.xP + 1,
                    room.upperRight.xP);
            int targetY = RandomUtils.uniform(rand, room.bottomLeft.yP + 1,
                    room.upperRight.yP);
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
            if (tiles[p.xP][p.yP] == Tileset.FLOOR) {
                tiles[p.xP][p.yP] = Tileset.SNAKE;
                count += 1;
            }
        }
    }

    private void addHerb() {
        int count = 0;
        while (count < 10) {
            Position p = new Position(RandomUtils.uniform(rand, 1, width - 1),
                    RandomUtils.uniform(rand, 1, height - 1));
            if (tiles[p.xP][p.yP] == Tileset.FLOOR) {
                tiles[p.xP][p.yP] = Tileset.HERB;
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

