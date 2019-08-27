package xyz.a5s7.graph;

import java.util.*;

import static java.util.Objects.requireNonNull;

public abstract class AbstractGraph<V, E extends Edge<V>> implements Graph<V, E> {
    protected final Map<V, Collection<E>> adjMap;

    public AbstractGraph() {
        adjMap = new HashMap<>();
    }

    protected Set<V> getVertices() {
        return Collections.unmodifiableSet(adjMap.keySet());
    }

    /**
     * {@inheritDoc}
     *
     * May not return optimal path.
     * Doesn't not return cycle.
     */
    @Override
    public List<E> getPath(V from, V to) {
        requireNonNull(from);
        requireNonNull(to);
        requireExist(from);
        requireExist(to);

        if (from.equals(to)) {
            throw new IllegalArgumentException("Path can't contain the same vertex");
        }

        Queue<LinkedList<E>> q = new LinkedList<>();
        Set<V> visited = new HashSet<>();

        V current = from;
        LinkedList<E> path = null;
        do {
            LinkedList<E> finalPath = path;
            getEdges(current)
                    .stream()
                    .filter(edge -> !visited.contains(edge.getTo()))
                    //.peek(edge -> traverse.accept(edge.getTo()))
                    .peek(edge -> visited.add(edge.getTo()))
                    .map(edge -> newPath(finalPath, edge))
                    .forEachOrdered(q::offer)
            ;

            path = q.poll();
            if (path != null) {
                current = path.getLast().getTo();
                if (to.equals(current)) {
                    return path;
                }
            }
        } while (path != null);

        return Collections.emptyList();
    }

    protected void requireExist(V vertex) {
        if (!adjMap.containsKey(vertex)) {
            throw new IllegalArgumentException("Unknown vertex " + vertex);
        }
    }

    private LinkedList<E> newPath(final LinkedList<E> original, final E edge) {
        LinkedList<E> p;
        if (original == null) {
            p = new LinkedList<>();
        } else {
            p = new LinkedList<>(original);
        }
        p.add(edge);
        return p;
    }

    protected Collection<E> getEdges(V vertex) {
        Collection<E> edges = adjMap.get(vertex);
        if (edges == null) {
            throw new IllegalArgumentException("Unknown vertex " + vertex);
        }
        return edges;
    }

    @Override
    public boolean addVertex(V v) {
        requireNonNull(v);
        return adjMap.putIfAbsent(v, new HashSet<>()) == null;
    }
}
