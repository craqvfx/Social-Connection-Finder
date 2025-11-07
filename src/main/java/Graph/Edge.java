package Graph;

// Used for storage of an edges attributes (destination (direction) and weight)
public class Edge
{
    String destinationNode;
    int weight;

    public Edge(String destinationNode, int weight)
    {
        this.destinationNode = destinationNode;
        this.weight = weight;
    }

    public int getWeight()
    {
        return weight;
    }

    public String getDestination()
    {
        return destinationNode;
    }

    public String toString()
    {
        return "'" + destinationNode + "':" + String.valueOf(weight);
    }
}