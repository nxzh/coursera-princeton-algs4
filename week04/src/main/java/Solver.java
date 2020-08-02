import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private final Board initialBoard;
    private SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board is null");
        }
        this.initialBoard = board;
        solve();
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private Comparator<SearchNode> priority() {
        return new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode n1, SearchNode n2) {
                return (n1.moves + n1.board.manhattan()) - (n2.moves + n2.board.manhattan());
            }
        };
    }

    private boolean isTracked(Queue<Board> boards, Board bd) {
        Iterator<Board> iter = boards.iterator();
        while (iter.hasNext()) {
            Board cur = iter.next();
            if (cur.equals(bd)) {
                return true;
            }
        }
        return false;
    }

    private void solve() {
        Queue<Board> track = new Queue<>();
        MinPQ<SearchNode> pq = new MinPQ<>(priority());
        MinPQ<SearchNode> pqTwin = new MinPQ<>(priority());
        pq.insert(SearchNode.of(initialBoard, 0, null));
        pqTwin.insert(SearchNode.of(initialBoard.twin(), 0, null));
        while (!pq.isEmpty()) {
            SearchNode min = pq.delMin();
            track.enqueue(min.board);
            if (min.board.isGoal()) {
                solution = min;
                break;
            } else {
                for (Board bd : min.board.neighbors()) {
                    if (!isTracked(track, bd)) {
                        track.enqueue(bd);
                        SearchNode cur = SearchNode.of(bd, min.moves + 1, min);
                        pq.insert(cur);
                    }
                }
            }
            SearchNode minTwin = pqTwin.delMin();
            if (minTwin.board.isGoal()) {
                track.enqueue(minTwin.board);
                break;
            } else {
                for (Board bd : minTwin.board.neighbors()) {
                    if (!isTracked(track, bd)) {
                        track.enqueue(bd);
                        SearchNode cur = SearchNode.of(bd, minTwin.moves + 1, minTwin);
                        pqTwin.insert(cur);
                    }
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution == null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution == null ? -1 : solution.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution == null ? null : solution.boards();
    }

    private static class SearchNode {
        Board board;
        int moves;
        SearchNode previous;

        public static SearchNode of(Board current, int moves, SearchNode previous) {
            SearchNode ret = new SearchNode();
            ret.board = current;
            ret.moves = moves;
            ret.previous = previous;
            return ret;
        }

        Iterable<Board> boards() {
            Stack<Board> stk = new Stack<>();
            SearchNode ptr = this;
            while (ptr != null) {
                stk.push(ptr.board);
                ptr = ptr.previous;
            }
            return stk;
        }
    }
}
