/*
    Implements our new heuristics, based on piece count, corner and side position factoring,
    adjusted to also work when playing black
 */
public class CornerSideEvaluator implements OthelloEvaluator {

    @Override
    public int evaluate(OthelloPosition pos, boolean isWhitePlaying) throws TimeUpException {
        int cornerMultiplier = 1000;
        int sideMultiplier = 10;
        int pieceMultiplier = 1;
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

        int aiScore = (isWhitePlaying ? white : black) - (isWhitePlaying ? black : white);
        int aiCorners = (isWhitePlaying ? whiteCorners : blackCorners) - (isWhitePlaying ? blackCorners : whiteCorners);
        int aiSides = (isWhitePlaying ? whiteSides : blackSides) - (isWhitePlaying ? blackSides : whiteSides);
        return (aiScore * pieceMultiplier) + (aiCorners * cornerMultiplier) + (aiSides * sideMultiplier);
    }
}
