package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private WorldState ws;
        private int move = 0;
        private SearchNode pre;

        private SearchNode(WorldState ws, int move, SearchNode pre) {
            this.ws = ws;
            this.move = move;
            this.pre = pre;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.move + this.ws.estimatedDistanceToGoal()
                    - o.move - o.ws.estimatedDistanceToGoal();
        }
    }

    private MinPQ<SearchNode> nextNodes = new MinPQ<>();
    private Stack<WorldState> bestNodes = new Stack<>();
    private int enqueNum;

    public Solver(WorldState initial) {
        SearchNode curNode  = new SearchNode(initial, 0, null);
        nextNodes.insert(curNode);
        while (!nextNodes.isEmpty()) {
            curNode = nextNodes.delMin();
            if (curNode.ws.isGoal()) {
                break;
            }
            for (WorldState neighbour : curNode.ws.neighbors()) {
                if (curNode.pre == null || !neighbour.equals(curNode.pre.ws)) {
                    nextNodes.insert(new SearchNode(neighbour, curNode.move + 1, curNode));
                    enqueNum += 1;
                }
            }
        }
        for (SearchNode n = curNode; n != null; n = n.pre) {
            bestNodes.push(n.ws);
        }
    }

    public int moves() {
        return bestNodes.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return bestNodes;
    }

    /**
     * test for the critical optimization
     */
    int test() {
        return enqueNum;
    }
}
