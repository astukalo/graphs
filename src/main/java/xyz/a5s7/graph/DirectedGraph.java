package xyz.a5s7.graph;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class DirectedGraph<V, E extends Edge<V>> extends AbstractGraph<V, E>{
    /**
     * {@inheritDoc}
     *
     * Parallel edges not allowed.
     */
    @Override
    public boolean addEdge(E edge) {
        requireNonNull(edge);

        Collection<E> edgesFrom = getEdges(edge.getFrom());
        requireExist(edge.getTo());

        return edgesFrom.add(edge);
    }
}
