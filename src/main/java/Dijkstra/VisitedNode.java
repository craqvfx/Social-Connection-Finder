package Dijkstra;

//node in a tree in which each node can trace its shortest path to the root (startNode) via the previous pointer
public class VisitedNode
{
    private String name;//name of node
    private int nodeDistance;//distance to node from startNode in visitedNodeList
    private VisitedNode previous;//points to previous node in the shortest distance path

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
