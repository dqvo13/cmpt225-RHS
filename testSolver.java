import rushhour.*;

public class testSolver {
    public static void main(String[] args) {
//        Solver.solveFromFile("badboard1.txt", "S01_out.txt");
//        Solver.solveFromFile("badboard2.txt", "S01_out.txt");
//        Solver.solveFromFile("D33.txt", "D33_out.txt");
//        Solver.solveFromFile("F33.txt", "F33_out.txt");
        /**
         * A puzzles
         */
        for (int i = 0; i <= 10; i++) {
            if (i < 10)
                Solver.solveFromFile("A0" + i + ".txt", "A0" + i + "_out.txt");
            else
                Solver.solveFromFile("A" + i + ".txt", "A" + i + "_out.txt");
        }
        /**
         * B puzzles
         */
        for (int i = 11; i <= 20; i++) {
            Solver.solveFromFile("B" + i + ".txt", "B" + i + "_out.txt");
        }
        /**
         * C puzzles
         */
        for (int i = 21; i <= 29; i++) {
            Solver.solveFromFile("C" + i + ".txt", "C" + i + "_out.txt");
        }
        /**
         * D puzzles
         */
        for (int i = 30; i <= 35; i++) {
            Solver.solveFromFile("D" + i + ".txt", "D" + i + "_out.txt");
        }
        /**
         * S puzzles
         */
        for (int i = 1; i <= 6; i++) {
            Solver.solveFromFile("S0" + i + ".txt", "S0" + i + "_out.txt");
        }

    }
}
