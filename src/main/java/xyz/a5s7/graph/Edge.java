package xyz.a5s7.graph;

import java.util.Objects;

/**
 * Can be extended to weighted edge
 */
public class Edge<V> {
    private final V from;
    private final V to;

    public Edge(V from, V to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        this.from = from;
        this.to = to;
    }

    public V getFrom() {
        return from;
    }

    public V getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge<?> edge = (Edge<?>) o;
        return from.equals(edge.from) &&
                to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    /**
     * Inverts edge for undirected graphs
     * @return inverted edge
     */
    public <E extends Edge<V>> E invert() {
        return (E) new Edge(to, from);
    }
}
