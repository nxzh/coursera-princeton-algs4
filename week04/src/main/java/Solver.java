import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private final Board board;
    private int moves = -1;
    private Queue<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board is null");
        }
        this.board = initial;
        solve();
    }

    private Comparator<Board> hammingComparator() {
        return new Comparator<Board>() {
            @Override
            public int compare(Board o1, Board o2) {
//                int priority = o1.hamming() - o2.hamming();
//                if (priority != 0) {
//                    return priority;
//                }
                return o1.manhattan() - o2.manhattan();
            }
        };
    }

    private void solve() {
        MinPQ<Board> boards = new MinPQ<>(hammingComparator());
        boards.insert(this.board);
        Queue<Board> track = new Queue<>();
        Board lastTrack = null;
        while (true) {
            Board minHamming = boards.delMin();
            track.enqueue(minHamming);
            moves++;

            if (minHamming.isGoal()) {
                break;
            }
            if (moves > 0 && (minHamming.equals(this.board) || minHamming.manhattan() == minHamming.manhattan())) {
                moves = -1;
                track = null;
                break;
            }
            lastTrack = minHamming;
            for (Board bd : minHamming.neighbors()) {
                boards.insert(bd);
            }
        }
        solution = track;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution.isEmpty();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
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
}
