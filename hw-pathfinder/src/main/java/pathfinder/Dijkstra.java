package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * Find the shortest path in a graph between two nodes using Dijkstra's Algorithm
 */
public class Dijkstra {

    /**
     * Find the shortest path from node start to node end in graph using Dijkstra's Algorithm
     * @param graph - the graph to find path in
     * @param start - the starting node
     * @param end - the ending node
     * @param <T> - the type to represent nodes in graph
     * @return a Path that represents the shortest path from start to end;
     *          null if no valid path found;
     *          break ties by the time the paths are found
     * @spec.requires graph != null && start != null && end != null && all edges in graph have positive weights
     */
    public static <T> Path<T> findShortestPath(Graph<T, Double> graph, T start, T end) {
        Path<T> path = new Path<>(start);
        Queue<Path<T>> active = new PriorityQueue<>();
        Set<T> finished = new HashSet<>();
        active.add(path);

        while (!active.isEmpty()) {
            Path<T> minPath = active.remove();
            T minDest = minPath.getEnd();
            if (minDest.equals(end)) {
                return minPath;
            }
            if (finished.contains(minDest)) {
                continue;
            }

            for (Graph<T, Double>.Edge child : graph.getChildren(minDest)) {
                if (!finished.contains(child.getDestination())) {
                    Path<T> newPath = minPath.extend(child.getDestination(), child.getLabel());
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        return null;
    }
}
