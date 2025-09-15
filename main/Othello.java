public class Othello{
	/**
	 * Current behavior:
	 *   - Uses Alpha-Beta pruning with a fixed search depth.
	 *   - Prints the best move found at that depth.
	 *
	 * Assignment requirement:
	 *   - Replace fixed-depth search with Iterative Deepening Search (IDS)
	 *     that respects a time limit (provided as an argument).
	 *
	 * @author Henrik Björklund; serac01; josigabor
	 */
	public static void main(String [] args) throws IllegalMoveException, TimeUpException {
		long startTime = System.currentTimeMillis();
		if (args.length < 2) {
			System.err.println("Too few arguments.\nUsage: othello <position_string> <time_limit_seconds>");
			return;
		}

		String boardString = args[0];
		String timeStr = args[1];

		if (boardString.length() != 65 ||
				!(boardString.charAt(0) == 'W' || boardString.charAt(0) == 'B') ||
				!boardString.substring(1).matches("[EOX]{64}")) {
			System.err.println("Invalid board string.\nMust be 65 characters: first W/B, rest E/O/X.");
			return;
		}

		int timeLimit;
		try {
			timeLimit = Integer.parseInt(timeStr);
			if (timeLimit <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.err.println("Invalid time limit.\nMust be a positive number.");
			return;
		}

		OthelloPosition position = new OthelloPosition(boardString);
		OthelloAlgorithm algorithm = new AlphaBeta(new CornerSideEvaluator());

		int depth = 1;
		OthelloAction move;
		OthelloAction bestMove = null;

		try {
			long endTime = startTime + timeLimit * 1000L;
			while (System.currentTimeMillis() < endTime) {
				algorithm.setSearchDepth(depth);
				move = algorithm.evaluate(position, endTime);
				if (move != null) bestMove = move;
				depth++;
			}
		} catch(TimeUpException e) {
			// TODO
		}

		if(bestMove == null)  bestMove = new OthelloAction("pass");

		bestMove.print();
	}
}
