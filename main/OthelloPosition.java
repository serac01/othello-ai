

import java.util.*;

/**
 * This class is used to represent game positions. It uses a 2-dimensional char
 * array for the board and a Boolean to keep track of which player has the move.
 * 
 * @author Henrik Bj&ouml; rklund
 */

public class OthelloPosition {
    protected static final int BOARD_SIZE = 8;
    protected boolean maxPlayerRound;
    protected char[][] board;

    public OthelloPosition() {
        board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
        for (int i = 0; i < BOARD_SIZE + 2; i++)
            for (int j = 0; j < BOARD_SIZE + 2; j++)
                board[i][j] = 'E';
    }

    public OthelloPosition(String s) {
        if (s.length() != 65) {
            board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
            for (int i = 0; i < BOARD_SIZE + 2; i++)
                for (int j = 0; j < BOARD_SIZE + 2; j++)
                    board[i][j] = 'E';
        } else {
            board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
            maxPlayerRound = s.charAt(0) == 'W';
            for (int i = 1; i <= 64; i++) {
                char c;
                if (s.charAt(i) == 'E')
                    c = 'E';
                else if (s.charAt(i) == 'O')
                    c = 'W';
                else 
                    c = 'B';
                board[(i - 1) / 8 + 1][((i - 1) % 8) + 1] = c;
            }
        }

    }

    public void initialize() {
        board[BOARD_SIZE / 2][BOARD_SIZE / 2] = board[BOARD_SIZE / 2 + 1][BOARD_SIZE / 2 + 1] = 'W';
        board[BOARD_SIZE / 2][BOARD_SIZE / 2 + 1] = board[BOARD_SIZE / 2 + 1][BOARD_SIZE / 2] = 'B';
        maxPlayerRound = true;
    }

    public LinkedList<OthelloAction> getAllPossibleMoves(long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        boolean[][] candidates = new boolean[BOARD_SIZE][BOARD_SIZE];
        LinkedList<OthelloAction> moves = new LinkedList<OthelloAction>();
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                candidates[i][j] = isCandidate(i + 1, j + 1, endTime);
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                if (candidates[i][j])
                    if (isPossibleToMove(i + 1, j + 1, endTime))
                        moves.add(new OthelloAction(i + 1, j + 1));
        return moves;
    }

    private boolean isPossibleToMove(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        return checkNorth(row, column, endTime) ||
                checkNorthEast(row, column, endTime) ||
                checkEast(row, column, endTime) ||
                checkSouthEast(row, column, endTime) ||
                checkSouth(row, column, endTime) ||
                checkSouthWest(row, column, endTime) ||
                checkWest(row, column, endTime) ||
                checkNorthWest(row, column, endTime);
    }

