Knight's Tour Solver 🏇♞
A Java implementation for solving the Knight’s Tour Problem using BFS, DFS, DFS with h1b heuristic (Warnsdorff’s Rule), and DFS with h2 heuristic (Warnsdorff + Corner Proximity).
This project aims to efficiently solve the problem on various board sizes (n x n) while optimizing performance with heuristic-based depth-first search.

📌 Features
✔ Implements 4 different search strategies to solve the Knight’s Tour Problem:

- BFS (Breadth-First Search)
- DFS (Depth-First Search)
- DFS + h1b (Warnsdorff's Heuristic)
- DFS + h2 (Warnsdorff + Corner Proximity Heuristic)
  
✔ Supports different chessboard sizes (e.g., 8x8, 16x16, 32x32, 41x41, 52x52, etc.)

✔ Time limit control (set to 15 minutes to prevent infinite execution).

✔ Logging system to track execution details in .log files.

✔ Custom move sets allow different movement order variations for the knight.


🚀 Installation & Running the Project

Prerequisites
Java 8+ must be installed.



💡Clone the repository:

git clone https://github.com/hamzalis/knights-tour-solver.git

cd knights-tour-solver

💡Compile & Run:

javac Main.java

java Main

💡User Input Prompts

Once the program starts, it will ask for user inputs:

Enter board size (n): 8

Enter starting X coordinate (1-based): 1

Enter starting Y coordinate (1-based): 1

Choose move set index (0-4): 0

Choose algorithm (1 for BFS, 2 for DFS, 3 for DFS with h1b, 4 for DFS with h2): 4

📌 Example Execution & Output

💡 If you choose n=8, starting position (1,1), and DFS with h2 heuristic, you might get the following output:

Solution found!

Path: [(1, 1), (2, 3), (1, 5), (2, 7), (4, 8), ...]

Total nodes expanded: 3,242,065

Total nodes created: 3,242,160

Time spent: 00:01.359

💡 If no solution exists, the output will be:

No solution exists!

Total nodes expanded: 2087279528

Total nodes created: 2087279616

Time spent: 15:00.001


💡 If the program runs out of memory, the following message is displayed:

Out of Memory!

Total nodes expanded: 5,555,000

Total nodes created: 24,000,000

Time spent: 02:45.678

📂 Project Structure

📌 Main.java – Handles user input and initializes the solver.

📌 KnightTourSolver.java – Implements BFS, DFS, and heuristic-based searches.

📌 Node.java – Represents a chessboard state.

📌 SearchAlgorithm.java – Contains heuristics and movement rules.

📌 testcaseX.log – Log files recording test results.



