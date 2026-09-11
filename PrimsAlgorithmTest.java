package algorithms;

import org.junit.jupiter.api.Test;
import visualizer.Vertex;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimsAlgorithmTest {

    @Test
    void buildsAMinimumSpanningTreeFromTheStartVertex() {
        SampleGraph graph = new SampleGraph();
        Vertex start = graph.vertex("A");

        String result = new PrimsAlgorithm().run(graph.adjacency, start);

        // Grows the tree one cheapest edge at a time: A-C(2), C-E(1), A-B(5), B-D(3)
        assertEquals("Prim : B=A, C=A, D=B, E=C", result);
    }
}
