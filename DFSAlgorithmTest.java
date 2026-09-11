package algorithms;

import org.junit.jupiter.api.Test;
import visualizer.Vertex;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DFSAlgorithmTest {

    @Test
    void visitsEveryReachableVertexInDepthFirstOrder() {
        SampleGraph graph = new SampleGraph();
        Vertex start = graph.vertex("A");

        String result = new DFSAlgorithm().run(graph.adjacency, start);

        // From A, DFS dives into C (lowest weight, 2) before backtracking to B,
        // then keeps diving from C into E, then D, then D's only unvisited
        // neighbor B - unlike BFS, which explores level by level.
        assertEquals("DFS : A -> C -> E -> D -> B", result);
    }
}
