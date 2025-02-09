# Turkey Map Navigation System

## Overview
This project implements a navigation system for cities in Turkey using graph-based pathfinding algorithms. The system efficiently finds routes between cities, ensuring optimized travel paths and distance calculations.

---

## Features
- **City Representation:** Each city is represented as a node in the graph with connections to other cities.
- **Optimized Pathfinding:** Uses efficient traversal algorithms, including Floyd-Warshall, to calculate the shortest path.
- **Dynamic Route Updates:** Handles dynamic changes in connections or distances between cities.
- **Visualization:** The system leverages `stdlib` to visualize the map and routes graphically.

---

## Project Structure

```
TurkeyMapNavigation/
|-- code/
|   |-- City.java                   # Represents a city as a graph node
|   |-- MehmetCanGurbuz.java        # Core implementation of the navigation system
|-- misc/                           # Additional resources or configuration files
|-- report/                         # Documentation and project reports
```

---

## How It Works

1. **Graph Initialization:**
    - The cities and their connections are initialized as a graph, where each city is a node and roads between them are weighted edges.

2. **Pathfinding Algorithm:**
    - The system calculates the optimal path between cities using graph traversal.
    - **Floyd-Warshall Algorithm:** Computes the shortest paths between all pairs of cities efficiently, even in dense networks.
    - Weights on edges represent distances or travel times, depending on the configuration.

3. **Dynamic Updates:**
    - The graph structure can be updated dynamically to reflect changes in distances or new road connections.

4. **Visualization with `stdlib`:**
    - The project uses `stdlib` to visually represent the cities, connections, and optimal routes.
    - The graphical output displays the current state of the map and highlights the shortest path between the selected cities.

---

## Example Workflow
1. The user provides the starting city and destination.
2. The system calculates the optimal path using Floyd-Warshall or other algorithms and displays the result.
3. The graphical output shows the cities and the optimal route.
4. If any road updates or changes occur, the graph and visualization are updated accordingly.

---

## Installation and Usage

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd TurkeyMapNavigation/code
    ```

2. Compile the Java files:
    ```bash
    javac *.java
    ```

3. Run the application:
    ```bash
    java MehmetCanGurbuz
    ```

4. View the graphical visualization as the path is computed.

---

## Future Improvements
- Integrate real-time traffic and road conditions.
- Implement support for multiple pathfinding algorithms (e.g., Dijkstra, A*).
- Improve visualization with interactive features for user input and map navigation.
