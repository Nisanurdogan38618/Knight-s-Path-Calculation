# Knight's Path: A Pathfinding Simulation

## 📖 Project Overview
**Knight's Path** is a simulation-based strategy game implemented in **Java**. [cite_start]The core objective is to guide a knight through a 2D grid map to collect coins distributed across various terrain types[cite: 1192, 1193].

The project utilizes **Object-Oriented Design** principles and visualizes the pathfinding process using the `StdDraw` graphics library. [cite_start]The knight intelligently navigates obstacles (impassable tiles) and calculates the most efficient route based on terrain costs (Grass vs. Sand)[cite: 1194, 1196].

## 🚀 Key Features
* [cite_start]**Weighted Terrain System:** The map consists of different tile types (Grass, Sand, Impassable), each influencing the movement cost[cite: 1194].
* [cite_start]**Dijkstra's Algorithm:** Uses a priority-queue-based Dijkstra implementation to calculate the mathematically shortest path to objectives[cite: 1267].
* [cite_start]**Visualization:** Real-time rendering of the map, knight, and path steps using `StdDraw`[cite: 1196].
* [cite_start]**Bonus Mode:** Includes a visual enhancement feature that draws persistent, multi-colored trails to track the knight's history[cite: 1299, 1301].

## 🛠️ System Architecture
[cite_start]The project is structured into modular classes, separating logic from visualization[cite: 1202]:

* [cite_start]**`Main.java`**: The entry point that orchestrates file reading, pathplanning, and game loops[cite: 1272].
* **`PathFinder.java`**: The "brain" of the system. [cite_start]Implements Dijkstra's algorithm to find the least costly path[cite: 1266, 1267].
* [cite_start]**`MapLoader.java`**: parses input files to construct the 2D `Tile` grid and load objectives[cite: 1261].
* [cite_start]**`GameRenderer.java`**: Handles all graphical output (drawing the map, tiles, and the knight)[cite: 1269].
* [cite_start]**`Tile.java`**: Represents a single unit on the map, storing its type and neighbors[cite: 1258].
* [cite_start]**`Position.java`**: A helper class encapsulating (row, col) coordinates[cite: 1255].

## 🧠 Algorithm Logic
The map is treated as a **weighted undirected graph**. [cite_start]The cost to move between two tiles is determined by the terrain weights provided in the input files[cite: 1278].

**Total Path Cost Formula:**
$$\text{Total Cost} = \sum_{i=0}^{n-1} \text{cost}(T_i, T_{i+1})$$
[cite_start]*Where $T$ represents the tiles in the path sequence[cite: 1293].*

## 💻 How to Run
[cite_start]The program requires three input files to function[cite: 1195].

1.  **Compile the project:**
    ```bash
    javac *.java
    ```
2.  **Run the Standard Mode:**
    ```bash
    java Main map.txt costs.txt objectives.txt
    ```
3.  **Run the Bonus Mode (Visual Trails):**
    ```bash
    java Bonus map.txt costs.txt objectives.txt
    ```

## 📂 Input Files Description
* **Map File:** Defines the grid layout and obstacle positions.
* **Cost File:** Defines the traversal cost for each terrain type (e.g., Grass = 1, Sand = 3).
* **Objectives File:** Contains the coordinates of the coins the knight must collect.
---
*This project was developed as part of the CMPE 250 Data Structures and Algorithms course.*
