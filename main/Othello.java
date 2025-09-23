import java.util.concurrent.*;

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
	public static void main(String [] args) {
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

		// Create a thread
		ExecutorService executor = Executors.newSingleThreadExecutor();
		OthelloPosition position = new OthelloPosition(boardString);
		boolean isWhitePlaying = (boardString.charAt(0) == 'W');
		OthelloAlgorithm algorithm = new AlphaBeta(new CornerSideEvaluator(), isWhitePlaying);
		OthelloAction bestMove = null;

		long endTime = (startTime + timeLimit * 1000L) - 400L;
		int depth = 0;
		while (true) {
			long remaining = endTime - System.currentTimeMillis();
			if (remaining <= 0) break;
			depth++;
			int finalDepth = depth;

			// Run the set setSearchDepth and evaluate on the thread
			Future<OthelloAction> future = executor.submit(() -> {
				algorithm.setSearchDepth(finalDepth);
				return algorithm.evaluate(position);
			});

			try {
				// if evaluation is made before time it returns the move, if not, returns and exception
				OthelloAction move = future.get(remaining, TimeUnit.MILLISECONDS);
				if (move != null) bestMove = move;
			} catch (TimeoutException e) {
				// Cancel the thread
				future.cancel(true);
				break;
			} catch (Exception e) {
				break;
			}
		}

		// Shutdown the executor
		executor.shutdownNow();

		if(bestMove == null)  bestMove = new OthelloAction("pass");

		bestMove.print();
	}
}
