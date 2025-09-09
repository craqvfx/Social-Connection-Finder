package PriorityQueue;

import java.util.ArrayList;

public class PriorityQueue
{   //The front of the priority queue, e.g the 1st element
    private Element front;
    private int length = 0;

    public static void main(String[] args)
    {
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        //pq.pop();
        pq.add("1", 1);pq.printArray();
        pq.add("4", 4);pq.printArray();
        pq.add("5", 3);pq.printArray();
        pq.add("2", 2);pq.printArray();
        pq.add("3", 3);pq.printArray();
        pq.add("6", 1);pq.printArray();
        pq.pop();pq.printArray();
        pq.add("3", 3);pq.printArray();
        //pq.remove("1");
        pq.remove("3");pq.printArray();
        pq.isEmpty();
    }

    //converts the priority queue into a string array of the value parameter
    public String[] asArray()
    {
        ArrayList<String> a = new ArrayList<String>();
        Element e = front;

        while (e != null)
        {//Iterate through elements adding to the array
            a.add(e.Value());
            e = e.getNext();
        }

        return a.toArray(new String[a.size()]);
    }

    public void printArray()
    {//Converts the priority queue into a an Array List
        Element e = front;

        while (e != null)
        {//Iterate through elements adding to the array
            System.out.print(e);
            e = e.getNext();
        }
        System.out.println();
    }

    //return if the queue is empty
    public boolean isEmpty()
    {
        return length == 0;
    }

    //add item of specified priority
    public void add(String value, int priority)
    {
        Element right = front;
        Element left;

        if (front == null)
        {//edge case for 0 items
            front = new Element(value, null, null, priority);
            length++;
            return;
        }
        else if(front.getNext() == null)
        {//edge case for 1 item
            Element newNode = new Element(value, front, null, priority);
            front.setNext(newNode);
            length++;
            return;
        }

        while (right.getNext() != null && right.getPriority() <= priority)
        {//Find where to insert
            right = right.getNext();
        }

        left = right.getPrevious();

        Element newNode = new Element(value, left, right, priority);

        if(left == null)
        {
            front = newNode;
        }
        else
        {
            left.setNext(newNode);
        }

        if(right != null)
        {
            right.setPrevious(newNode);
        }

        length ++;
    }

    //remove an element, if element not present throws error
    public String remove(String value)
    {
        //remove the first element that has a matching value, and reorganise the queue
        //throw an error if the value is not present
        Element current = front;
        Element left;
        Element right;

        //iterate through elements in the LinkedList
        while (! current.Value().equals(value))
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

        right = current.getNext();
        left = current.getPrevious();

        //if theres a left neighbor, update next. if not update the front
        if (left != null)
        {
            left.setNext(right);
        }
        else
        {
            front = right;
        }


        //if theres a right neighbor, update previous
        if (right != null)
        {
            right.setPrevious(left);
        }

        length--;
        return current.Value();
    }

    //return and remove the 1st element from the queue and reorganise the queue, throws an error if the queue is empty
    public String pop()
    {
        //throw an error if the queue is empty
        if(length == 0)
        {
            throw new UnsupportedOperationException();
        }

        //store value to return later
        String value = front.Value();

        //update front
        front = front.getNext();

        //set previous pointer to null as this is the new front
        if(front != null)
        {
            front.setPrevious(null);
        }

        length --;
        return value;
    }

    //return the 1st element from the queue (without removing it)
    public String peek()
    {
        return front.Value();
    }

    public int getPriority(String value)
    {
        //search pq for value
        for(Element e = front; e != null; e = e.getNext())
        {
            //if value is present, return it
            if(e.Value().equals(value))
            {
                return e.getPriority();
            }
        }

        //if not present, return error
        return -1;
    }

    //returns size of priority queue
    public int size()
    {
        int size = 0;

        if(!isEmpty())
        {
            Element current = front;

            //loop through entire queue, iterating size for each visited element
            while(current.getNext()!= null)
            {
                current = current.getNext();
                size++;
            }
        }

        return size;
    }
}
