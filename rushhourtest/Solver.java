package rushhourtest;

import rushhour.Board;
import rushhour.Car;

import java.io.File;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

public class Solver {
    private Board currentBoard;
//    private boolean isSolved;
//    private ArrayList<Board> boardStack;
    private LinkedList<Board> boardQueue;
//    private PriorityQueue<Board> boardQueue;
//    private Comparator<Board> boardComparator;
    private HashSet<Integer> seenBoards;

    /**
     * comparator for priority queue
     * for use with heuristic cost of boards
     */
//    private class BoardComparator implements Comparator<Board> {
//        @Override
//        public int compare(Board x, Board y) {
//            return x.getHeurCost() - y.getHeurCost();
//        }
//    }

    /**
     * constructor for stack of boards
     * @param filename
     */
    public Solver(String filename) {
//        isSolved = false;
        currentBoard = new Board(filename);
//        boardStack = new ArrayList<Board>();
//        boardStack.add(currentBoard);
        boardQueue = new LinkedList<Board>();
//        boardComparator = new BoardComparator();
//        boardQueue = new PriorityQueue<Board>(boardComparator);
        boardQueue.addLast(currentBoard);
        seenBoards = new HashSet<Integer>();
        seenBoards.add(currentBoard.getHashNum());
    }

    /**
     * check if xCar is free to reach goal
     * @return true if there is a free path, false otherwise
     */
    private boolean canSolve() {
        Car xCar = this.currentBoard.getCar('X');
        int searchY = xCar.getY();

        for (int i = xCar.getX() + xCar.getLength(); i < 6; i++) {
            if (this.currentBoard.getCharBoard()[searchY][i] != '.')
                return false;
        }
        return true;
    }

    /**
     * check if xCar is already at goal
     * @return true if so, false otherwise
     */
    private boolean isSolved() {
        Car xCar = this.currentBoard.getCar('X');
        return xCar.getX() == 4;
    }

    private void findAllNext() {
        // make all possible moves w/ all cars, add to stack
        for (Car c : this.currentBoard.getListOfCars()) {
            Board tempFor = new Board(this.currentBoard);      // deep copy of board
            Car carCpyFor = tempFor.getCar(c.getName());       // car in currentBoard as same car in temp

            tempFor = tempFor.attemptMove(tempFor.getCar(c.getName()), Board.FORWARD);   // attempt move forwards
            if (tempFor != null && !(this.seenBoards.contains(tempFor.getHashNum()))) {
//                this.boardStack.add(tempFor);
                this.boardQueue.addLast(tempFor);
//                this.boardQueue.add(tempFor);
                this.seenBoards.add(tempFor.getHashNum());
            }

            Board tempBack = new Board(this.currentBoard);                  // recreate deep copy
            Car carCpyBack = tempBack.getCar(c.getName());
            tempBack = tempBack.attemptMove(tempBack.getCar(c.getName()), Board.BACKWARD); // attempt move backwards
            if (tempBack != null && !(this.seenBoards.contains(tempBack.getHashNum()))) {
//                this.boardStack.add(tempBack);
                this.boardQueue.addLast(tempBack);
//                this.boardQueue.add(tempBack);
                this.seenBoards.add(tempBack.getHashNum());
            }
        }
    }

    public static void solveFromFile(String input, String output) {
        Solver solution = new Solver(input);

        while (!solution.canSolve() && !(solution.boardQueue.isEmpty())) {
            // remove board from queue
            solution.currentBoard = solution.boardQueue.removeFirst();
            // remove board from priority queue
//            solution.currentBoard = solution.boardQueue.remove();
            solution.findAllNext();
        }

        /**
         * xCar must have free path,
         * move the car over to the goal
         */
        while (!solution.isSolved()) {
            Car xCar = solution.currentBoard.getCar('X');
            solution.currentBoard = solution.currentBoard.attemptMove(xCar, Board.FORWARD);
        }

        /**
         * take currentBoard, output list of moves to get there
         * write to output file
         */
        try {
            File fout = new File(output);
            PrintWriter boardWriter = new PrintWriter(fout);
            Board.printMovesToFile(boardWriter, solution.currentBoard.getLastMoveNode());
            boardWriter.flush();
            boardWriter.close();
        } catch (Exception e) {
            System.out.println("file writing error");
        }

//        /**
//         * take currentBoard, print list of moves to get there
//         */
//        Board.printMovesToScreen(solution.currentBoard.getLastMoveNode());

        /**
         * print board
         */
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                System.out.printf(solution.currentBoard.getCharBoard()[i][j] + " ");
            }
            System.out.println();
        }
    }
}
