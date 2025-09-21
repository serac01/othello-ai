
public class TestMain{
    public static void main(String [] args) throws IllegalMoveException, TimeUpException {
        String boardString;

        if(args.length > 0) {
            boardString = args[0];
        } else {
            boardString = "WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE";
        }

        OthelloPosition position = new OthelloPosition(boardString);
        OthelloAlgorithm algorithm = new AlphaBeta(new CornerSideEvaluator());

        // firsMoveFirstOption(position);
        alphaBeta(position,algorithm);


        // Which evaluator (heuristics) should be used
        // algorithm = new AlphaBeta(new CountingEvaluator());

        // TODO: replace the fixed-depth implementation with Iterative Deepening Search
        // Set the depth that AlphaBeta will search to.
        // algorithm.setSearchDepth(8);

        // Evaluate the position
		/*OthelloAction move = algorithm.evaluate(position);
		if(move == null) {
			move = new OthelloAction("pass");
		}
		// Send the chosen move to stdout (print it)
		 move.print();*/

    }

    private static void alphaBeta(OthelloPosition position, OthelloAlgorithm algorithm) throws IllegalMoveException, TimeUpException {
        position.illustrate();
        OthelloAction move;

        int turn = 1;
        while (!position.isTerminal()) {
            // TODO: REMOVE DEBUG
            System.out.println("Turn " + turn + " - Player " + (position.toMove() ? "W" : "B"));

            if (!position.getAllPossibleMoves().isEmpty()) {
                algorithm.setSearchDepth(7);
                move = algorithm.evaluate(position);

                try {
                    System.out.println("Available moves:");
                    for (OthelloAction action : position.getAllPossibleMoves()) {
                        action.print();
                    }

                    // TODO: move can be null at this point?????
                    position = position.makeMove(move);
                    position.illustrate();
                } catch (IllegalMoveException e) {
                    // TODO: REMOVE DEBUG
                    System.out.println("AI tried illegal move! This shouldn't happen.");
                    break;
                }
            } else {
                System.out.println("No possible move -> PASS");
                try {
                    position = position.makeMove(new OthelloAction(0, 0, true));
                } catch (IllegalMoveException e) {
                    System.out.println("Error during make move");
                    break;
                }
            }

            turn++;
        }
        System.out.println("End Game");
    }


    private static void firsMoveFirstOption(OthelloPosition position) throws TimeUpException {
        position.illustrate();
        OthelloAction move;

        int turn = 1;
        while (!position.isTerminal()) {
            // TODO: REMOVE DEBUG
            System.out.println("Turn " + turn + " - Player " + (position.toMove() ? "W" : "B"));

            if (!position.getAllPossibleMoves().isEmpty()) {
                try {
                    // TODO: REMOVE DEBUG
                    System.out.println("Available moves:");
                    for (OthelloAction action : position.getAllPossibleMoves()) {
                        action.print();
                    }

                    move = position.getAllPossibleMoves().getFirst();
                    // TODO: REMOVE DEBUG
                    System.out.print("Choose: ");
                    move.print();

                    position = position.makeMove(move);
                    position.illustrate();
                } catch (IllegalMoveException e) {
                    // TODO: REMOVE DEBUG
                    System.out.println("Illegal move: " + e.getAction());
                    break;
                }
            } else {
                // TODO: REMOVE DEBUG
                System.out.println("No possible move -> PASS");
                try {
                    position = position.makeMove(new OthelloAction(0, 0, true));
                } catch (IllegalMoveException e) {
                    // TODO: REMOVE DEBUG
                    System.out.println("Error during make move");
                    break;
                }
            }

            turn++;
        }

        // TODO: REMOVE DEBUG
        System.out.println("End game!");
    }
}