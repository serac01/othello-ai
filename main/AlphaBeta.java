import java.util.LinkedList;

/**
 * Alpha-Beta search algorithm.
 * TODO: complete the search logic and add time control checks for IDS
 * 
 * @author Henrik Bj&ouml; rklund; serac01; josigabor
 */
public class AlphaBeta implements OthelloAlgorithm {
	protected int searchDepth;
	protected boolean isWhitePlaying;
	protected static final int DefaultDepth = 7;
	protected OthelloEvaluator evaluator;

	public AlphaBeta() {
		this.evaluator = new CountingEvaluator();
		this.searchDepth = DefaultDepth;
	}

	public AlphaBeta(OthelloEvaluator eval) {
		this.evaluator = eval;
		this.searchDepth = DefaultDepth;
	}

	public AlphaBeta(OthelloEvaluator eval, boolean isWhitePlaying) {
		this.evaluator = eval;
		this.isWhitePlaying = isWhitePlaying;
		this.searchDepth = DefaultDepth;
	}

	public AlphaBeta(OthelloEvaluator eval, int depth) {
		this.evaluator = eval;
		this.searchDepth = depth;
	}

	public void setEvaluator(OthelloEvaluator eval) {
		evaluator = eval;
	}

	public void setSearchDepth(int depth) {
		searchDepth = depth;
	}
	/*
		This function calls the first recursion
	 */
	public OthelloAction evaluate(OthelloPosition pos) throws IllegalMoveException, TimeUpException {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		OthelloAction bestValue = null;

		LinkedList<OthelloAction> moves = pos.getAllPossibleMoves();

		if (moves.isEmpty()) return new OthelloAction("pass");

		for (OthelloAction move : moves) {
			if (Thread.interrupted()) throw new TimeUpException();

			OthelloPosition newPos = pos.makeMove(move);
			int score = alphaBeta(newPos, searchDepth - 1, alpha, beta, false);

			if (score > alpha) {
				alpha = score;
				bestValue = move;
			}
		}
		return bestValue;
	}
	/*
		The function handles starting the max-min search call based on the isMax parameter
	 */
	int alphaBeta(OthelloPosition pos, int depth, int alpha, int beta, boolean isMax)  throws IllegalMoveException, TimeUpException {
		if (Thread.interrupted()) throw new TimeUpException();
		if (depth == 0 || pos.isTerminal())
			return evaluator.evaluate(pos, isWhitePlaying);
		return isMax ? maxValue(pos, depth,alpha, beta) : minValue(pos, depth,alpha, beta);
	}
	/*
		Implements the Max search, calling a Min search on a lower layer if depth allows
	 */
	int maxValue(OthelloPosition pos, int depth, int alpha, int beta)  throws IllegalMoveException, TimeUpException {
		if (Thread.interrupted()) throw new TimeUpException();
		int value = Integer.MIN_VALUE;
		for (OthelloAction move : pos.getAllPossibleMoves()) {
			if (Thread.interrupted()) throw new TimeUpException();
			OthelloPosition newPos = pos.makeMove(move);
			int eval = alphaBeta(newPos, depth - 1, alpha, beta, false);
			if (Thread.interrupted()) throw new TimeUpException();
			value = Math.max(value, eval);
			alpha = Math.max(alpha, eval);
			if (beta <= alpha) break; // cut off
		}
		return value;
	}
	/*
        Implements the Min search, calling a Max search on a lower layer if depth allows
    */
	int minValue(OthelloPosition pos, int depth, int alpha, int beta)  throws IllegalMoveException, TimeUpException {
		if (Thread.interrupted()) throw new TimeUpException();
		int value = Integer.MAX_VALUE;
		for (OthelloAction move : pos.getAllPossibleMoves()) {
			if (Thread.interrupted()) throw new TimeUpException();
			OthelloPosition newPos = pos.makeMove(move);
			int eval = alphaBeta(newPos, depth - 1, alpha, beta, true);
			if (Thread.interrupted()) throw new TimeUpException();
			value = Math.min(value, eval);
			beta = Math.min(beta, eval);
			if (beta <= alpha) break; // cut off
		}
		return value;
	}
}