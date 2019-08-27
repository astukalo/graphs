package xyz.a5s7.graph;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectedGraphTest {
    DirectedGraph<TestNode, Edge<TestNode>> graph;

    @Before
    public void setUp() {
        graph = new DirectedGraph<>();
    }

    @Test
    public void addVertex() {
        TestNode v = new TestNode("a");
        graph.addVertex(v);
        graph.addVertex(new TestNode("a"));

        assertThat(graph.getVertices()).hasSize(2);
        assertThat(graph.getVertices()).containsOnlyOnce(v);
    }

    @Test
    public void vertexMustNotBeAddedTwice() {
        TestNode v = new TestNode("a");
        assertThat(graph.addVertex(v)).isTrue();
        assertThat(graph.addVertex(v)).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void nullVertexNotAllowed() {
        assertThat(graph.addVertex(null)).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void nullEdgeNotAllowed() {
        graph.addEdge(null);
    }

    @Test
    public void addsEdge() {
        TestNode a = new TestNode("a");
        TestNode b = new TestNode("b");
        TestNode c = new TestNode("c");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        assertThat(graph.addEdge(new Edge(a, b))).isTrue();
        assertThat(graph.addEdge(new Edge(b, a))).isTrue();
        assertThat(graph.addEdge(new Edge(a, c))).isTrue();
        assertThat(graph.getEdges(a)).containsOnlyOnce(new Edge<>(a, b), new Edge<>(a, c));
        assertThat(graph.getEdges(b)).containsOnlyOnce(new Edge<>(b, a));
        assertThat(graph.getEdges(c)).isEmpty();
    }

    @Test
    public void parallelEdgeNotAllowed() {
        TestNode a = new TestNode("a");
        TestNode b = new TestNode("b");
        graph.addVertex(a);
        graph.addVertex(b);
        assertThat(graph.addEdge(new Edge(a, b))).isTrue();
        assertThat(graph.addEdge(new Edge(a, b))).isFalse();
    }

    @Test
    public void edgeCantBeAddedIfNoVertex() {
        TestNode a = new TestNode("a");
        TestNode c = new TestNode("c");
        graph.addVertex(c);

        try {
            graph.addEdge(new Edge(a, c));
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(graph.getEdges(c)).isEmpty();
        }

        try {
            graph.addEdge(new Edge(c, a));
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(graph.getEdges(c)).isEmpty();
        }
    }

    @Test
    public void mustntGetPathVertexNotExist() {
        TestNode a = new TestNode("a");
        TestNode c = new TestNode("c");
        graph.addVertex(c);

        try {
            graph.getPath(a, c);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            graph.getPath(c, a);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void mustntGetPathIfVertexNull() {
        TestNode a = new TestNode("a");

        try {
            graph.getPath(a, null);
            Assert.fail();
        } catch (NullPointerException e) {
        }

        try {
            graph.getPath(null, a);
            Assert.fail();
        } catch (NullPointerException e) {
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void mustntGetPathIfFromSameAsTo() {
        TestNode a = new TestNode("a");
        graph.addVertex(a);
        graph.getPath(a, a);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void findsPath() {
        /*
           +---+          +--+
           | 1 |      +---+2 +----+
           +-+-+      |   +--+    |
             |        |           |
             |        v           |
             |      +-+-+         |
             +----->+ 3 |         |
                    +-+-+         |
                      |           |
                      |           v
          +----<------+         +-+-+
          | 4 +---------------->+ 5 |
          +---+                 +---+
         */
        DirectedGraph<Integer, Edge<Integer>> graph = new DirectedGraph<>();
        for (int i = 1; i <= 5; i++) {
            graph.addVertex(i);
        }
        graph.addEdge(new Edge<>(1, 3));
        graph.addEdge(new Edge<>(2, 3));
        graph.addEdge(new Edge<>(2, 5));
        graph.addEdge(new Edge<>(3, 4));
        graph.addEdge(new Edge<>(4, 5));
        graph.addEdge(new Edge<>(5, 4));
        assertThat(graph.getPath(2, 4)).containsExactly(new Edge<>(2, 3), new Edge<>(3, 4));
        assertThat(graph.getPath(1, 5)).containsExactly(new Edge<>(1, 3), new Edge<>(3, 4), new Edge<>(4, 5));
        assertThat(graph.getPath(1, 2)).isEmpty();
    }

    @Test
    public void noPath() {
        TestNode a = new TestNode("a");
        TestNode c = new TestNode("c");
        graph.addVertex(a);
        graph.addVertex(c);
        assertThat(graph.getPath(a, c)).isEmpty();
    }
}