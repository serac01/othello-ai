# Othello (Reversi) AI Engine

## 📌 Introduction
This project implements an **Othello (Reversi) game engine** capable of selecting the best move for a given board position under a time limit.  
The engine uses **Alpha-Beta pruning** combined with **Iterative Deepening Search (IDS)** to ensure it always returns the best move found within the allocated time.

---

## ⚙️ Requirements

### Input
- A position string of length **65**:
    - First character: `W` or `B` → player to move.
    - Next 64 characters: the board, with
        - `E` = Empty
        - `O` = White
        - `X` = Black
- A **time limit in seconds**.

### Processing
- Compute the **best move** using:
    - **Alpha-Beta search** with a heuristic evaluation function.
    - **Iterative Deepening Search (IDS)**

### Output
- Print the move as **(row,column)**.
- Print **pass** if no legal moves exist.

---

## ✅ Assumptions
- White always starts → **White = MAX**, **Black = MIN** in Alpha-Beta.

---

## 📊 Performance Requirements
- Must **outperform a naive Alpha-Beta player** (fixed depth = 7, simple piece-count heuristic).
- Works for time limits between **2–10 seconds**, for both White and Black.

---

## 🛠️ Project Structure

- `Othello`: The main program. Currently, uses a fixed depth to search for a move. Modify this file to implement Iterative Deepening Search (IDS) using the time limit argument.
- `OthelloPosition`: Represents the game state. You must complete the missing parts marked TODO (e.g., generating legal moves, applying moves, checking terminal states).
- `OthelloAction`: Represents a move.
- `OthelloAlgorithm`: Interface for search algorithms. Your `AlphaBeta` class should implement this.
- `OthelloEvaluator`: Interface for evaluation functions. You can start with the provided CountingEvaluator (counts pieces) and then design a better heuristic.
- `AlphaBeta`: Skeleton for Alpha-Beta search. You need to complete the search logic and add time control checks for IDS.
- `othello.sh`: A bash script to compile and run your program. Modify it if necessary to work with your solution (it should work as is for Java and Python). It takes three arguments: position_string time_limit do_compile.


```bash
./othello.sh <position_string> <time_limit> <do_compile>
