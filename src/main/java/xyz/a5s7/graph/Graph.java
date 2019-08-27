package xyz.a5s7.graph;

import java.util.List;

public interface Graph<V, E extends Edge<V>> {
    /**
     * Returns a list of edges between 2 vertices.
     *
     * @param from vertex to start
     * @param to vertex to finish
     * @return a list of edges between 2 vertices
     * @throws IllegalArgumentException if any vertex doesn't exist
     */
    List<E> getPath(V from, V to);

    /**
     * Adds vertex to the graph.
     *
     * @param v vertex to be added to this graph
     * @return true, if added
     */
    boolean addVertex(V v);

    /**
     * Adds edge to the graph.
     *
     * @param edge edge to be added to this graph
     * @return true, if added
     * @throws IllegalArgumentException if any vertex of an edge doesn't exist
     */
    boolean addEdge(E edge);
}
