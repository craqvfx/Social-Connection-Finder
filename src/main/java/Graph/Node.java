package Graph;

import java.util.ArrayList;

//used to store a list of edges that a node has
public class Node
{
    private String nodeName;//name of the node
    private ArrayList<Edge> adjacencyList = new ArrayList<Edge>();//stores all edges to this node

    //constructor to set nodeName
    public Node(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public void addEdge(Edge edge)
    {
        adjacencyList.add(edge);
    }

    @Override
    public String toString()
    {
        String output = "{";

        for(int i = 0; i <adjacencyList.size(); i++)
        {
            output += adjacencyList.get(i).toString();

            if(i<adjacencyList.size() - 1)
            {
                output += ",";
            }
        }

        output += "}";
        return output;
    }

    public String getNodeName()
    {
        return nodeName;
    }

    //gets weight of edge between two nodes, if edge not present it throws an error
    public int getEdgeWeight(String destination)
    {
        //searches through the nodes to find the destination, then returns the distance to it
        for(int i = 0; i < adjacencyList.size(); i++)
        {
            if(adjacencyList.get(i).getDestination() == destination)
            {
                return adjacencyList.get(i).getWeight();
            }
        }

        throw new IllegalArgumentException("Edge not present");
    }

    //returns a string array of the nodes neighbours
    public String[] getNeighbours()
    {
        String[] neighbours = new String[adjacencyList.size()];

        //add all edges to the array
        for(int i = 0; i < adjacencyList.size(); i++)
        {
            neighbours[i] = adjacencyList.get(i).getDestination();
        }

        return neighbours;
    }
}