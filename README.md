# ♟️ Othello AI

> An Artificial Intelligence agent for **Othello (Reversi)** developed in **Java** for the **Artificial Intelligence (5DV243)** course at **Umeå University**.

The objective of the project is to build a competitive Othello engine capable of selecting the best move within a given time limit using adversarial search techniques. The AI combines **Alpha-Beta Pruning**, **Iterative Deepening Search (IDS)**, and heuristic evaluation to efficiently explore the game tree and make strong decisions.

---

# ✨ Features

- ♟️ Complete Othello (Reversi) game engine
- 🌳 Alpha-Beta Pruning search algorithm
- ⏱️ Iterative Deepening Search (IDS)
- 🧠 Heuristic board evaluation
- ⚡ Time-limited move selection
- ✔️ Legal move generation
- 🎯 Automatic best move recommendation
- 🏆 Designed to outperform a naïve Alpha-Beta player
- 🖥️ Command-line interface
- 📊 Performance evaluation against baseline AI

---

# 🛠 Technologies

- Java
- Object-Oriented Programming
- Alpha-Beta Pruning
- Iterative Deepening Search (IDS)
- Heuristic Evaluation
- Minimax Search
- Game Tree Search

---

# 📚 Artificial Intelligence Concepts

This project demonstrates several core AI concepts:

- Adversarial Search
- Minimax Algorithm
- Alpha-Beta Pruning
- Iterative Deepening Search
- Heuristic Evaluation Functions
- Time-Constrained Search
- Game Tree Optimization

---

# ♟️ Game Overview

Othello (also known as Reversi) is a two-player strategy board game played on an 8×8 grid.

Players alternately place discs on the board, capturing opponent pieces by surrounding them horizontally, vertically, or diagonally.

The objective is to finish the game with more discs of your color than your opponent.

The AI evaluates possible future game states and recommends the strongest move within the available thinking time.

---

# 🧠 Search Algorithm

The engine combines several search techniques to efficiently explore the game tree.

## Alpha-Beta Pruning

- Explores the Minimax search tree
- Eliminates branches that cannot affect the final decision
- Significantly reduces the number of evaluated positions

## Iterative Deepening Search

- Starts searching at depth 1
- Progressively increases search depth
- Stops when the time limit is reached
- Returns the best move found at the last completed depth

## Heuristic Evaluation

Non-terminal positions are evaluated using heuristic functions that estimate the quality of a board state when a complete search is not feasible.

---

# 📥 Input Format

The program receives:

- Board position string
- Player to move
- Time limit (seconds)

Example:

```text
WEEEEEEEE...
```

Where:

- `W` or `B` indicates the current player
- Remaining characters represent the 8×8 board
- `E` = Empty
- `O` = White
- `X` = Black

---

# 📤 Output Format

The engine outputs either:

```text
(4,7)
```

or

```text
pass
```

if no legal move exists.

---

# 🧩 Project Structure

```text
OthelloAI/
│
├── AlphaBeta.java
├── Othello.java
├── OthelloAction.java
├── OthelloAlgorithm.java
├── OthelloEvaluator.java
├── CountingEvaluator.java
├── OthelloPosition.java
├── othello.sh
└── ...
```

---

# 🏗 Architecture

The project follows a modular Object-Oriented architecture.

```text
Othello
│
├── Game State
│     └── OthelloPosition
│
├── Search
│     ├── AlphaBeta
│     └── IDS
│
├── Evaluation
│     └── Heuristic Functions
│
└── Move Representation
      └── OthelloAction
```

The search algorithm is separated from the board representation, allowing different evaluation functions and search strategies to be implemented independently.

---

# 🚀 Getting Started

## Requirements

- Java 17+ (or compatible version)
- Bash (for `othello.sh`)

## Clone the repository

```bash
git clone https://github.com/serac01/othello-ai.git
```

## Compile

```bash
javac *.java
```

## Run

```bash
java Othello
```

Or use the provided script:

```bash
bash othello.sh <position> <time_limit> <compile_flag>
```

Example:

```bash
bash othello.sh WEEEE... 5 true
```

---

# 🎮 AI Performance

The engine was developed to compete against a provided baseline implementation using a simple piece-count heuristic.

Testing included:

- Playing as White and Black
- Multiple search depths
- Different time limits
- Performance comparisons against the naïve AI

The combination of Alpha-Beta Pruning, Iterative Deepening Search, and improved heuristics enables significantly stronger play while meeting strict time constraints.

---

# 📄 License

This project was developed for the **Artificial Intelligence** course at **Umeå University**.

It is intended for educational purposes.