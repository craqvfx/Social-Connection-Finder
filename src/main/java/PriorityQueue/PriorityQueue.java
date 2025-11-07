package PriorityQueue;

import java.util.ArrayList;

public class PriorityQueue
{   
    private Element front; // The front of the priority queue, e.g the 1st element
    private int length = 0;

    // Converts the priority queue into a string array of the value properties of each element object
    public String[] asArray()
    {
        ArrayList<String> a = new ArrayList<String>();
        Element e = front;

        while (e != null)
        { // Iterate through elements adding to the array
            a.add(e.Value());
            e = e.getNext();
        }

        return a.toArray(new String[a.size()]);
    }

    // Converts the priority queue into a an Array List
    public void printArray()
    {
        Element e = front;

        while (e != null)
        {// Iterate through elements adding to the array
            System.out.print(e);
            e = e.getNext();
        }
        System.out.println();
    }

    // Return true if the queue is empty
    public boolean isEmpty()
    {
        return length == 0;
    }

    // Add item of specified priority
    public void add(String value, int priority)
    {
        Element right = front;
        Element left;

        if (front == null)
        { // Edge case for 0 items, sets front as new element
            front = new Element(value, null, null, priority);
            length++;
            return;
        }
        else if(front.getNext() == null)
        { // Edge case for 1 item, sets new element as front or back depending on priority
            if (priority < front.getPriority()) 
            {
                Element newNode = new Element(value, null, front, priority);
                front.setPrevious(newNode);
                front = newNode;
            }
            else 
            {
                Element newNode = new Element(value, front, null, priority);
                front.setNext(newNode);
            }
            
            length++;
            return;
        }

        while (right.getNext() != null && right.getPriority() <= priority)
        { // Find where to insert
            right = right.getNext();
        }

        // Create new element
        left = right.getPrevious();
        Element newNode = new Element(value, left, right, priority);

        // Update pointers
        left.setNext(newNode);
        if(right != null)
        {
            right.setPrevious(newNode);
        }

        length ++;
    }

    // Remove an element, if element not present throws error
    public String remove(String value)
    {
        // Remove the first element that has a matching value, and reorganise the queue
        // Throw an error if the value is not present
        Element current = front;
        Element left;
        Element right;

        // Iterate through elements in the LinkedList
        while (!current.Value().equals(value))
        {
            if(current.getNext() == null)
            {
                throw new IllegalArgumentException("Node not present");
            }
            else
            {
                current = current.getNext();
            }
        }

        // Prepare left and right pointers
        right = current.getNext();
        left = current.getPrevious();

        // If theres a left neighbor, update next. If not update the front
        if (left != null)
        {
            left.setNext(right);
        }
        else
        {
            front = right;
        }

        // If theres a right neighbor, update previous
        if (right != null)
        {
            right.setPrevious(left);
        }

        length--;
        return current.Value();
    }

    // Return and remove the 1st element from the queue and reorganise the queue, throws an error if the queue is empty
    public String pop()
    {
        // Throw an error if the queue is empty
        if(length == 0)
        {
            throw new UnsupportedOperationException();
        }

        // Store value to return later
        String value = front.Value();

        // Update front
        front = front.getNext();

        // Set previous pointer to null as this is the new front
        if(front != null) // Check for edge case of popping from 1 element queue
        {
            front.setPrevious(null);
        }

        length --;
        return value;
    }

    // Return the 1st element from the queue (without removing it)
    public String peek()
    {
        return front.Value();
    }

    // Returns the priority of a specified value (or the first instance if the value occurs multiple times), returns -1 if not present
    public int getPriority(String value)
    {
        // Search pq for value
        for(Element e = front; e != null; e = e.getNext())
        {
            // If value is present, return it
            if(e.Value().equals(value))
            {
                return e.getPriority();
            }
        }

        // If not present, return error
        return -1;
    }

    // Returns size of priority queue
    public int size()
    {
        int size = 0;

        if(!isEmpty())
        {
            Element current = front;

            // Loop through entire queue, iterating size for each visited element
            while(current.getNext()!= null)
            {
                current = current.getNext();
                size++;
            }
        }

        return size;
    }
}
