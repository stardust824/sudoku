//Date: Feb 23rd 2025


public class Sudoku {

    public static boolean check(char[][] puzzle) {
        char[] theRow = new char[9];
        //calls check row for each row
        for (int rows = 0; rows < 9; rows ++){
            for(int columns = 0; columns < 9; columns ++){
                //makes an 1D array of the row we are on
                theRow[columns] = puzzle[rows][columns];
            }
            if(checkArray(theRow) == false) {
                return false;
            }
        }
        char[] theColumn = new char[9];
        for (int rows = 0; rows < 9; rows++) {
            for(int columns = 0; columns < 9; columns++){
                theColumn[columns] = puzzle[columns][rows];
            }
            if(checkArray(theColumn) == false){
                return false;
            }
        }
        //box 1
        if(checkBox(0,0, puzzle) == false){
            return false;
        }
        //box 2
        if(checkBox(0, 3, puzzle) == false) {
            return false;
        }
        //box 3
       if(checkBox(0, 6, puzzle) == false) {
           return false;
       }
       //box 4
        if(checkBox(3, 0, puzzle) == false){
            return false;
        }
        //box 5
        if(checkBox(3, 3, puzzle) == false){
            return false;
        }
        //box 6
        if(checkBox(3, 6, puzzle) == false){
            return false;
        }
        //box 7
        if(checkBox(6, 0, puzzle) == false){
            return false;
        }
        //box 8
        if(checkBox(6, 3, puzzle) == false){
            return false;
        }
        //box 9
        if(checkBox(6, 6, puzzle) == false){
            return false;
        }
        return true;
    }

    public static boolean checkArray(char[] array) {
        //make a temporary list
        char [] temp = new char[9];
        //loop through the row
        for(int item = 0; item < 9; item++){
            //loops through temporary list
            for (int tempItem = 0; tempItem < 9; tempItem++){
                //checks if the index is "empty". If it isn't it checks if the value is already in the array
                if(array[item] != '.') {
                    //check each item in the row list to see if it is equal to one of the values in the temporary list
                    //if equal, returns false because there's 2 of a number in a row
                    if (array[item] == temp[tempItem]) {
                        return false;
                    }
                }
                else{
                    break;
                }
                //it adds the empty cells with '.' to the temp array this is important. Because it needs
                //to check if a cell is empty that way earlier in the code. Do not remove
            }
            temp[item] = array[item];
        }
        return true;
    }

