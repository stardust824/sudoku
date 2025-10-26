// Currently main solves a partially solved sudoku board
// It can be changed to solve an empty or different board

public class Main {
    public static void main(String[] args) {
        char[][]puzzle = {{'.', '.', '.', '.', '3', '5', '.', '7', '.'}, {'2', '5', '.', '.', '4', '6', '8', '.', '1'}, {'.', '1', '3', '7', '.', '8', '.', '4', '9'}, {'1', '9', '.', '.', '.', '7', '.', '.', '4'}, {'.', '.', '5', '.', '.', '2', '.', '9', '6'}, {'8', '.', '2', '.', '9', '4', '.', '.', '7'}, {'3', '7', '.', '.', '.', '9', '.', '.', '.'}, {'.', '6', '1', '.', '7', '.', '.', '.', '.'}, {'4', '.', '.', '5', '8', '1', '.', '.', '.'}};
        Sudoku.printBoardTest(puzzle);
        System.out.println(Sudoku.solve(puzzle));
    }
}
