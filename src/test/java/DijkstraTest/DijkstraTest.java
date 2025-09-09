package DijkstraTest;

import org.junit.Test;

import Graph.Graph;
import Dijkstra.Dijkstra;

import static org.junit.Assert.*;

public class DijkstraTest
{
    @Test
    public void test1emptyGraph()
    {
        Graph graph = new Graph();

        // assertThrows checks if an IllegalArgumentException is thrown when creating Dijkstra
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            new Dijkstra(graph);
        });

        // Verify that the correct error message is thrown
        assertEquals("Graph empty", exception.getMessage());
    }

    @Test
    public void test2smallDirectedGraphgetPath()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("A", "C", 2);
        graph.add("B", "C", 3);
        graph.add("B", "D", 7);
        graph.add("B", "E", 4);
        graph.add("C", "D", 3);
        graph.add("D", "E", 1);
        graph.add("D", "F", 2);
        graph.add("E", "F", 5);
        graph.add("E", "G", 7);
        graph.add("F", "G", 5);

        Dijkstra dijkstra = new Dijkstra(graph);

        String path = "";
        for(String node: dijkstra.getPath("A", "G"))
            path += node;

        assertEquals("Test 2: getPath() on a small directed graph", "ACDFG", path);
    }

    @Test
    public void test3smallDirectedGraphgetDistance()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("A", "C", 2);
        graph.add("B", "C", 3);
        graph.add("B", "D", 7);
        graph.add("B", "E", 4);
        graph.add("C", "D", 3);
        graph.add("D", "E", 1);
        graph.add("D", "F", 2);
        graph.add("E", "F", 5);
        graph.add("E", "G", 7);
        graph.add("F", "G", 5);

        Dijkstra dijkstra = new Dijkstra(graph);

        assertEquals("Test 3: getDistance() on a small directed graph", 12, dijkstra.getDistance("A", "G"));
    }

    @Test
    public void test4smallUndirectedGraphgetPath()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("B", "A", 4);

        graph.add("A", "C", 2);
        graph.add("C", "A", 2);

        graph.add("B", "C", 3);
        graph.add("C", "B", 3);

        graph.add("B", "D", 7);
        graph.add("D", "B", 7);

        graph.add("B", "E", 4);
        graph.add("E", "B", 4);

        graph.add("C", "D", 3);
        graph.add("D", "C", 3);

        graph.add("D", "E", 1);
        graph.add("E", "D", 1);

        graph.add("D", "F", 2);
        graph.add("F", "D", 2);

        graph.add("E", "F", 5);
        graph.add("F", "E", 5);

        graph.add("E", "G", 7);
        graph.add("G", "E", 7);

        graph.add("F", "G", 5);
        graph.add("G", "F", 5);

        Dijkstra dijkstra = new Dijkstra(graph);

        String path = "";
        for(String node: dijkstra.getPath("A", "G"))
            path += node;

        assertEquals("Test 4: getPath() on a small undirected graph", "ACDFG", path);
    }

    public void test3smallUndirectedGraphgetDistance()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("B", "A", 4);

        graph.add("A", "C", 2);
        graph.add("C", "A", 2);

        graph.add("B", "C", 3);
        graph.add("C", "B", 3);

        graph.add("B", "D", 7);
        graph.add("D", "B", 7);

        graph.add("B", "E", 4);
        graph.add("E", "B", 4);

        graph.add("C", "D", 3);
        graph.add("D", "C", 3);

        graph.add("D", "E", 1);
        graph.add("E", "D", 1);

        graph.add("D", "F", 2);
        graph.add("F", "D", 2);

        graph.add("E", "F", 5);
        graph.add("F", "E", 5);

        graph.add("E", "G", 7);
        graph.add("G", "E", 7);

        graph.add("F", "G", 5);
        graph.add("G", "F", 5);

        Dijkstra dijkstra = new Dijkstra(graph);

        assertEquals("Test 5: getDistance() on a small undirected graph", 12, dijkstra.getDistance("A", "G"));
    }

    @Test
    public void test6largeDirectedGraphgetPath()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("A", "C", 5);
        graph.add("A", "D", 8);
        graph.add("B", "D", 3);
        graph.add("B", "E", 12);
        graph.add("C", "D", 1);
        graph.add("C", "F", 11);
        graph.add("D", "E", 9);
        graph.add("D", "F", 4);
        graph.add("E", "G", 6);
        graph.add("E", "H", 10);
        graph.add("F", "G", 5);
        graph.add("F", "I", 11);
        graph.add("G", "H", 3);
        graph.add("G", "I", 5);
        graph.add("G", "J", 15);
        graph.add("H", "J", 14);
        graph.add("I", "J", 8);


        Dijkstra dijkstra = new Dijkstra(graph);

        String path = "";
        for(String node: dijkstra.getPath("A", "J"))
            path += node;

        assertEquals("Test 6: getPath() on a large directed graph", "ACDFGIJ", path);
    }

    @Test
    public void test7largeDirectedGraphgetDistance()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("A", "C", 5);
        graph.add("A", "D", 8);
        graph.add("B", "D", 3);
        graph.add("B", "E", 12);
        graph.add("C", "D", 1);
        graph.add("C", "F", 11);
        graph.add("D", "E", 9);
        graph.add("D", "F", 4);
        graph.add("E", "G", 6);
        graph.add("E", "H", 10);
        graph.add("F", "G", 5);
        graph.add("F", "I", 11);
        graph.add("G", "H", 3);
        graph.add("G", "I", 5);
        graph.add("G", "J", 15);
        graph.add("H", "J", 14);
        graph.add("I", "J", 8);


        Dijkstra dijkstra = new Dijkstra(graph);

        assertEquals("Test 7: getDistance() on a large directed graph", 28, dijkstra.getDistance("A", "J"));
    }

    @Test
    public void test8largeUndirectedGraphgetPath()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("B", "A", 4);
        graph.add("A", "C", 5);
        graph.add("C", "A", 5);
        graph.add("A", "D", 8);
        graph.add("D", "A", 8);
        graph.add("B", "D", 3);
        graph.add("D", "B", 3);
        graph.add("B", "E", 12);
        graph.add("E", "B", 12);
        graph.add("C", "D", 1);
        graph.add("D", "C", 1);
        graph.add("C", "F", 11);
        graph.add("F", "C", 11);
        graph.add("D", "E", 9);
        graph.add("E", "D", 9);
        graph.add("D", "F", 4);
        graph.add("F", "D", 4);
        graph.add("E", "G", 6);
        graph.add("G", "E", 6);
        graph.add("E", "H", 10);
        graph.add("H", "E", 10);
        graph.add("F", "G", 5);
        graph.add("G", "F", 5);
        graph.add("F", "I", 11);
        graph.add("I", "F", 11);
        graph.add("G", "H", 3);
        graph.add("H", "G", 3);
        graph.add("G", "I", 5);
        graph.add("I", "G", 5);
        graph.add("G", "J", 15);
        graph.add("J", "G", 15);
        graph.add("H", "J", 14);
        graph.add("J", "H", 14);
        graph.add("I", "J", 8);
        graph.add("J", "I", 8);


        Dijkstra dijkstra = new Dijkstra(graph);

        String path = "";
        for(String node: dijkstra.getPath("A", "J"))
            path += node;

        assertEquals("Test 8: getPath() on a large undirected graph", "ACDFGIJ", path);
    }

    @Test
    public void test9largeUndirectedGraphgetDistance()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("B", "A", 4);
        graph.add("A", "C", 5);
        graph.add("C", "A", 5);
        graph.add("A", "D", 8);
        graph.add("D", "A", 8);
        graph.add("B", "D", 3);
        graph.add("D", "B", 3);
        graph.add("B", "E", 12);
        graph.add("E", "B", 12);
        graph.add("C", "D", 1);
        graph.add("D", "C", 1);
        graph.add("C", "F", 11);
        graph.add("F", "C", 11);
        graph.add("D", "E", 9);
        graph.add("E", "D", 9);
        graph.add("D", "F", 4);
        graph.add("F", "D", 4);
        graph.add("E", "G", 6);
        graph.add("G", "E", 6);
        graph.add("E", "H", 10);
        graph.add("H", "E", 10);
        graph.add("F", "G", 5);
        graph.add("G", "F", 5);
        graph.add("F", "I", 11);
        graph.add("I", "F", 11);
        graph.add("G", "H", 3);
        graph.add("H", "G", 3);
        graph.add("G", "I", 5);
        graph.add("I", "G", 5);
        graph.add("G", "J", 15);
        graph.add("J", "G", 15);
        graph.add("H", "J", 14);
        graph.add("J", "H", 14);
        graph.add("I", "J", 8);
        graph.add("J", "I", 8);


        Dijkstra dijkstra = new Dijkstra(graph);

        assertEquals("Test 9: getDistance() on a large undirected graph", 28, dijkstra.getDistance("A", "J"));
    }

    @Test
    public void test10changeStartNodeForLargeUndirectedGraph()
    {
        Graph graph = new Graph();

        graph.add("A", "B", 4);
        graph.add("B", "A", 4);
        graph.add("A", "C", 5);
        graph.add("C", "A", 5);
        graph.add("A", "D", 8);
        graph.add("D", "A", 8);
        graph.add("B", "D", 3);
        graph.add("D", "B", 3);
        graph.add("B", "E", 12);
        graph.add("E", "B", 12);
        graph.add("C", "D", 1);
        graph.add("D", "C", 1);
        graph.add("C", "F", 11);
        graph.add("F", "C", 11);
        graph.add("D", "E", 9);
        graph.add("E", "D", 9);
        graph.add("D", "F", 4);
        graph.add("F", "D", 4);
        graph.add("E", "G", 6);
        graph.add("G", "E", 6);
        graph.add("E", "H", 10);
        graph.add("H", "E", 10);
        graph.add("F", "G", 5);
        graph.add("G", "F", 5);
        graph.add("F", "I", 11);
        graph.add("I", "F", 11);
        graph.add("G", "H", 3);
        graph.add("H", "G", 3);
        graph.add("G", "I", 5);
        graph.add("I", "G", 5);
        graph.add("G", "J", 15);
        graph.add("J", "G", 15);
        graph.add("H", "J", 14);
        graph.add("J", "H", 14);
        graph.add("I", "J", 8);
        graph.add("J", "I", 8);

        Dijkstra dijkstra = new Dijkstra(graph);

        dijkstra.getDistance("J", "A");

        assertEquals("Test 10: getDistance() on a on a large undirected graph from J to A, then A to J", 28, dijkstra.getDistance("A", "J"));
    }
}