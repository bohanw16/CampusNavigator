package graph;

import java.util.*;

/**
 * This class represents a mutable, directed graph
 *
 * A graph is a collection of nodes and edges,
 * Each node has a distinct label,
 * A node can have edges connecting to other nodes,
 * An edge has direction, from the origin node (parent) to the destination node (child),
 * Edges with the same parent and child nodes have unique labels,
 * A node can have no edge, which means it has no neighbor,
 * A node can have an edge pointing to itself,
 * A graph can be empty.
 */
public class Graph<E, T> {

    Map<E, Set<Edge>> nodeSet;
    public static final boolean DEBUG = false;

    // Abstraction Function:
    // AF(this) = a graph where each of its node n is a key in nodeSet,
    //          and each of n's outgoing edges is an element of n's corresponding value in nodeSet,
    //          which is a Set of Edge's.

    // Representation Invariant:
    // nodeSet != null
    // Every key and value in nodeSet != null
    // No node has the same label as any other node
    // No node has two outgoing edges with the same label
    // Every edge has an origin node and a destination node

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (nodeSet != null): "nodeSet is null";

        if (DEBUG) {  // for debugging
            for (E node : nodeSet.keySet()) {
                assert (nodeSet.get(node) != null) : "entry of nodeSet is null";
            }
        }
        // Repetitive node labels and edge labels are checked implicitly in addNode() and addEdge()
    }

    /**
     * @spec.effects construct an empty Graph
     */
    public Graph() {
        nodeSet = new HashMap<>();
        checkRep();
    }

    /**
     * Return a set of all nodes in this graph
     * @return a set containing all nodes in the graph
     */
    public Set<E> allNodes() {
        checkRep();
        return nodeSet.keySet();
    }

    /**
     * Get all outgoing edges of the given parent node
     * @param parent - The parent of the children nodes to find
     * @return a list containing all outgoing edges from the given parent node if the parent node exists,
     *          null o/w
     * @spec.requires parent != null
     */
    public List<Edge> getChildren(E parent) {
        checkRep();
        if (!nodeSet.containsKey(parent)) {
            return null;
        }

        List<Edge> result = new ArrayList<>(nodeSet.get(parent));
        checkRep();
        return result;
    }

    /**
     * Add a new node to the graph if no other identical node exists
     * @param node - the label of the new node
     * @return true if node added successfully, false otherwise
     * @spec.effects add a new node to the graph
     * @spec.modifies this
     * @spec.requires node != null
     */
    public boolean addNode(E node) {
        checkRep();
        if (nodeSet.containsKey(node)) {
            return false;
        }
        nodeSet.put(node, new HashSet<>());
        checkRep();
        return true;
    }

    /**
     * Add a new edge to the graph if the given origin and destination nodes exist
     *          and no other identical edge exists
     * @param origin - the origin node of the new edge
     * @param destination - the destination node of the new edge
     * @param label - the label of the new edge
     * @return true if edge added successfully, false otherwise
     * @spec.effects add a new edge to the graph
     * @spec.modifies this
     * @spec.requires origin, destination and label != null
     */
    public boolean addEdge(E origin, E destination, T label) {
        checkRep();
        if (!nodeSet.containsKey(origin) || !nodeSet.containsKey(destination)) {
            return false;
        }
        Edge newEdge = new Edge(origin, destination, label);
        if (nodeSet.get(origin).contains(newEdge)) {
            return false;
        }
        nodeSet.get(origin).add(newEdge);
        checkRep();
        return true;
    }


    /**
     * This class represents an immutable edge between two nodes,
     *
     * An edge has an origin node and a destination node,
     * An edge's label is unique between two nodes,
     * An edge has a label,
     * An outgoing edge of a node is edge from node to its child,
     */
    public class Edge {

        E origin;
        E destination;
        T label;

        // Abstraction Function:
        // AF(r) = an edge from node origin to node destination with label label.

        // Representation Invariant:
        // Origin node != null
        // Destination node != null
        // Label != null

        /**
         * Throws an exception if the representation invariant is violated.
         */
        private void checkRep() {
            assert(this.origin != null): "edge's origin is null";
            assert(this.destination != null): "edge's destination is null";
            assert(this.label != null): "edge's label is null";
        }

        /**
         * Construct a new edge with given origin, destination and label
         * @param origin - the origin node of this edge
         * @param destination - the destination node of this edge
         * @param label - the label of this edge
         * @spec.effects construct a new edge
         * @spec.requires origin and destination can't be null
         */
        public Edge(E origin, E destination, T label) {
            this.origin = origin;
            this.destination = destination;
            this.label = label;
            checkRep();
        }

        /**
         * Get the origin node of this edge
         * @return the origin node of this edge
         */
        public E getOrigin() {
            checkRep();
            return this.origin;
        }

        /**
         * Get the destination node of this edge
         * @return the destination node of this edge
         */
        public E getDestination() {
            checkRep();
            return this.destination;
        }

        /**
         * Get the label of this edge
         * @return the label of this edge
         */
        public T getLabel() {
            checkRep();
            return this.label;
        }

        /**
         * Standard equality operation.
         * @param o - the object to be compared for equality
         * @return true if and only if o is an instance of Edge and this and o have
         *          the same origin, destination and label, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
//            if (o == null || this.getClass() != o.getClass()) return false;
            if (!(o instanceof Graph<?, ?>.Edge)) {
                return false;
            }
            Graph<?, ?>.Edge other = (Graph<?, ?>.Edge) o;
            return this.origin.equals(other.origin) && this.destination.equals(other.destination) &&
                    this.label.equals(other.label);
        }

        /**
         * Standard hashCode function.
         * @return an int that all objects equal to this will also return
         */
        @Override
        public int hashCode() {
            return Objects.hash(origin, destination, label);
        }
    }
}
