package xyz.a5s7.graph;

import org.assertj.core.api.Condition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;

public class UndirectedGraphTest {
    UndirectedGraph<TestNode, Edge<TestNode>> graph;

    @Before
    public void setUp() {
        graph = new UndirectedGraph<>();
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
        assertThat(graph.addEdge(new Edge(b, a))).isFalse();
        assertThat(graph.addEdge(new Edge(a, c))).isTrue();
        assertThat(graph.getEdges(a)).containsOnlyOnce(new Edge<>(a, b), new Edge<>(a, c));
        assertThat(graph.getEdges(b)).containsOnlyOnce(new Edge<>(b, a));
        assertThat(graph.getEdges(c)).containsExactly(new Edge<>(c, a));
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
             |        |           |
             |      +-+-+         |
             +------+ 3 |         |
                    +-+-+         |
                      |           |
                      |           |
          +-----------+         +-+-+
          | 4 +-----------------+ 5 |
          +---+                 +---+
         */
        Graph<Integer, Edge<Integer>> graph = new UndirectedGraph<>();
        for (int i = 1; i <= 5; i++) {
            graph.addVertex(i);
        }
        graph.addEdge(new Edge<>(1, 3));
        graph.addEdge(new Edge<>(2, 3));
        graph.addEdge(new Edge<>(3, 4));
        graph.addEdge(new Edge<>(4, 5));
        graph.addEdge(new Edge<>(5, 4));
        graph.addEdge(new Edge<>(5, 2));

        Condition<? super List<? extends Edge<Integer>>> path1 = new Condition<>(edges -> {
            ArrayList<Object> p1 = new ArrayList<>();
            p1.add(new Edge<>(5, 4));
            p1.add(new Edge<>(4, 3));
            p1.add(new Edge<>(3, 1));
            return edges.containsAll(p1);
        }, "p1");

        Condition<? super List<? extends Edge<Integer>>> path2 = new Condition<>(edges -> {
            ArrayList<Object> p1 = new ArrayList<>();
            p1.add(new Edge<>(5, 2));
            p1.add(new Edge<>(2, 3));
            p1.add(new Edge<>(3, 1));
            return edges.containsAll(p1);
        }, "p2");
        assertThat(graph.getPath(5, 1)).is(
            anyOf(path1, path2)
        );
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