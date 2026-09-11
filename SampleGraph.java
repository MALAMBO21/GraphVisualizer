package algorithms;

import visualizer.Edge;
import visualizer.Vertex;

import java.util.*;

/**
 * Builds one small, fixed graph shared by every algorithm's unit tests, so the
 * expected results can be worked out by hand and cross-checked against each
 * other (e.g. Prim's and Kruskal's should agree on the MST, since every edge
 * weight below is unique and the minimum spanning tree is therefore unique).
 *
 * <pre>
 *      A --5-- B
 *      |       |
 *      2       3
 *      |       |
 *      C --8-- D
 *      |
 *      1
 *      |
 *      E
 * </pre>
 *
 * Edges: A-B(5), A-C(2), B-D(3), C-D(8), C-E(1)
 */
final class SampleGraph {

    final Map<Vertex, List<Edge>> adjacency = new HashMap<>();
    private final Map<String, Vertex> byId = new HashMap<>();

    SampleGraph() {
        // Vertex.vertices and Edge.edges are static containers shared with the
        // production Graph/Vertex/Edge classes, so each test must start from a
        // clean slate rather than accumulating vertices across test methods.
        Vertex.vertices.clear();
        Edge.edges.clear();

        Vertex a = new Vertex(0, 0, "A");
        Vertex b = new Vertex(0, 0, "B");
        Vertex c = new Vertex(0, 0, "C");
        Vertex d = new Vertex(0, 0, "D");
        Vertex e = new Vertex(0, 0, "E");
        byId.put("A", a);
        byId.put("B", b);
        byId.put("C", c);
        byId.put("D", d);
        byId.put("E", e);

        Edge ab = new Edge(a, b, 5);
        Edge ba = new Edge(b, a, 5);
        Edge ac = new Edge(a, c, 2);
        Edge ca = new Edge(c, a, 2);
        Edge bd = new Edge(b, d, 3);
        Edge db = new Edge(d, b, 3);
        Edge cd = new Edge(c, d, 8);
        Edge dc = new Edge(d, c, 8);
        Edge ce = new Edge(c, e, 1);
        Edge ec = new Edge(e, c, 1);

        // Lists must be mutable (not List.of(...)) since each algorithm sorts
        // them in place with Collections.sort / List.sort.
        adjacency.put(a, new ArrayList<>(List.of(ab, ac)));
        adjacency.put(b, new ArrayList<>(List.of(ba, bd)));
        adjacency.put(c, new ArrayList<>(List.of(ca, cd, ce)));
        adjacency.put(d, new ArrayList<>(List.of(db, dc)));
        adjacency.put(e, new ArrayList<>(List.of(ec)));
    }

    Vertex vertex(String id) {
        return byId.get(id);
    }
}
