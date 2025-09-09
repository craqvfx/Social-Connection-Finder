package Dijkstra;

/*
Interface for Dijkstra class
Reports shortest path and distance through getPath() and getDistance() functions
 */
public interface IDijkstra
{
    public String[] getPath(String startNode, String endNode);

    public int getDistance(String startNode, String endNode);
}
