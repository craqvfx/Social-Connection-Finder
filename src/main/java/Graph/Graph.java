package Graph;

import java.util.ArrayList;

//directed graph
public class Graph
{
    private ArrayList<Node> nodeList = new ArrayList<Node>();//list of all nodes in the graph

    @Override
    public String toString()
    {
        String output = "GRAPH = {\n";

        for(int i = 0; i <nodeList.size(); i++)
        {
            output += "'" + nodeList.get(i).getNodeName() + "':";

            output += nodeList.get(i).toString();

            output += nodeList.size() - 1 > i ? ",\n" : "\n";
        }

        output += "}";
        return output;
    }

    //checks if graph is empty
    public boolean isEmpty()
    {
        return nodeList.size() == 0;
    }

    //adds a node to the graph
    public void add(String sourceNode, String destinationNode, int weight)
    {
        Edge edge = new Edge(destinationNode, weight);

        //add outgoing edge
        if(getIndex(sourceNode) != -1)
        {//if node already exists, add edge
            nodeList.get(getIndex(sourceNode)).addEdge(edge);
        }
        else
        {//create node and then add edge
            Node node = new Node(sourceNode);
            node.addEdge(edge);
            nodeList.add(node);
        }

        //if destinationNode is not already in the graph, add it
        if(getIndex(destinationNode) == -1)
        {//create node and then add edge
            Node node = new Node(destinationNode);
            nodeList.add(node);
        }

        //makes it an undirected graph by adding the reverse edge
        Edge reverseEdge = new Edge(sourceNode, weight);
        nodeList.get(getIndex(destinationNode)).addEdge(reverseEdge);
    }

    //returns index of nodeName
    private int getIndex(String nodeName)
    {
        //search for nodeName in nodeList
        for(int i = 0; i < nodeList.size(); i++)
        {
            //if node is present, return the index thereof
            if(nodeList.get(i).getNodeName().equals(nodeName))
            {
                return i;
            }
        }

        //if not present, return -1
        return -1;
    }

    //return array of all nodes
    public String[] getNodes()
    {
        String[] nodeNameList = new String[nodeList.size()];

        //iterate through list and add each nodeName to the array
        for(int i = 0; i < nodeList.size(); i++)
        {
            nodeNameList[i] = nodeList.get(i).getNodeName();
        }

        return nodeNameList;
    }

    //return array of all neighbours of a node
    public String[] getNeighbours(String nodeName)
    {
        return nodeList.get(getIndex(nodeName)).getNeighbours();
    }

    //gets distance between two nodes. if they don't share an edge, throws error
    public int getEdgeDistance(String start, String end)
    {
        return nodeList.get(getIndex(start)).getEdgeWeight(end);
    }
}
