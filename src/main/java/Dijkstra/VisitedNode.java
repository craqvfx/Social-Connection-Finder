package Dijkstra;

/* 
Represents a node that has been completely traversed by dijkstra
Previous pointer also makes it part of a tree in which each node can trace its shortest path to the root (startNode) via the previous pointer
// TODO: add tree stuff to design?
*/ 

public class VisitedNode
{
    private String name; // Name of node
    private int nodeDistance; // Distance to node from startNode in visitedNodeList
    private VisitedNode previous; // Points to previous node in the shortest distance path

    public VisitedNode(String name, int distance, VisitedNode previous)
    {
        this.name = name;
        this.nodeDistance = distance;
        this.previous = previous;
    }

    public String getName()
    {
        return name;
    }

    public int getNodeDistance()
    {
        return nodeDistance;
    }

    public void setNodeDistance(int nodeDistance)
    {
        this.nodeDistance = nodeDistance;
    }

    public VisitedNode getPrevious()
    {
        return previous;
    }

    public void setPrevious(VisitedNode previous)
    {
        this.previous = previous;
    }
}
