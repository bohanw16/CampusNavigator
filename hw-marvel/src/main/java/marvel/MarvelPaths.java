package marvel;

import graph.Graph;

import java.util.*;

public class MarvelPaths {

    public static void main(String[] args) {
        Graph<String, String> graph = buildGraph("marvel.csv");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=====================================================================================");
            System.out.println("=====================WELCOME TO MARVEL DATABASE======================================");
            System.out.println("Enter two character name separate by ',' to find the shortest path between them");
            System.out.println("Enter QUIT to quit");
            String s = scanner.nextLine().trim();
            if (s.equals("QUIT")) {break;}
            String[] characters = s.split(",");
            if (characters.length != 2) {
                System.out.println("Invalid Input Format");
                System.out.println("");
                continue;
            }
            String start = characters[0].trim();
            String end = characters[1].trim();
            List<Graph<String, String>.Edge> path = findPath(start, end, graph);
            if (path == null) {
                System.out.println("No path found between " + start + "and " + end);
            } else if (path.isEmpty()) {
                System.out.println("Bad Input: same character");
            } else if (path.get(0).getLabel().equals("unknown")) {
                for (Graph.Edge edge : path) {
                    System.out.println("Non-existing character name: " + edge.getOrigin());
                }
            } else {
                System.out.println("Path from " + start + " to " + end + ":");
                for (Graph.Edge edge : path) {
                    System.out.println("    " + edge.getOrigin() + " ---> " + edge.getDestination() +
                            " in <" + edge.getLabel() + ">");
                }
            }
            System.out.println("");
        }
    }

    /**
     * Build a graph based on data from the CSV file. Each line in the file should be in the form
     * "CharacterName,BookName", which indicates that the character appears in the book.
     * Each node in the graph represents a distinct character, and each edge (Char1, Char2) indicates
     * that the two characters appear in the same book.
     * @param filename - the name of the file to parse
     * @return the resulting graph
     */
    public static Graph<String, String> buildGraph(String filename) {
        MarvelParser mp = new MarvelParser();
        List<Map<String, List<String>>> parsedData = mp.parseData(filename);
        Map<String, List<String>> charToBook = parsedData.get(0);
        Map<String, List<String>> bookToChar = parsedData.get(1);

        Graph<String, String> graph = new Graph<>();
        Set<String> charSet = charToBook.keySet();

        for (String character : charSet) {
            graph.addNode(character);
        }
        for (String book : bookToChar.keySet()) {
            List<String> chars = bookToChar.get(book);
            // for each char, create an edge between it and each other char in the same book
            for (String character : chars) {
                for (String anotherChar : chars) {
                    if (!character.equals(anotherChar)) {
                        graph.addEdge(character, anotherChar, book);
                    }
                }
            }
        }

        return graph;
    }

    /**
     * Find the shortest path from the given start node to end node in graph using BFS,
     * break ties lexicographically first on node labels then on edge labels.
     * @param start - the start node of the path to find
     * @param end - the end node of the path to find
     * @param graph - the graph to find path in
     * @return a List of Edge's representing the shortest path from start to end;
     *          an invalid path with edge(s) with the non-existing node(s) and
     *          label "unknown" if the given node(s) does not exist;
     *          empty List if start and end are the same;
     *          null if no path is found.
     */
    public static List<Graph<String, String>.Edge> findPath(String start, String end, Graph<String, String> graph) {
        Set<String> allNodes = graph.allNodes();

        boolean unknown = false;
        List<Graph<String, String>.Edge> invalidPath = new ArrayList<>();
        Graph<String, String> g1 = new Graph<>();
        if (!allNodes.contains(start)) {
            invalidPath.add(g1.new Edge(start, start, "unknown"));
            unknown = true;
        }
        if (!allNodes.contains(end)) {
            invalidPath.add(g1.new Edge(end, end, "unknown"));
            unknown = true;
        }
        if (unknown) {
            return invalidPath;
        }

        Queue<String> queue = new PriorityQueue<>();
        Map<String, List<Graph<String, String>.Edge>> map = new HashMap<>();
        queue.add(start);
        map.put(start, new ArrayList<>());

        while (!queue.isEmpty()) {
            String nextNode = queue.poll();
            if (nextNode.equals(end)) {
                return map.get(nextNode);
            }
            List<Graph<String, String>.Edge> outgoingEdges = graph.getChildren(nextNode);
            // outgoingEdges.sort(Comparator.comparing(Graph.Edge::getDestination).thenComparing(Graph.Edge::getLabel));
            EdgeComparator edgeComparator = new EdgeComparator();
            outgoingEdges.sort(edgeComparator);
            for (Graph<String, String>.Edge edge : outgoingEdges) {
                String child = edge.getDestination();
                if (!map.containsKey(child)) {
                    List<Graph<String, String>.Edge> path = new ArrayList<>(map.get(nextNode));
                    path.add(edge);
                    map.put(child, path);
                    queue.add(child);
                }
            }
        }

        return null;
    }

    /**
     * Edge Comparator of Graph<String, String> type
     * Compare two edges' first on child nodes, then on labels
     */
    private static class EdgeComparator implements Comparator<Graph<String, String>.Edge> {
        @Override
        public int compare(Graph<String, String>.Edge e1, Graph<String, String>.Edge e2) {
            String dest1 = e1.getDestination();
            String dest2 = e2.getDestination();
            if (dest1.compareTo(dest2) > 0) {
                return 1;
            } else if (dest1.compareTo(dest2) < 0) {
                return -1;
            } else {
                return e1.getLabel().compareTo(e2.getLabel());
            }
        }
    }
}
