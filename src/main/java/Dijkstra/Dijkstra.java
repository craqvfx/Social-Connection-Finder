package Dijkstra;

import PriorityQueue.PriorityQueue;
import Graph.Graph;

import java.util.ArrayList;

/*
Dijkstra object can be created to store a graph and find the shortest distance between two points in the graph
Reports shortest path and distance through getPath() and getDistance() functions

Works by building a tree of nodes, with each node pointing to its parent, which is the previous node in the shortest distance path,
this way once graph is traversed from startNode, the tree built can be used to find shortest distance to any endNode,
however if startNode changes, graph must be traversed again and tree must be rebuilt
*/
public class Dijkstra implements IDijkstra
{
    Graph graph; //graph to be operated upon, initialised upon construction
    String startNode;
    private ArrayList<VisitedNode> visitedNodeList = new ArrayList<VisitedNode>();//holds all visited nodes

    //constructor, stores graph and throws error if it's empty
    public Dijkstra(Graph graph)
    {
        if(graph.isEmpty())
        {
            throw new IllegalArgumentException("Graph empty");
        }

        this.graph = graph;
    }

    //helper function to find the shortest distance between startNode and every other node in the graph, updates visitedNodeList with results
    //builds tree
    private void traverseDijkstra(String startNode)
    {
        //clear previous state and set the starting node
        visitedNodeList.clear();
        this.startNode = startNode;

        //initialize the priority queue:
        //start node gets distance 0, all others are set to "infinity" (Integer.MAX_VALUE)
        PriorityQueue pQueue = new PriorityQueue();
        String[] nodeNameList = graph.getNodes();
        for (String node : nodeNameList)
        {
            if (node.equals(startNode))
            {
                visitedNodeList.add(new VisitedNode(node, 0, null));
                pQueue.add(node, 0);
            }
            else
            {
                visitedNodeList.add(new VisitedNode(node, Integer.MAX_VALUE, null));
                pQueue.add(node, Integer.MAX_VALUE);
            }
        }

        //process all nodes in the queue until empty
        while (!pQueue.isEmpty())
        {
            //pop the vertex with the smallest distance from the priority queue,
            //retrieve its corresponding VisitedNode (which holds the current best-known distance and previous node),
            //and store its current distance
            String currentVertex = pQueue.pop();
            VisitedNode currentVN = visitedNodeList.get(getIndex(currentVertex));
            int currentDistance = currentVN.getNodeDistance();

            //for each neighbor of the current vertex, calculate a new distance
            String[] allNeighbours = graph.getNeighbours(currentVertex);
            for(String neighbour : allNeighbours)
            {
                //calculate new distance via currentVertex
                int newDistance = currentDistance + graph.getEdgeDistance(currentVertex, neighbour);
                VisitedNode neighbourVN = visitedNodeList.get(getIndex(neighbour));

                //if the new distance is better than the best known distance, update the record
                if(newDistance < neighbourVN.getNodeDistance())
                {
                    //update the neighbor's distance and record currentVN as its previous node
                    neighbourVN.setNodeDistance(newDistance);
                    neighbourVN.setPrevious(currentVN);

                    //instead of removing an existing entry, add a new one with the updated distance
                    //this ensures that if a better path is found later, it will be processed
                    pQueue.add(neighbour, newDistance);
                }
            }
        }
    }

    //returns shortest path from startNode to endNode in a string array, [x] element being the name of the node for the [x]th step in the path
    //does this by traversing the tree from leaf to root
    @Override
    public String[] getPath(String startNode, String endNode)
    {
        //if graph has not already been traversed from startNode, traverse it (stops graph being traversed from the same startNode repeatedly)
        if(!startNode.equals(this.startNode))
        {
            traverseDijkstra(startNode);
        }

        //traverse tree from leaf to root using current as a cursor, adding each node it visits to path arrayList
        ArrayList<String> path = new ArrayList<>();
        VisitedNode current = visitedNodeList.get(getIndex(endNode));
        while (current != null)
        {
            path.add(current.getName());
            current = current.getPrevious();
        }

        //reverse path (change from leaf to root, to root(startNode) to leaf(endNode))
        String[] pathArray = path.toArray(new String[0]);
        String[] reversedPathArray = new String[pathArray.length];
        for(int i = 0; i < pathArray.length; i++)
        {
            reversedPathArray[i] = pathArray[pathArray.length - i - 1];
        }

        return reversedPathArray;
    }

    public void printPath(String startNode, String endNode)
    {
        String[] s = getPath(startNode, endNode);

        for(int i = 0; i < s.length; i++)
        {
            if(i<s.length - 1)
                System.out.print(s[i] + " -> ");
            else
                System.out.print(s[i]);

        }
    }

    //returns shortest distance from startNode to endNode in a string array
    @Override
    public int getDistance(String startNode, String endNode)
    {
        //if graph has not already been traversed from startNode, traverse it (stops graph being traversed from the same startNode repeatedly)
        if(!startNode.equals(this.startNode))
        {
            traverseDijkstra(startNode);
        }

        //returns distance of the visitedNode object in visitedNodeList
        return visitedNodeList.get(getIndex(endNode)).getNodeDistance();
    }

    //helper function to search for a node within visitedNodeList, returns the index thereof and throws an error if not present
    private int getIndex(String nodeName)
    {
        //iterates through list
        for(int i = 0; i < visitedNodeList.size(); i++)
        {
            //if current item is the item to be searched for, return the index thereof
            if(visitedNodeList.get(i).getName().equals(nodeName))
            {
                return i;
            }
        }

        throw new IllegalArgumentException("node not in list");
    }
}