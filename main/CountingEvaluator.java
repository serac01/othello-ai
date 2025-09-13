

/**
 * A simple evaluator that just counts the number of black and white squares
 * 
 * @author Henrik Bj&ouml;rklund
 */
public class CountingEvaluator implements OthelloEvaluator {

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
		return whiteSquares - blackSquares;
	}
}