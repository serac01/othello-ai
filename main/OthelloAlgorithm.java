

/**
 * This interface defines the mandatory methods for game playing algorithms,
 * i.e., algorithms that take an <code>OthelloAlgorithm</code> and return a
 * suggested move for the player who has the move.
 * 
 * The algorithm only defines the search method. The heuristic evaluation of
 * positions is given by an <code>main.java.othello.OthelloEvaluator</code> which is given to the
 * algorithm.
 * 
 * @author Henrik Bj&ouml;rklund
 */

public interface OthelloAlgorithm {

	// Sets the algorithm to use for heuristic evaluation.
	void setEvaluator(OthelloEvaluator evaluator);

	//Returns what the algorithm considers to be the best move.
	OthelloAction evaluate(OthelloPosition position) throws IllegalMoveException;

	// Sets the maximum search depth of the algorithm
	void setSearchDepth(int depth);
}