    private boolean checkNorth(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isOpponentSquare(row - 1, column, endTime)) return false;
        for (int i = row - 2; i > 0; i--) {
            if (isEmpty(i, column))
                return false;
            if (isOwnSquare(i, column, endTime))
                return true;
        }
        return false;
    }

    private boolean checkEast(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isOpponentSquare(row, column + 1, endTime))
            return false;
        for (int i = column + 2; i <= BOARD_SIZE; i++) {
            if (isEmpty(row, i))
                return false;
            if (isOwnSquare(row, i, endTime))
                return true;
        }
        return false;
    }

    private boolean checkSouth(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isOpponentSquare(row + 1, column, endTime))
            return false;
        for (int i = row + 2; i <= BOARD_SIZE; i++) {
            if (isEmpty(i, column))
                return false;
            if (isOwnSquare(i, column, endTime))
                return true;
        }
        return false;
    }

    private boolean checkWest(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isOpponentSquare(row, column - 1, endTime))
            return false;
        for (int i = column - 2; i > 0; i--) {
            if (isEmpty(row, i))
                return false;
            if (isOwnSquare(row, i, endTime))
                return true;
        }
        return false;
    }

    private boolean checkNorthEast(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isOpponentSquare(row - 1, column + 1, endTime))
            return false;
        for (int i = 2; row - i > 0 && column + i <= BOARD_SIZE; i++) {
            if (isEmpty(row - i, column + i))
                return false;
            if (isOwnSquare(row - i, column + i, endTime))
                return true;
        }
        return false;
    }

    private boolean checkSouthEast(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isOpponentSquare(row + 1, column + 1, endTime))
            return false;
        for (int i = 2; row + i <= BOARD_SIZE && column + i <= BOARD_SIZE; i++) {
            if (isEmpty(row + i, column + i))
                return false;
            if (isOwnSquare(row + i, column + i, endTime))
                return true;
        }
        return false;
    }

    private boolean checkSouthWest(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isOpponentSquare(row + 1, column - 1, endTime))
            return false;
        for (int i = 2; row + i <= BOARD_SIZE && column - i > 0; i++) {
            if (isEmpty(row + i, column - i))
                return false;
            if (isOwnSquare(row + i, column - i, endTime))
                return true;
        }
        return false;
    }

    private boolean checkNorthWest(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isOpponentSquare(row - 1, column - 1, endTime))
            return false;
        for (int i = 2; row - i > 0 && column - i > 0; i++) {
            if (isEmpty(row - i, column - i))
                return false;
            if (isOwnSquare(row - i, column - i, endTime))
                return true;
        }
        return false;
    }

    private boolean isOpponentSquare(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (maxPlayerRound && (board[row][column] == 'B'))
            return true;
        if (!maxPlayerRound && (board[row][column] == 'W'))
            return true;
        return false;
    }

    private boolean isOwnSquare(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!maxPlayerRound && (board[row][column] == 'B'))
            return true;
        if (maxPlayerRound && (board[row][column] == 'W'))
            return true;
        return false;
    }
    
    private boolean isCandidate(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isEmpty(row, column))
            return false;
        return hasNeighbor(row, column, endTime);
    }
    
    private boolean hasNeighbor(int row, int column, long endTime) throws  TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!isEmpty(row - 1, column))
            return true;
        if (!isEmpty(row - 1, column + 1))
            return true;
        if (!isEmpty(row, column + 1))
            return true;
        if (!isEmpty(row + 1, column + 1))
            return true;
        if (!isEmpty(row + 1, column))
            return true;
        if (!isEmpty(row + 1, column - 1))
            return true;
        if (!isEmpty(row, column - 1))
            return true;
        return !isEmpty(row - 1, column - 1);
    }
    
    private boolean isEmpty(int row, int column) { return board[row][column] == 'E'; }

    public boolean toMove() { return maxPlayerRound; }

    public OthelloPosition makeMove(OthelloAction action, long endTime)  throws  IllegalMoveException, TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        OthelloPosition newPos = this.clone(endTime);
        if (action.isPassMove()) {
            newPos.maxPlayerRound = !this.maxPlayerRound;
            return newPos;
        }

        LinkedList<OthelloAction> legalMoves = getAllPossibleMoves(endTime);
        boolean valid = false;
        for (OthelloAction m : legalMoves) {
            if (m.getRow() == action.getRow() && m.getColumn() == action.getColumn()) {
                valid = true;
                break;
            }
        }
        if (!valid) throw new IllegalMoveException(action);

        char playerDisc = newPos.maxPlayerRound ? 'W' : 'B';
        newPos.board[action.getRow()][action.getColumn()] = playerDisc;

        newPos.flipDiscs(action.getRow(), action.getColumn(), -1,  0, endTime);
        newPos.flipDiscs(action.getRow(), action.getColumn(), -1,  1, endTime);
        newPos.flipDiscs(action.getRow(), action.getColumn(),  0,  1, endTime);
        newPos.flipDiscs(action.getRow(), action.getColumn(),  1,  1, endTime);
        newPos.flipDiscs(action.getRow(), action.getColumn(),  1,  0, endTime);
        newPos.flipDiscs(action.getRow(), action.getColumn(),  1, -1, endTime);
        newPos.flipDiscs(action.getRow(), action.getColumn(),  0, -1, endTime);
        newPos.flipDiscs(action.getRow(), action.getColumn(), -1, -1, endTime);

        newPos.maxPlayerRound = !this.maxPlayerRound;

        return newPos;
    }

    private void flipDiscs(int row, int col, int dRow, int dCol, long endTime)  throws TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        int i = row + dRow;
        int j = col + dCol;

        char opponent = maxPlayerRound ? 'B' : 'W';
        char self = maxPlayerRound ? 'W' : 'B';

        if (board[i][j] != opponent) return;

        int k = i, l = j;
        while (board[k][l] == opponent) {
            k += dRow;
            l += dCol;
        }

        if (board[k][l] == self) {
            while (!(k == row && l == col)) {
                board[k][l] = self;
                k -= dRow;
                l -= dCol;
            }
        }
    }

    public boolean isTerminal(long endTime)  throws TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        if (!getAllPossibleMoves(endTime).isEmpty()) return false;

        OthelloPosition other = this.clone(endTime);
        other.maxPlayerRound = !this.maxPlayerRound;
        return other.getAllPossibleMoves(endTime).isEmpty();
    }

    protected OthelloPosition clone(long endTime)  throws TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        OthelloPosition newPosition = new OthelloPosition();
        newPosition.maxPlayerRound = maxPlayerRound;
        for (int i = 0; i < BOARD_SIZE + 2; i++)
            for (int j = 0; j < BOARD_SIZE + 2; j++)
                newPosition.board[i][j] = board[i][j];
        return newPosition;
    }

    public void illustrate() {
        System.out.print("   ");
        for (int i = 1; i <= BOARD_SIZE; i++)
            System.out.print("| " + i + " ");
        System.out.println("|");
        printHorizontalBorder();
        for (int i = 1; i <= BOARD_SIZE; i++) {
            System.out.print(" " + i + " ");
            for (int j = 1; j <= BOARD_SIZE; j++) {
                if (board[i][j] == 'W') {
                    System.out.print("| 0 ");
                } else if (board[i][j] == 'B') {
                    System.out.print("| X ");
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.println("| " + i + " ");
            printHorizontalBorder();
        }
        System.out.print("   ");
        for (int i = 1; i <= BOARD_SIZE; i++)
            System.out.print("| " + i + " ");
        System.out.println("|\n");
    }

    private void printHorizontalBorder() {
        System.out.print("---");
        for (int i = 1; i <= BOARD_SIZE; i++) {
            System.out.print("|---");
        }
        System.out.println("|---");
    }

    public String toString() {
        String s = "";
        char c, d;
        if (maxPlayerRound) {
            s += "W";
        } else {
            s += "B";
        }
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                d = board[i][j];
                if (d == 'W') {
                    c = 'O';
                } else if (d == 'B') {
                    c = 'X';
                } else {
                    c = 'E';
                }
                s += c;
            }
        }
        return s;
    }

}
