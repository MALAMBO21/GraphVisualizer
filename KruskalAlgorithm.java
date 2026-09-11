package algorithms;

import visualizer.Edge;
import visualizer.Vertex;

import java.util.*;

/**
 * Kruskal's Algorithm builds a Minimum Spanning Tree (MST) by considering
 * every edge in ascending order of weight and greedily keeping any edge
 * that connects two vertices which aren't already connected. This is the
 * classic "greedy + cycle detection" counterpart to Prim's "grow one
 * connected tree" approach - the two often produce different-looking (but
 * equally minimal-weight) trees on graphs with tied edge weights.
 */
public class KruskalAlgorithm implements GraphAlgorithm {

    @Override
    public String run(Map<Vertex, List<Edge>> graph, Vertex start) {

        // Kruskal's Algorithm only makes sense on the connected component
        // that contains the chosen start vertex, so the rest of the graph
        // (if the user built several disconnected pieces) is ignored - the
        // same convention PrimsAlgorithm already uses.
        Set<Vertex> connectedVertices = findConnectedVertices(graph, start);

        if (connectedVertices.size() == 1) {
            return "Kruskal : " + start.getId() + " (single vertex, no edges to connect)";
        }

        // Every undirected edge is stored twice internally (A->B and B->A),
        // so collect each one only once before sorting.
        List<Edge> distinctEdges = collectDistinctEdges(graph, connectedVertices);
        distinctEdges.sort(Comparator.comparingInt(Edge::getWeight));

        UnionFind unionFind = new UnionFind(connectedVertices);
        List<Edge> mstEdges = new ArrayList<>();
        int totalWeight = 0;

        for (Edge edge : distinctEdges) {
            // The tree is complete once it has (vertexCount - 1) edges
            if (mstEdges.size() == connectedVertices.size() - 1) break;

            // union() returns false if the edge would create a cycle,
            // in which case it is simply skipped
            if (unionFind.union(edge.getVertex1(), edge.getVertex2())) {
                mstEdges.add(edge);
                totalWeight += edge.getWeight();
            }
        }

        return processMST(mstEdges, totalWeight);
    }

    private static List<Edge> collectDistinctEdges(Map<Vertex, List<Edge>> graph, Set<Vertex> connectedVertices) {
        List<Edge> distinctEdges = new ArrayList<>();
        Set<String> seenPairs = new HashSet<>();

        for (Vertex vertex : connectedVertices) {
            for (Edge edge : graph.get(vertex)) {
                String pairKey = canonicalPairKey(edge.getVertex1(), edge.getVertex2());
                if (seenPairs.add(pairKey)) {
                    distinctEdges.add(edge);
                }
            }
        }

        return distinctEdges;
    }

    // Produces the same key regardless of which direction the Edge object was
    // created in, e.g. both "A -> B" and "B -> A" map to "A-B".
    private static String canonicalPairKey(Vertex vertex1, Vertex vertex2) {
        String id1 = vertex1.getId();
        String id2 = vertex2.getId();
        return id1.compareTo(id2) <= 0 ? id1 + "-" + id2 : id2 + "-" + id1;
    }

    private static String processMST(List<Edge> mstEdges, int totalWeight) {
        StringBuilder output = new StringBuilder("Kruskal : ");

        for (Edge edge : mstEdges) {
            output.append(canonicalPairKey(edge.getVertex1(), edge.getVertex2()))
                    .append("(").append(edge.getWeight()).append("), ");
        }

        output.setLength(output.length() - 2);
        output.append(" | Total Weight: ").append(totalWeight);

        return output.toString();
    }

    /*
    Uses Breadth-First Search to find all Vertices reachable from a start Vertex.
    (Deliberately duplicated from PrimsAlgorithm rather than shared, so each
    algorithm class stays fully self-contained and independently testable.)
     */
    private static Set<Vertex> findConnectedVertices(Map<Vertex, List<Edge>> graph, Vertex start) {
        Set<Vertex> reachableVertices = new HashSet<>();
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            reachableVertices.add(current);

            for (Edge edge : graph.get(current)) {
                Vertex neighbor = edge.getVertex2();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return reachableVertices;
    }
}