    //accepts the starting indices of the row and column as arguments
    public static boolean checkBox(int rowIndex, int columnIndex, char[][] puzzle){
        //make an array of the box & then run check array on each
        //index for the box array since I couldn't figure out how else to implement it
        int boxArrayIndex = 0;
        char[] boxArray = new char[9];
        //makes a box array
        for(int rows = rowIndex; rows < rowIndex + 3; rows++){
            for(int columns = columnIndex; columns < columnIndex + 3; columns++){
                boxArray[boxArrayIndex] = puzzle[rows][columns];
                boxArrayIndex ++;
            }
        }
        return(checkArray(boxArray));
    }
    public static void printBoard(char[][] puzzle){
        int columnPrint = 1;
        for (int rows = 0; rows < 9; rows ++) {
            for(int columns = 0; columns < 9; columns++) {
                System.out.print(puzzle[rows][columns]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
    //overloading
    public static void printBoard(Cell[][] puzzle){
        int columnPrint = 1;
        for (int rows = 0; rows < 9; rows ++) {
            for(int columns = 0; columns < 9; columns++) {
                System.out.print(puzzle[rows][columns].getValue());
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
    public static void printBoardTest(char[][] puzzle){
        //has to be one, so that the columns print normal
        int columnPrint = 1;
        for (int rows = 0; rows < 9; rows ++) {
            if(rows % 3 == 0){
                //prints a line every 3 rows
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.print("┃");
            }
            for(int columns = 0; columns < 9; columns++) {
                System.out.print(puzzle[rows][columns]);
                //adds in spacing so it actually looks like a box
                if(columnPrint % 3 != 0) {
                    System.out.print("  ");
                }

                if(columnPrint % 3 == 0 ){
                    //makes prints a ┃ every 3 columns to make the sudoku box
                    System.out.print("┃");
                }
                columnPrint++;
            }
            //prints the first ┃ of each row
            System.out.println("");
            System.out.print("┃");
        }
        //prints line after last row
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    public static boolean solve(char[][] puzzle) {
        if(check(puzzle) == false){
            System.out.println("\"The given Sudoku puzzle is invalid\"");
            return false;
        }
        else {
            //constructs all the cells with loops and initializes the cell possible values
            Cell[][] cellArray = setUpCells(puzzle);
            //puts nums in boxes method call
            //while at least one cell has been changed, it keeps going
            boolean keepGoing = true;
            while (keepGoing) {
                keepGoing = putNumsInPuzzle(cellArray);
            }
             boolean tOrF = finalSteps(cellArray);
            //if final steps is true, it will print the board itself
            if(tOrF == true){
                return true;
            }
            else{
                System.out.println("\"This puzzle is unsolvable\"");
                return false;
            }
        }
    }

    public static Cell[][] setUpCells(char[][] puzzle){
        Cell[][] cells = new Cell[9][9];
        //stores cell values in cell array
        for (int rows = 0; rows < 9; rows++) {
            for(int columns = 0; columns < 9; columns++) {
                cells[rows][columns] = new Cell(rows, columns, puzzle[rows][columns]);
            }
        }
        //sets what values a cell can be
        updateCells(cells);
        return cells;
    }

    public static boolean putNumsInPuzzle(Cell[][] cellArray){
        boolean cellsChanged = false;
        //row doesn't need to be initialized because of the way it works. See row logic below
        Cell[] theRow;
        Cell[] theColumn = new Cell[9];
        Cell[] theBox = new Cell[9];

        for(int rows = 0; rows < 9; rows++){
            //row logic
            theRow = cellArray[rows];
            boolean rowChanged = putNumsInArray(theRow, cellArray);
            if(rowChanged){
                cellsChanged = true;
            }
            for(int columns = 0; columns < 9; columns++) {
                //makes the column array
                theColumn[columns] = cellArray[columns][rows];

                //cell loop checks if there is one possible value, if so, sets the cell to that value, and changes
                //the number of possible values by -1 (which should equal 0)
                if (cellArray[rows][columns].getNumPossible() == 1 && cellArray[rows][columns].getValue() == '.') {
                    //set up loop to index through the list of possible values and set the one that is true to its value
                    boolean[] possibleValues = cellArray[rows][columns].getPossibleValues();
                    //iterates through the list of possible values. Only one should be true.
                    for (int i = 0; i < possibleValues.length; i++) {
                        if (possibleValues[i] == true) {
                            //sets the value of the cell
                            char value = Character.forDigit((i + 1), 10);
                                cellArray[rows][columns].setValue(value);
                                cellsChanged = true;
                                //updates surrounding cells and tells them they can't have that value >:\
                                updateCells(cellArray);
                                possibleValues = cellArray[rows][columns].getPossibleValues();
                        }
                    }
                }
            }
            //column logic
            boolean columnChanged = putNumsInArray(theColumn, cellArray);
            if(columnChanged){
                cellsChanged = true;
            }
        }
        //box logic
        for(int box = 0; box < 9; box++){
            int boxRow = ((box / 3) * 3);
            int boxColumn = ((box % 3) * 3);
            for(int cell = 0; cell < 9; cell++){
                int cellRow = (cell / 3);
                int cellColumn = (cell % 3);
                theBox[cell] = cellArray[boxRow + cellRow][boxColumn + cellColumn];
            }
            boolean boxChanged = putNumsInArray(theBox, cellArray);
            if(boxChanged){
                cellsChanged = true;
            }
        }
        return cellsChanged;
    }

    public static boolean putNumsInArray(Cell[] theArray, Cell[][] puzzle) {
        boolean changed = false;
        boolean[] possibleValues = {true, true, true, true, true, true, true, true, true};
        //sets up the boolean array of possible values
        for (int i = 0; i < 9; i++) {
            if(theArray[i].getValue() != '.') {
             int value = Character.digit(theArray[i].getValue(), 10);
                possibleValues[value - 1] = false;
            }
        }
        //indexes through possible Array values
        for(int j = 0; j < 9; j++){
            int numCellsCanHaveJ = 0;
            Cell cellCanHaveJ = null;
            //if the row can have the value j,
            if(possibleValues[j] == true){
                //check each cell in the row to see if the cell can have j.
                for(int cell = 0; cell < 9; cell++){
                    //gets possible values of the cell
                    boolean [] cellPossibleValues = theArray[cell].getPossibleValues();
                    //if the cell can have j
                    if (cellPossibleValues[j] == true) {
                        numCellsCanHaveJ++;
                        cellCanHaveJ = theArray[cell];
                    }
                }
                if (numCellsCanHaveJ == 1){
                    cellCanHaveJ.setValue(Character.forDigit((j + 1), 10));
                    changed = true;
                    updateCells(puzzle);
                    //updates the boolean array of possible values
                    for (int i = 0; i < 9; i++) {
                        if(theArray[i].getValue() != '.') {
                            int value = Character.digit(theArray[i].getValue(), 10);
                            possibleValues[value - 1] = false;
                        }
                    }
                }
            }
        }
        return changed;
    }

    //updates the surrounding cells. in rows, columns, and boxes
    public static void updateCells(Cell[][] cells){
        for (int rows = 0; rows < 9; rows++) {
            for(int columns = 0; columns < 9; columns++) {
                if(cells[rows][columns].getValue() != '.') {
                    //makes value an int
                    int value = Character.getNumericValue(cells[rows][columns].getValue());
                    for(int i = 0; i < 9; i++){
                        //indexes it with 0-8 because that's how it works
                        // sets the rows, columns, and boxes the cells is in to false with value
                        //uses value to index the possibleValues to set it to false and change the number of possible values
                        cells[i][columns].setIfPossible(value, false);
                        cells[rows][i].setIfPossible(value, false);
                        //box logic
                        //rows = row-row%3 + i/3  columns = columns - columns%3 + i%3
                        cells[rows - rows % 3 + i / 3][columns - columns % 3 + i % 3].setIfPossible(value, false);
                    }
                }
            }
        }
    }

    public static boolean finalSteps(Cell[][] cells){
        //get cells with least number of possible values
        int leastPossible = 10;
        //make not empty
        if(emptySpaces(cells) == false){
            printBoard(cells);
            return true;
        }
        Cell cellWithLeast = null;

        for (int rows = 0; rows < 9; rows++){
            for(int columns = 0; columns < 9; columns++){
                if(cells[rows][columns].getNumPossible() < leastPossible && cells[rows][columns].getValue() == '.'){
                    leastPossible = cells[rows][columns].getNumPossible();
                    cellWithLeast = cells[rows][columns];
                }
            }
        }
        if (leastPossible == 0){
            if (emptySpaces(cells)) {
                return false;
            }
            else{
                printBoard(cells);
                return true;
            }
        }
        if (leastPossible == 1){
            //run until not valid and then call this method again
            boolean keepGoing = true;
            while (keepGoing) {
                keepGoing = putNumsInPuzzle(cells);
            }
            return(finalSteps(cells));
        }
        else{
            //make list of possible values of the cellWithLeast
            //make a deepcopy of the cell array
            //call this method with the deepcopy of the cell array
            boolean [] booleanArray = cellWithLeast.getPossibleValues();
            boolean stop;
            //loops through all the possible values and tries them with recursion
            for(int i = 0; i < 9; i++){
                //remember that index of booleanArray starts at 0.
                if(booleanArray[i] == true){
                    //turns guessValue into a character & adds 1 to it, so it's the right number
                    char guessValue = Character.forDigit((i + 1), 10);
                    Cell[][] deepCopy = deepCopy(cells);
                    //sets deepcopy cell to guessValue. The deepcopy updates the cells itself
                    deepCopy[cellWithLeast.getRow()][cellWithLeast.getColumn()].setValue(guessValue);
                    updateCells(deepCopy);
                    //call this function w/ recursion
                    stop = finalSteps(deepCopy);
                    if(stop == true){
                       return true;
                    }
                }
            }
            return false;
        }
    }

    public static Cell[][] deepCopy(Cell[][] cells){
        Cell[][] copy = new Cell[9][9];
        for(int rows = 0; rows < 9; rows++){
            for(int columns = 0; columns < 9; columns++){
                copy[rows][columns] = new Cell(rows, columns, cells[rows][columns].getValue());
            }
        }
        updateCells(copy);
        return copy;
    }

    public static boolean emptySpaces(Cell[][] cells){
        boolean emptySpaces = false;
        for (int rows = 0; rows < 9; rows++){
            for(int columns = 0; columns < 9; columns++){
                if(cells[rows][columns].getValue() == '.'){
                    emptySpaces = true;
                }
            }
        }
        return emptySpaces;
    }
}


