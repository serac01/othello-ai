public class CornerSideEvaluator implements OthelloEvaluator {

    private int cornerMultiplier = 1000;
    private int sideMultiplier = 10;
    private int pieceMultiplier = 1;
    private int moveMultiplier = 1;

    @Override
    public int evaluate(OthelloPosition pos, long endTime) throws TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();

        int white = 0, black = 0;
        int moveCount =  pos.getAllPossibleMoves(endTime).size();
        for (int i = 1; i <= OthelloPosition.BOARD_SIZE; i++) {
            for (int j = 1; j <= OthelloPosition.BOARD_SIZE; j++) {
                char c = pos.board[i][j];
                if (c == 'W') white++;
                else if (c == 'B') black++;
            }
        }
        if(moveCount == 0) return (white) * 100000;
        int[][] corners = {{1,1},{1,8},{8,1},{8,8}};
        int whiteCorners = 0, blackCorners = 0;
        for (int[] c : corners) {
            if (pos.board[c[0]][c[1]] == 'W') whiteCorners++;
            else if (pos.board[c[0]][c[1]] == 'B') blackCorners++;
        }

        int whiteSides = 0, blackSides = 0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                boolean isCorner = (i==1||i==8) && (j==1||j==8);
                boolean isSide = (i==1||i==8||j==1||j==8) && !isCorner;
                if (!isSide) continue;
                if (pos.board[i][j]=='W') whiteSides++;
                else if (pos.board[i][j]=='B') blackSides++;
            }
        }
        int[][] subCorners = {{1,2},{2,1},{1,7},{2,8},{7,1},{8,2},{7,8},{8,7}};
        int whiteSubCorners = 0, blackSubCorners = 0;
//        for (int[] c : subCorners) {
//            if (pos.board[c[0]][c[1]] == 'W') whiteSubCorners++;
//            else if (pos.board[c[0]][c[1]] == 'B') blackSubCorners++;
//        }
//        int subCornerMultiplyer = white+black > 30 ? 10 : -1;
        int subCornerMultiplyer = 0;



        return (white - black) * pieceMultiplier
                + (whiteCorners - blackCorners) * cornerMultiplier
                + (whiteSides - blackSides) * sideMultiplier
                + (whiteSubCorners - blackSubCorners) * subCornerMultiplyer
                + (moveCount * moveMultiplier);
    }
}
/* Heuristics optimalisation on 2 secs
Corner | Side | Piece Multiplyer
1000 10 1 : 14 to black / 40 to black / 40 to black / 60 to black / 60 to black / 46 to black





 */