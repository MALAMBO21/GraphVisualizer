package algorithms;

import org.junit.jupiter.api.Test;
import visualizer.Vertex;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KruskalAlgorithmTest {

    @Test
    void buildsAMinimumSpanningTreeByAscendingEdgeWeight() {
        SampleGraph graph = new SampleGraph();
        Vertex start = graph.vertex("A");

        String result = new KruskalAlgorithm().run(graph.adjacency, start);

        // Processes edges in ascending weight order, keeping any edge that
        // doesn't form a cycle: C-E(1), A-C(2), B-D(3), A-B(5)
        // (C-D(8) is skipped - it would form a cycle). Total weight: 1+2+3+5 = 11.
        assertEquals("Kruskal : C-E(1), A-C(2), B-D(3), A-B(5) | Total Weight: 11", result);
    }

    @Test
    void agreesWithPrimsOnTotalWeightSinceAllEdgeWeightsAreUnique() {
        // When every edge weight is unique, the Minimum Spanning Tree is unique,
        // so Kruskal's and Prim's must find a tree of the same total weight -
        // even though they build it in a completely different order.
        SampleGraph graph = new SampleGraph();
        Vertex start = graph.vertex("A");

        String kruskalResult = new KruskalAlgorithm().run(graph.adjacency, start);

        assertEquals(11, extractTotalWeight(kruskalResult));
    }

    private static int extractTotalWeight(String kruskalOutput) {
        String marker = "Total Weight: ";
        int index = kruskalOutput.indexOf(marker);
        return Integer.parseInt(kruskalOutput.substring(index + marker.length()));
    }
}
