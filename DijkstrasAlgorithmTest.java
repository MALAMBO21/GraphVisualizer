package algorithms;

import org.junit.jupiter.api.Test;
import visualizer.Vertex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DijkstrasAlgorithmTest {

    @Test
    void reportsShortestDistanceToEveryReachableVertex() {
        SampleGraph graph = new SampleGraph();
        Vertex start = graph.vertex("A");

        String result = new DijkstrasAlgorithm().run(graph.adjacency, start);

        // Shortest distances from A: A=0 (itself), C=2 (direct),
        // E=3 (via C), B=5 (direct), D=8 (via B: 5+3, cheaper than via C: 2+8=10)
        assertEquals("Dijkstra : A=0, B=5, C=2, D=8, E=3", result);
    }

    @Test
    void regressionTest_includesTheStartVertexAtDistanceZero() {
        // Regression test for a bug in the original implementation: the start
        // vertex was never added to the output map, so it silently vanished
        // from the result instead of showing up as "<id>=0".
        SampleGraph graph = new SampleGraph();
        Vertex start = graph.vertex("A");

        String result = new DijkstrasAlgorithm().run(graph.adjacency, start);

        assertTrue(result.contains("A=0"), "Expected the start vertex to appear with distance 0");
    }
}
