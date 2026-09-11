package algorithms;

import org.junit.jupiter.api.Test;
import visualizer.Vertex;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BFSAlgorithmTest {

    @Test
    void visitsEveryReachableVertexInBreadthFirstOrder() {
        SampleGraph graph = new SampleGraph();
        Vertex start = graph.vertex("A");

        String result = new BFSAlgorithm().run(graph.adjacency, start);

        // From A: visit A's neighbors by ascending weight (C=2 before B=5),
        // then each of their unvisited neighbors, level by level.
        assertEquals("BFS : A -> C -> B -> E -> D", result);
    }
}
