package algorithms;

import visualizer.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A minimal Disjoint Set Union (Union-Find) structure with path compression
 * and union by rank. It answers one question efficiently: "are these two
 * vertices already connected?" - which is exactly what Kruskal's Algorithm
 * needs to detect (and avoid) cycles while building a Minimum Spanning Tree.
 */
public class UnionFind {

    private final Map<Vertex, Vertex> parent = new HashMap<>();
    private final Map<Vertex, Integer> rank = new HashMap<>();

    public UnionFind(Set<Vertex> vertices) {
        // Every vertex starts out as its own, single-member set
        for (Vertex vertex : vertices) {
            parent.put(vertex, vertex);
            rank.put(vertex, 0);
        }
    }

    /**
     * Finds the representative ("root") of the set that the given vertex
     * belongs to, compressing the path along the way so future lookups
     * are faster.
     */
    public Vertex find(Vertex vertex) {
        if (!parent.get(vertex).equals(vertex)) {
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }

    /**
     * Attempts to merge the sets containing vertex1 and vertex2.
     *
     * @return true if the two vertices were in different sets (and have now
     *         been merged); false if they were already connected, meaning
     *         connecting them again would create a cycle.
     */
    public boolean union(Vertex vertex1, Vertex vertex2) {
        Vertex root1 = find(vertex1);
        Vertex root2 = find(vertex2);

        if (root1.equals(root2)) return false;

        // Union by rank: always attach the shorter tree under the taller one
        int rank1 = rank.get(root1);
        int rank2 = rank.get(root2);

        if (rank1 < rank2) {
            parent.put(root1, root2);
        } else if (rank1 > rank2) {
            parent.put(root2, root1);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank1 + 1);
        }

        return true;
    }
}
