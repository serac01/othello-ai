
/**
 * Alpha-Beta search algorithm.
 * TODO: complete the search logic and add time control checks for IDS
 * 
 * @author Henrik Bj&ouml; rklund; serac01; josigabor
 */
public class AlphaBeta implements OthelloAlgorithm {
	protected int searchDepth;
	protected static final int DefaultDepth = 7;
	protected OthelloEvaluator evaluator;

	public AlphaBeta() {
		evaluator = new CountingEvaluator();
		searchDepth = DefaultDepth;
	}

	public AlphaBeta(OthelloEvaluator eval) {
		evaluator = eval;
		searchDepth = DefaultDepth;
	}

	public AlphaBeta(OthelloEvaluator eval, int depth) {
		evaluator = eval;
		searchDepth = depth;
	}

	public void setEvaluator(OthelloEvaluator eval) {
		evaluator = eval;
	}

	public void setSearchDepth(int depth) {
		searchDepth = depth;
	}

	public OthelloAction evaluate(OthelloPosition pos) throws IllegalMoveException {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		OthelloAction bestValue = null;

		for (OthelloAction value : pos.getAllPossibleMoves()) {
			OthelloPosition newPos = pos.makeMove(value);
			int score = alphaBeta(newPos, searchDepth - 1, alpha, beta, true);
			if (score > alpha) {
				alpha = score;
				bestValue = value;
			}
		}

		return bestValue;
	}

	int alphaBeta(OthelloPosition pos, int depth, int alpha, int beta, boolean isMax) throws IllegalMoveException {
		if (depth == 0 || pos.isTerminal())
			return evaluator.evaluate(pos);
		return isMax ? maxValue(pos, depth,alpha, beta) : minValue(pos, depth,alpha, beta);
	}

	int maxValue(OthelloPosition pos, int depth, int alpha, int beta) throws IllegalMoveException {
		int value = Integer.MIN_VALUE;
		for (OthelloAction move : pos.getAllPossibleMoves()) {
			OthelloPosition newPos = pos.makeMove(move);
			int eval = alphaBeta(newPos, depth - 1, alpha, beta, false);
			value = Math.max(value, eval);
			alpha = Math.max(alpha, eval);
			if (beta <= alpha) break; // cut off
		}
		return value;
	}

	int minValue(OthelloPosition pos, int depth, int alpha, int beta) throws IllegalMoveException {
		int value = Integer.MAX_VALUE;
		for (OthelloAction move : pos.getAllPossibleMoves()) {
			OthelloPosition newPos = pos.makeMove(move);
			int eval = alphaBeta(newPos, depth - 1, alpha, beta, true);
			value = Math.min(value, eval);
			beta = Math.min(beta, eval);
			if (beta <= alpha) break; // cut off
		}
		return value;
	}
}