package algorithms;

import visualizer.Edge;
import visualizer.Vertex;
import java.util.*;

public class DijkstrasAlgorithm implements GraphAlgorithm {
    @Override
    public String run(Map<Vertex, List<Edge>> graph, Vertex start) {

        // Initialize the map that will store the Vertex: Weight pairs.
        // The source vertex is added immediately with a distance of 0, since the
        // distance from the source to itself is always 0. Without this, the source
        // vertex would be silently missing from the final output.
        Map<Vertex, Double> outputMap = new TreeMap<>();
        outputMap.put(start, 0.0);

        // Initialize distances with infinity for all vertices except the source
        Map<Vertex, Double> distances = new HashMap<>();
        for (Vertex vertex: graph.keySet()) {
            if (!vertex.equals(start)) distances.put(vertex, Double.POSITIVE_INFINITY);
        }

        // Mark all vertices except source as unprocessed
        Set<Vertex> unprocessedVertices = new HashSet<>(distances.keySet());

        // Find the edges of the start Vertex
        List<Edge> startVertexEdges = graph.get(start);

        // Update distances to unprocessed neighbors of start vertex
        for (Edge edge: startVertexEdges) {
            Vertex neighbor = edge.getVertex2();
            int weight = edge.getWeight();
            if (unprocessedVertices.contains(neighbor)) {
                double newDistance = (double) weight;
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                }
            }
        }

        // Dijkstra's algorithm
        while (!unprocessedVertices.isEmpty()) {
            // Find the Vertex with the smallest distance
            Vertex current = findSmallestDistanceVertex(unprocessedVertices, distances);

            // Add the current vertex with its weight to the outputMap and mark it as processed
            outputMap.put(current, distances.get(current));
            unprocessedVertices.remove(current);

            // Find the edges of the current Vertex
            List<Edge> currentVertexEdges = graph.get(current);

            // Update distances to unprocessed neighbors
            for (Edge edge: currentVertexEdges) {
                Vertex neighbor = edge.getVertex2();
                double weight = (double) edge.getWeight();
                if (unprocessedVertices.contains(neighbor)) {
                    double newDistance = distances.get(current) + weight;
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                    }
                }
            }
        }

        String shortestPaths = processDistances(outputMap);
        // Prefix added for consistency with BFS/DFS's "ALGO : ..." output style,
        // and to match the "Dijkstra: A=0, B=4, ..." format shown in the brief.
        return "Dijkstra : " + shortestPaths.substring(0, shortestPaths.length() - 2);
    }

    private static Vertex findSmallestDistanceVertex(Set<Vertex> unprocessedVertices, Map<Vertex, Double> distances) {
        Vertex smallestVertex = null;
        double smallestDistance = Double.POSITIVE_INFINITY;

        for (Vertex vertex: unprocessedVertices) {
            if (distances.get(vertex) <= smallestDistance) {
                smallestVertex = vertex;
                smallestDistance = distances.get(vertex);
            }
        }

        return smallestVertex;
    }

    private String processDistances(Map<Vertex, Double> map) {
        String shortestPaths = "";
        for (Vertex vertex: map.keySet()) {
            Double weight = map.get(vertex);
            if (weight == Double.POSITIVE_INFINITY) {
                shortestPaths += vertex.getId() + "=" + weight + ", ";
            } else {
                shortestPaths += vertex.getId() + "=" + weight.intValue() + ", ";
            }
        }
        return shortestPaths;
    }
}
