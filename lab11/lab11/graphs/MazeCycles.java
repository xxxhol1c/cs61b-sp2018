package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private boolean cycleFound = false;
    private int[] parents;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        parents = new int[maze.V()];
    }

    @Override
    public void solve() {
        // DONE: Your code here!
        dfs(0);
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                parents[w] = v;
                dfs(w);
            } else if (parents[v] != w) {
                edgeTo[w] = v;
                int next = v;
                while (next != w) {
                    edgeTo[next] = parents[next];
                    next = parents[next];
                }
                cycleFound = true;
                announce();
            }
            if (cycleFound) {
                return;
            }
        }
    }
}

