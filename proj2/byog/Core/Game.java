package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Game implements Serializable {
    /* Feel free to change the WIDTH and HEIGHT.*/
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final int TERWIDTH = 80;
    private static final int TERHEIGHT = 31;
    private boolean gameOver = false;
    private static String settlement = "";
    private String keys = "";
    private StringBuilder health = new StringBuilder("♥ ♥ ♥ ♥ ♥");
    private char mode;
    private final TERenderer ter = new TERenderer();
    private World createdWorld;


    /**
     * Method used for playing a fresh game.
     * The game should start from the main menu.
     */
    public void playWithKeyboard() {
        renderMenu();
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char choice = StdDraw.nextKeyTyped();
            switch (choice) {
                case 'n':
                case 'N':
                case 'd':
                case 'D':
                    newGame(choice);
                    break;
                case 'l':
                case 'L':
                    loadGame();
                    break;
                case 'q':
                case 'Q':
                    quitGame();
                    break;
                default:
            }
        }
    }

    /**
     * Get the input seed and generate the particular world.
     *
     * @param selectedMode determine the game mode (the render method)
     *                     and help load the saved game later.
     */
    private void newGame(char selectedMode) {
        long seed = inputSeed(selectedMode);
        World newWorld = new World(WIDTH, HEIGHT, seed);
        ter.initialize(TERWIDTH, TERHEIGHT);
        newWorld.generateWorld();
        createdWorld = newWorld;
        mode = selectedMode;
        startGame(selectedMode);
    }

    private void loadGame() {
        Game game = getSavedGame();
        this.mode = game.mode;
        this.health = game.health;
        this.createdWorld = game.createdWorld;
        this.keys = game.keys;
        this.ter.initialize(TERWIDTH, TERHEIGHT);
        this.startGame(mode);
    }

    private void quitGame() {
        System.exit(1);
    }

    private void saveGame() {
        File f = new File("./Game.txt");
        try {
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private static Game getSavedGame() {
        File f = new File("./Game.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (Game) os.readObject();
            } catch (FileNotFoundException e) {
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return null;
    }

    /* make sure the key typed is digit*/
    private long inputSeed(char selectedMode) {
        StringBuilder seedBuilder = new StringBuilder();
        renderSeedMenu(selectedMode);
        while (true) {
            StdDraw.enableDoubleBuffering();
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char pressed = StdDraw.nextKeyTyped();
            if (pressed >= 48 && pressed <= 57) {
                seedBuilder.append(pressed);
            } else if (pressed == 's' || pressed == 'S') {
                break;
            }
            renderSeedMenu(selectedMode);
            StdDraw.text(0.5, 0.45,
                    "Your seed is: " + seedBuilder);
            StdDraw.show();
        }
        String seedString = seedBuilder.toString();
        return Long.parseLong(seedString);
    }

    /**
     * The basic skeleton to play with the game.
     * Get the pressed key by user and record it (determine whether is to save the game).
     * Render the status bar to show the present condition of the player.
     * Make some condition to determine whether game is over.
     * If not over, interact with the pressed key in the while - loop,
     * and render the returned world.
     *
     * @param selectedMode determine the method to render
     */
    private void startGame(char selectedMode) {
        char key;
        String keyPressed = "";
        while (!gameOver) {
            if (selectedMode == 'n' || selectedMode == 'N') {
                ter.renderWorld(createdWorld);
            } else if (selectedMode == 'd' || selectedMode == 'D') {
                ter.renderLight(createdWorld);
            }
            int left = 10;
            StdDraw.text(left, HEIGHT, "Health: " + health);
            int right = 70;
            StdDraw.text(right, HEIGHT, "Keys:" + keys);
            StdDraw.show();
            Position lD = createdWorld.getLockedDoor();
            if (createdWorld.getTiles()[lD.getX()][lD.getY()].equals(Tileset.PLAYER)) {
                settlement = "Congratulations! You escaped from here!";
                gameOver = true;
            }
            if (health.charAt(0) == '♡') {
                settlement = "You died, try next time.";
                gameOver = true;
            }
            for (int i = 0; i < keyPressed.length() - 1; i += 1) {
                if ((keyPressed.charAt(i) == ':' && keyPressed.charAt(i + 1) == 'q')
                        || (keyPressed.charAt(i) == ':' && keyPressed.charAt(i + 1) == 'Q')) {
                    saveGame();
                    settlement = "Your game has been saved!";
                    gameOver = true;
                }
            }
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            key = StdDraw.nextKeyTyped();
            keyPressed += key;
            createdWorld = interact(key);
        }
        renderSettlementMenu(settlement);
        StdDraw.pause(2000);
        System.exit(1);
    }

    /**
     * Interact method.
     * Implement the different movement with the given key.
     *
     * @param key the pressed key
     * @return record the new world and return it to help it iterate in
     * the while - loop.
     */
    private World interact(char key) {
        Position player = createdWorld.getPlayer();
        switch (key) {
            case 'a':
            case 'A':
                player.moveLeft(createdWorld);
                break;
            case 'd':
            case 'D':
                player.moveRight(createdWorld);
                break;
            case 'w':
            case 'W':
                player.moveUp(createdWorld);
                break;
            case 's':
            case 'S':
                player.moveDown(createdWorld);
                break;
            case 'o':
            case 'O':
                openDoor(createdWorld, player);
                getKey();
                break;
            default:
        }
        changeHealth(createdWorld, player);
        return createdWorld;
    }

    private void changeHealth(World w, Position player) {
        if (w.getTiles()[player.getX()][player.getY()].equals(Tileset.ATTACKED)) {
            health.delete(0, 2);
            health.append(" ♡");
        } else if (w.getTiles()[player.getX()][player.getY()].equals(Tileset.HEALED)) {
            if (health.charAt(8) != '♥') {
                int index = health.indexOf("♡");
                health.replace(index, index + 1, "♥");
            }
        }
    }

    private void openDoor(World w, Position player) {
        if (keys.length() >= 6) {
            Position lockedDoor = w.getLockedDoor();
            Position left = new Position(player.getX() - 1, player.getY());
            Position right = new Position(player.getX() + 1, player.getY());
            Position up = new Position(player.getX(), player.getY() + 1);
            Position down = new Position(player.getX(), player.getY() - 1);
            if (w.getTiles()[left.getX()][left.getY()].equals(Tileset.LOCKED_DOOR)
                    || w.getTiles()[right.getX()][right.getY()].equals(Tileset.LOCKED_DOOR)
                    || w.getTiles()[up.getX()][up.getY()].equals(Tileset.LOCKED_DOOR)
                    || w.getTiles()[down.getX()][down.getY()].equals(Tileset.LOCKED_DOOR)) {
                w.getTiles()[lockedDoor.getX()][(lockedDoor.getY())] = Tileset.UNLOCKED_DOOR;
            }
        }
    }

    private void getKey() {
        World w = createdWorld;
        Position player = w.getPlayer();
        Position leftPos = new Position(player.getX() - 1, player.getY());
        Position rightPos = new Position(player.getX() + 1, player.getY());
        Position downPos = new Position(player.getX(), player.getY() - 1);
        Position upPos = new Position(player.getX(), player.getY() + 1);
        if (w.getTiles()[leftPos.getX()][leftPos.getY()].equals(Tileset.TREASURE)
                || w.getTiles()[leftPos.getX()][leftPos.getY()].equals(Tileset.EMPTYTREASURE)) {
            if (w.getTiles()[leftPos.getX()][leftPos.getY()].equals(Tileset.TREASURE)) {
                keys += " ⥉";
            }
            w.getTiles()[leftPos.getX()][leftPos.getY()] = Tileset.OPENEDTREASURE;
        } else if (w.getTiles()[rightPos.getX()][rightPos.getY()].equals(Tileset.TREASURE)
                || w.getTiles()[rightPos.getX()][rightPos.getY()].equals(Tileset.EMPTYTREASURE)) {
            if (w.getTiles()[rightPos.getX()][rightPos.getY()].equals(Tileset.TREASURE)) {
                keys += " ⥉";
            }
            w.getTiles()[rightPos.getX()][rightPos.getY()] = Tileset.OPENEDTREASURE;
        } else if (w.getTiles()[upPos.getX()][upPos.getY()].equals(Tileset.TREASURE)
                || w.getTiles()[upPos.getX()][upPos.getY()].equals(Tileset.EMPTYTREASURE)) {
            if (w.getTiles()[upPos.getX()][upPos.getY()].equals(Tileset.TREASURE)) {
                keys += " ⥉";
            }
            w.getTiles()[upPos.getX()][upPos.getY()] = Tileset.OPENEDTREASURE;
        } else if (w.getTiles()[downPos.getX()][downPos.getY()].equals(Tileset.TREASURE)
                || w.getTiles()[downPos.getX()][downPos.getY()].equals(Tileset.EMPTYTREASURE)) {
            if (w.getTiles()[downPos.getX()][downPos.getY()].equals(Tileset.TREASURE)) {
                keys += " ⥉";
            }
            w.getTiles()[downPos.getX()][downPos.getY()] = Tileset.OPENEDTREASURE;
        }
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] playWithInputString(String input) {
        /* DONE: Fill out this method to run the game using the input passed in,
         * and return a 2D tile representation of the world that would have been
         * drawn if the same inputs had been given to playWithKeyboard().*/
        char choose = input.charAt(0);
        mode = choose;
        return startWithInput(input, choose);
    }

    private TETile[][] startWithInput(String input, char choose) {
        if (choose == 'N' || choose == 'n') {
            long seed = inputToLong(input);
            World newWorld = new World(WIDTH, HEIGHT, seed);
            newWorld.generateWorld();
            createdWorld = newWorld;
            int start = 1;
            for (int i = 0; i < input.length(); i += 1) {
                if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                    start = i + 1;
                    break;
                }
            }
            enterWithInput(start, input);
            return createdWorld.getTiles();
        } else if (choose == 'l' || choose == 'L') {
            Game game = getSavedGame();
            this.createdWorld = game.createdWorld;
            enterWithInput(1, input);
            return createdWorld.getTiles();
        } else if (choose == 'q' || choose == 'Q') {
            return null;
        }
        return null;
    }

    private void enterWithInput(int startIndex, String input) {
        for (int i = startIndex; i < input.length(); i += 1) {
            if ((input.charAt(i) == ':' && input.charAt(i + 1) == 'q')
                    || (input.charAt(i) == ':' && input.charAt(i + 1) == 'Q')) {
                saveGame();
                System.out.println("Your game has been saved!");
                break;
            }
            createdWorld = interact(input.charAt(i));
        }
    }

    private static long inputToLong(String input) {
        StringBuilder seedBuilder = new StringBuilder();
        for (int i = 0; i < input.length(); i += 1) {
            if (input.charAt(i) >= 48 && input.charAt(i) <= 57) {
                seedBuilder.append(input.charAt(i));
            } else if (input.charAt(i) == 's'
                    || input.charAt(i) == 'S') {
                break;
            }
        }
        return Long.parseLong(seedBuilder.toString());
    }

    private void renderSeedMenu(char selectedMode) {
        StdDraw.clear(Color.black);
        StdDraw.setFont(new Font("Chalkduster", Font.PLAIN, 30));
        if (selectedMode == 'd' || selectedMode == 'D') {
            StdDraw.text(0.5, 0.9, "Tips: you choose difficult mode,");
            StdDraw.text(0.5, 0.85, " your sight is restricted.");
            StdDraw.text(0.5, 0.75, "Control: press W, A, S, D to move,");
            StdDraw.text(0.5, 0.7, " press O to open.");
            StdDraw.text(0.5, 0.6, "Please enter a random seed, ");
            StdDraw.text(0.5, 0.55, " then press 's' to start.");
        } else if (selectedMode == 'n' || selectedMode == 'N') {
            StdDraw.text(0.5, 0.85, "Tips: you choose normal mode.");
            StdDraw.text(0.5, 0.75, "Control: press W, A, S, D to move,");
            StdDraw.text(0.5, 0.7, " press O to open.");
            StdDraw.text(0.5, 0.6, "Please enter a random seed, ");
            StdDraw.text(0.5, 0.55, " then press 's' to start.");
        }
    }

    private void renderMenu() {
        StdDraw.setCanvasSize(WIDTH * 10, HEIGHT * 30);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font fontTitle = new Font("Chalkduster", Font.BOLD, 40);
        Font fontMenu = new Font("Chalkduster", Font.PLAIN, 25);
        StdDraw.setFont(fontTitle);
        StdDraw.text(0.5, 0.8, "CS61B Game: BYoG");
        StdDraw.setFont(fontMenu);
        StdDraw.text(0.5, 0.6, "Normal Mode (N)");
        StdDraw.text(0.5, 0.5, "Difficult Mode (D)");
        StdDraw.text(0.5, 0.4, "Load Game (L)");
        StdDraw.text(0.5, 0.3, "Quit (Q)");
    }

    private void renderSettlementMenu(String s) {
        StdDraw.setCanvasSize(WIDTH * 10, HEIGHT * 30);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        Font fontTitle = new Font("Chalkduster", Font.BOLD, 25);
        StdDraw.setFont(fontTitle);
        StdDraw.text(0.5, 0.5, s);
        StdDraw.show();
    }
}
