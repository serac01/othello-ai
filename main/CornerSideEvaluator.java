public class CornerSideEvaluator implements OthelloEvaluator {

    private int cornerMultiplier = 1000;
    private int sideMultiplier = 10;
    private int pieceMultiplier = 1;
    private int dangerMultiplier = -10;

    @Override
    public int evaluate(OthelloPosition pos, boolean isWhitePlaying) throws TimeUpException {
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

        int whiteDanger = 0, blackDanger = 0;
        int[][] dangerSquares = {
                {1,2},{2,1},{2,2},
                {1,7},{2,8},{2,7},
                {7,1},{8,2},{7,2},
                {8,7},{7,8},{7,7}
        };
        for (int[] s : dangerSquares) {
            if (Thread.interrupted()) throw new TimeUpException();
            char square = pos.board[s[0]][s[1]];
            boolean adjacentCornerEmpty = false;
            if (s[0]<=2 && s[1]<=2) adjacentCornerEmpty = pos.board[1][1]=='E';
            if (s[0]<=2 && s[1]>=7) adjacentCornerEmpty = adjacentCornerEmpty || pos.board[1][8]=='E';
            if (s[0]>=7 && s[1]<=2) adjacentCornerEmpty = adjacentCornerEmpty || pos.board[8][1]=='E';
            if (s[0]>=7 && s[1]>=7) adjacentCornerEmpty = adjacentCornerEmpty || pos.board[8][8]=='E';

            if (!adjacentCornerEmpty) continue;
            if (square == 'W') whiteDanger++;
            else if (square == 'B') blackDanger++;
        }

        int aiScore = (isWhitePlaying ? white : black) - (isWhitePlaying ? black : white);
        int aiCorners = (isWhitePlaying ? whiteCorners : blackCorners) - (isWhitePlaying ? blackCorners : whiteCorners);
        int aiSides = (isWhitePlaying ? whiteSides : blackSides) - (isWhitePlaying ? blackSides : whiteSides);
        int aiDanger = (isWhitePlaying ? whiteDanger : blackDanger) - (isWhitePlaying ? blackDanger : whiteDanger);
        return (aiScore * pieceMultiplier) + (aiCorners * cornerMultiplier) + (aiSides * sideMultiplier) + (aiDanger * dangerMultiplier);
    }
}
