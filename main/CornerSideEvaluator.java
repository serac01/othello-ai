public class CornerSideEvaluator implements OthelloEvaluator {

    private int cornerMultiplier = 1000;
    private int sideMultiplier = 10;
    private int pieceMultiplier = 1;

    @Override
    public int evaluate(OthelloPosition pos, long endTime) throws TimeUpException {
        if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
        int white = 0, black = 0;

        for (int i = 1; i <= OthelloPosition.BOARD_SIZE; i++) {
            for (int j = 1; j <= OthelloPosition.BOARD_SIZE; j++) {
                char c = pos.board[i][j];
                if (c == 'W') white++;
                else if (c == 'B') black++;
            }
        }

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

        if(pos.isTerminal(endTime)) return (white - black) * 100000;

        return (white - black) * pieceMultiplier
                + (whiteCorners - blackCorners) * cornerMultiplier
                + (whiteSides - blackSides) * sideMultiplier;
    }
}
