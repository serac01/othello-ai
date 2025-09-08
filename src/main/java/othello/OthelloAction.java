package main.java.othello;

/**
 * This class represents a 'move' in a game.
 *
 * @author Henrik Bj&ouml; rklund; serac01; josigabor
 */
public class OthelloAction {
    protected int row = -1;
    protected int column = -1;
    protected int estimatedValue = 0;
    protected boolean pass = false;

    public OthelloAction(int row, int column) {
        this.row = row;
        this.column = column;
        this.estimatedValue = 0;
    }

    public OthelloAction(int row, int column, boolean pass) {
        this.row = row;
        this.column = column;
        this.estimatedValue = 0;
        this.pass = pass;
    }

    public OthelloAction(String s) {
        if (s.equals("pass")) {
            this.row = 0;
            this.column = 0;
            this.estimatedValue = 0;
            this.pass = true;
        } else {
            this.row = Character.getNumericValue(s.charAt(1));
            this.column = Character.getNumericValue(s.charAt(3));
            this.estimatedValue = 0;
        }
    }

    public void setEstimatedValue(int estimatedValue) {
        this.estimatedValue = estimatedValue;
    }
    public int getEstimatedValue() {
        return this.estimatedValue;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    public int getColumn() {
        return this.column;
    }

    public void setRow(int row) {
        this.row = row;
    }
    public int getRow() {
        return this.row;
    }

    public void setPassMove(boolean pass) {
        this.pass = pass;
    }
    public boolean isPassMove() {
        return this.pass;
    }

    public void print() { System.out.println(pass ? "pass" : "(" + row + "," + column + ")"); }

}
