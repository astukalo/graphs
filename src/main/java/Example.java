import xyz.a5s7.graph.DirectedGraph;
import xyz.a5s7.graph.Edge;
import xyz.a5s7.graph.Graph;
import xyz.a5s7.graph.UndirectedGraph;

public class Example {
    public static void main(String[] args) {
        Graph<Integer, Edge<Integer>> ugraph = new DirectedGraph<>();
        for (int i = 1; i <= 5; i++) {
            ugraph.addVertex(i);
        }
        ugraph.addEdge(new Edge<>(1, 3));
        ugraph.addEdge(new Edge<>(2, 3));
        ugraph.addEdge(new Edge<>(2, 5));
        ugraph.addEdge(new Edge<>(3, 4));
        ugraph.addEdge(new Edge<>(4, 5));
        ugraph.addEdge(new Edge<>(5, 4));
        System.out.println(ugraph.getPath(1, 5));

        Graph<String, Edge<String>> dgraph = new UndirectedGraph<>();
        for (int i = 1; i <= 5; i++) {
            dgraph.addVertex(Integer.toString(i));
        }

        dgraph.addEdge(new Edge<>("1", "3"));
        dgraph.addEdge(new Edge<>("2", "3"));
        dgraph.addEdge(new Edge<>("3", "4"));
        dgraph.addEdge(new Edge<>("4", "5"));
        dgraph.addEdge(new Edge<>("5", "4"));
        dgraph.addEdge(new Edge<>("5", "2"));

        System.out.println(dgraph.getPath("5", "1"));
    }
}
