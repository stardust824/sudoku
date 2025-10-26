public class Cell {
    private int row;
    private int column;
    private char value;
    private boolean[] possibleValues;


    public Cell(int row, int column, char value) {
        this.row = row;
        this.column = column;
        this.value = value;
        if (this.value == '.') {
            //indexing with num-1 returns whether that number can go in this cell
            this.possibleValues = new boolean[]{true, true, true, true, true, true, true, true, true};
        } else {
            this.possibleValues = new boolean[]{false, false, false, false, false, false, false, false, false};
        }
    }

    //getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getValue() {
        return value;
    }

    //returns whether a value is or isn't possible. Includes -1 logic in the getter
    public boolean isValuePossible(int index) {
        return possibleValues[index - 1];
    }

    public int getNumPossible() {
        int numPossible = 0;
        for (int i = 0; i < 9; i++) {
            if (possibleValues[i] == true) {
                numPossible++;
            }
        }
        return numPossible;
    }

    public boolean[] getPossibleValues() {
        return possibleValues;
    }

    //setters
    public void setValue (char value){
        if(this.value != '.'){
            throw new IllegalStateException("Cell is NOT empty");
        }
        this.value = value;
    }

    //changes whether a cell can have a number.
    public void setIfPossible (int index, boolean isPossible){
        possibleValues[index - 1] = isPossible;
    }
}
