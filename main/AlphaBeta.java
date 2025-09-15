
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

	public OthelloAction evaluate(OthelloPosition pos, long endTime) throws IllegalMoveException, TimeUpException {
		if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		OthelloAction bestValue = null;
		OthelloPosition newPos = null;
		for (OthelloAction value : pos.getAllPossibleMoves(endTime)) {
			if(value == null) {
				return new OthelloAction("pass");
			}
			try{
				newPos = pos.makeMove(value, endTime);
			}catch(IllegalMoveException e){
				value.pass = true;
				continue;
			}

			int score = alphaBeta(newPos, searchDepth - 1, alpha, beta, true, endTime);
			if (score > alpha) {
				alpha = score;
				bestValue = value;
			}
		}

		return bestValue;
	}

	int alphaBeta(OthelloPosition pos, int depth, int alpha, int beta, boolean isMax, long endTime)  throws IllegalMoveException, TimeUpException {
		if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
		if (depth == 0 || pos.isTerminal(endTime))
			return evaluator.evaluate(pos, endTime);
		return isMax ? maxValue(pos, depth,alpha, beta, endTime) : minValue(pos, depth,alpha, beta, endTime);
	}

	int maxValue(OthelloPosition pos, int depth, int alpha, int beta, long endTime)  throws IllegalMoveException, TimeUpException {
		if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
		int value = Integer.MIN_VALUE;
		for (OthelloAction move : pos.getAllPossibleMoves(endTime)) {
			OthelloPosition newPos = pos.makeMove(move, endTime);
			int eval = alphaBeta(newPos, depth - 1, alpha, beta, false, endTime);
			value = Math.max(value, eval);
			alpha = Math.max(alpha, eval);
			if (beta <= alpha) break; // cut off
		}
		return value;
	}

	int minValue(OthelloPosition pos, int depth, int alpha, int beta, long endTime)  throws IllegalMoveException, TimeUpException {
		if(System.currentTimeMillis() >= endTime) throw new TimeUpException();
		int value = Integer.MAX_VALUE;
		for (OthelloAction move : pos.getAllPossibleMoves(endTime)) {
			OthelloPosition newPos = pos.makeMove(move, endTime);
			int eval = alphaBeta(newPos, depth - 1, alpha, beta, true, endTime);
			value = Math.min(value, eval);
			beta = Math.min(beta, eval);
			if (beta <= alpha) break; // cut off
		}
		return value;
	}
}