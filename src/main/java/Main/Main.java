package Main;

import DatabaseConnect.*;
import Dijkstra.*;
import Graph.*;

public class Main
{
    public static void main(String[] args)
    {
        /*
        DatabaseConnect conn = new DatabaseConnect();
        conn.addEdge(1, 2, 7);
        conn.addEdge(1, 4, 3);
        conn.addEdge(2, 3, 3);
        conn.addEdge(2, 4, 2);
        conn.addEdge(2, 5, 6);
        conn.addEdge(3, 4, 4);
        conn.addEdge(3, 5, 1);
        conn.addEdge(4, 5, 7);
        System.out.println("All edges inserted.");
        */
        DatabaseConnect conn = new DatabaseConnect();
        Graph graph = new Graph();
        conn.loadGraph(graph);
        System.out.println(graph.toString());
        Dijkstra dijkstra = new Dijkstra(graph);

        dijkstra.printPath("John Smith", "Becky Jane");
        System.out.println("\nDistance: " + dijkstra.getDistance("John Smith", "Becky Jane") + "0km");
    }
}