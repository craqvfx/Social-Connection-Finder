package PriorityQueue;

// An individual element of the priortiyQueue, stores value (name), priority, and previous and next pointers (for linked list)
public class Element
{
    private String value;
    private int priority;
    private Element previous;
    private Element next;

    public Element(String value, Element previous, Element next, int priority)
    {
        this.value = value;
        this.previous = previous;
        this.next = next;
        this.priority = priority;
    }

    @Override
    public String toString()
    {
        return value;
    }

    public String Value()
    {
        return value;
    }

    public int getPriority()
    {
        return priority;
    }

    public Element getPrevious()
    {
        return previous;
    }

    public void setPrevious(Element value)
    {
        previous = value;
    }

    public Element getNext()
    {
        return next;
    }

    public void setNext(Element value)
    {
        next = value;
    }
}