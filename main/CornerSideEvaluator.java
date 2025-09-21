public class CornerSideEvaluator implements OthelloEvaluator {

    private int cornerMultiplier = 1000;
    private int sideMultiplier = 10;
    private int pieceMultiplier = 1;

    @Override
    public int evaluate(OthelloPosition pos) throws TimeUpException {
        int white = 0, black = 0;

        for (int i = 1; i <= OthelloPosition.BOARD_SIZE; i++) {
            for (int j = 1; j <= OthelloPosition.BOARD_SIZE; j++) {
                if (Thread.interrupted()) throw new TimeUpException();
                char c = pos.board[i][j];
                if (c == 'W') white++;
                else if (c == 'B') black++;
            }
        }
        if(pos.isTerminal()) return (white - black) * 100000;

        int[][] corners = {{1,1},{1,8},{8,1},{8,8}};
        int whiteCorners = 0, blackCorners = 0;
        for (int[] c : corners) {
            if (Thread.interrupted()) throw new TimeUpException();
            if (pos.board[c[0]][c[1]] == 'W') whiteCorners++;
            else if (pos.board[c[0]][c[1]] == 'B') blackCorners++;
        }

        int whiteSides = 0, blackSides = 0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (Thread.interrupted()) throw new TimeUpException();
                boolean isCorner = (i==1||i==8) && (j==1||j==8);
                boolean isSide = (i==1||i==8||j==1||j==8) && !isCorner;
                if (!isSide) continue;
                if (pos.board[i][j]=='W') whiteSides++;
                else if (pos.board[i][j]=='B') blackSides++;
            }
        }

        return (white - black) * pieceMultiplier
                + (whiteCorners - blackCorners) * cornerMultiplier
                + (whiteSides - blackSides) * sideMultiplier;
    }
}