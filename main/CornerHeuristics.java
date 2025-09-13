
/**
 * A simple evaluator that just counts the number of black and white squares
 *
 */
public class CornerHeuristics implements OthelloEvaluator {
        private int cornerMultiplyer = 1000;
        private int sideMultiplyer = 10;
        private int moveMultiplyer = 100;
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
                return (whiteSquares - blackSquares) * 100000;
            }

            int wc = findWhiteCorners(pos);
            int bc = findBlackCorners(pos);
            int sides = sidesWithSupportingCorner(pos);
            return whiteSquares - blackSquares + cornerMultiplyer * (wc-bc) + sideMultiplyer * sides + moveMultiplyer * pos.getAllPossibleMoves().size();
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

        public int sidesWithSupportingCorner(OthelloPosition pos){
            int sidesWithSupportingCorners = 0;
            int i = 0;
            int j = 1;
            //First row sides
            for(i = 2; i < 8; i++){
                if(('W' == pos.board[1][1] || 'W' == pos.board[1][8]) && 'W' == pos.board[i][j]){
                    sidesWithSupportingCorners++;
                }
            }
            j = 8;
            //last row sides
            for(i = 2; i < 8; i++){
                if(('W' == pos.board[8][1] || 'W' == pos.board[8][8]) && 'W' == pos.board[i][j]){
                    sidesWithSupportingCorners++;
                }
            }
            i = 1;
            for(j = 2; j < 8; j++){
                if(('W' == pos.board[1][1] || 'W' == pos.board[8][1]) && 'W' == pos.board[i][j]){
                    sidesWithSupportingCorners++;
                }
            }
            i = 8;
            for(j = 2; j < 8; j++){
                if(('W' == pos.board[8][8] || 'W' == pos.board[1][8]) && 'W' == pos.board[i][j]){
                    sidesWithSupportingCorners++;
                }
            }
            return sidesWithSupportingCorners;
        }
}
