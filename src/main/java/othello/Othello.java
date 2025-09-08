package main.java.othello;

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
		String boardString;
		OthelloPosition position;
		OthelloAlgorithm algorithm;
		OthelloAction move;

		if(args.length > 0) {
			boardString = args[0];
		} else {
			boardString = "WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE";
		}

		position = new OthelloPosition(boardString);

		// TODO: remove before submitting, it's only for debug
		position.illustrate();

		// Which evaluator (heuristics) should be used
		algorithm = new AlphaBeta(new CountingEvaluator());

		// TODO: replace the fixed-depth implementation with Iterative Deepening Search
		// Set the depth that AlphaBeta will search to.
		algorithm.setSearchDepth(7);

		// Evaluate the position
		move = algorithm.evaluate(position);

		// Send the chosen move to stdout (print it)
		move.print();
    }
}