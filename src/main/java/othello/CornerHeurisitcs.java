package main.java.othello;
/**
 * A simple evaluator that just counts the number of black and white squares
 *
 */
public class CornerHeurisitcs implements OthelloEvaluator {

        public int evaluate(OthelloPosition pos) {

            int blackSquares = 0;
            int whiteSquares = 0;
            for (int i = 1; i <= OthelloPosition.BOARD_SIZE; i++) {
                for (int j = 1; j <= OthelloPosition.BOARD_SIZE; j++) {
                    if (((OthelloPosition) pos).board[i][j] == 'W')
                        whiteSquares++;
                    else if (((OthelloPosition) pos).board[i][j] == 'B')
                        blackSquares++;
                }
            }
            if(pos.isTerminal()) {
                return (whiteSquares - blackSquares) * 10;
            }

            int wc = findWhiteCorners(pos);
            int bc = findBlackCorners(pos);
            return whiteSquares - blackSquares + 10 * (wc-bc);
        }

        public int findWhiteCorners(OthelloPosition pos) {
            int whiteCorners = 0;
            if('W' == pos.board[1][1]){
                whiteCorners++;
            }
            if('W' == pos.board[1][8]){
                whiteCorners++;
            }
            if('W' == pos.board[8][1]){
                whiteCorners++;
            }
            if('W' == pos.board[8][8]){
                whiteCorners++;
            }
            return whiteCorners;
        }

        public int findBlackCorners(OthelloPosition pos) {
            int blackCorners = 0;
            if('B' == pos.board[1][1]){
                blackCorners++;
            }
            if('B' == pos.board[1][8]){
                blackCorners++;
            }
            if('B' == pos.board[8][1]){
                blackCorners++;
            }
            if('B' == pos.board[8][8]){
                blackCorners++;
            }
            return blackCorners;
        }
}
