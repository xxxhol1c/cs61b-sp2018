package lab11.graphs;

import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int targetX;
    private int targetY;

    private class Node implements Comparable<Node> {
        private int v;
        private int priority;

        private Node(int v) {
            this.v = v;
            this.priority = distTo[v] + h(v);
        }

        @Override
        public int compareTo(Node node) {
            return this.priority - node.priority;
        }
    }


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        this.targetX = targetX;
        this.targetY = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        PriorityQueue<Node> pq = new PriorityQueue<>(maze.V());
        Node cur = new Node(s);
        pq.add(cur);
        marked[s] = true;
        while (!pq.isEmpty()) {
            Node first = pq.poll();
            for (int w : maze.adj(first.v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = first.v;
                    distTo[w] = distTo[first.v] + 1;
                    announce();
                    if (w == t) {
                        return;
                    } else {
                        pq.add(new Node(w));
                    }
                }
            }
        }

    }

    @Override
    public void solve() {
        astar(s);
    }

}

