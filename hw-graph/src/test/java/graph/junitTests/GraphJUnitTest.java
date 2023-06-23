package graph.junitTests;

import graph.Graph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class GraphJUnitTest {

    Graph<String, String>.Edge e1 = new Graph<String, String>().new Edge("n1", "n2", "e1");

    // Node not exist
    @Test
    public void testNodeNotExist() {
        Graph<String, String> graph = new Graph<>();
        assertNull(graph.getChildren("n1"));
    }

    // Duplicate node
    @Test
    public void testDuplicateNode() {
        Graph<String, String> graph = new Graph<>();
        graph.addNode("n1");
        assertFalse(graph.addNode("n1"));
    }

    // Duplicate edge
    @Test
    public void testDuplicateEdge() {
        Graph<String, String> graph = new Graph<>();
        graph.addNode("n1");
        graph.addEdge("n1", "n1", "e1");
        assertFalse(graph.addEdge("n1", "n1", "e1"));
    }

    // Parent node not exist when adding edge
    @Test
    public void testParentNotExist() {
        Graph<String, String> graph = new Graph<>();
        graph.addNode("n1");
        assertFalse(graph.addEdge("n2", "n1", "e1"));
    }

    // Child node not exist when adding edge
    @Test
    public void testChildNotExist() {
        Graph<String, String> graph = new Graph<>();
        graph.addNode("n1");
        assertFalse(graph.addEdge("n1", "n2", "e1"));
    }

    // Get origin node
    @Test
    public void testGetOrigin() {
        assertEquals("n1", e1.getOrigin());
    }

    // Get destination node
    @Test
    public void testGetDestination() {
        assertEquals("n2", e1.getDestination());
    }

    // Get edge label
    @Test
    public void testGetLabel() {
        assertEquals("e1", e1.getLabel());
    }
}
