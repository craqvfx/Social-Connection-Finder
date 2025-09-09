package PriorityQueueTest;

import org.junit.Assert;
import org.junit.Test;
import PriorityQueue.PriorityQueue;

import static org.junit.Assert.*;

public class PriorityQueueTest
{
    @Test
    public void test1isEmpty()
    {
        System.out.println("Test 1: isEmpty(), {}");
        PriorityQueue pq = new PriorityQueue();
        assertTrue("isEmpty(): True, {}", pq.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test2pop()
    {
        System.out.println("Test 2: pop(), {}");
        PriorityQueue pq = new PriorityQueue();
        pq.pop();
    }

    @Test
    public void test3add1()
    {
        System.out.println("Test 3: add(1), {1}");
        PriorityQueue pq = new PriorityQueue();
        pq.add("1", 1);

        assertArrayEquals("Test 3: add(1), {1}", new String[]{"1"}, pq.asArray());
    }

    @Test
    public void test4add4()
    {
        System.out.println("Test 4: add(4): {1, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.add("1", 1);
        pq.add("4", 4);

        Assert.assertArrayEquals("Test 4: add(4): {1, 4}", new String[]{"1", "4"}, pq.asArray());
    }

    @Test
    public void test5add5()
    {
        System.out.println("Test 5: add(5), {1, 5, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        Assert.assertArrayEquals("Test 5: add(5), {1, 5, 4}", new String[]{"1", "5", "4"}, pq.asArray());
    }

    @Test
    public void test6add2()
    {
        System.out.println("Test 6: add(2): {1, 2, 5, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        pq.add("2", 2);
        Assert.assertArrayEquals("Test 6: add(2): {1, 2, 5, 4}", new String[]{"1", "2", "5", "4"}, pq.asArray());
    }

    @Test
    public void test7add3()
    {
        System.out.println("Test 7: add(3): {1, 2, 5, 3, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        pq.add("2", 2);
        pq.add("3", 3);
        Assert.assertArrayEquals("Test 7: add(3): {1, 2, 5, 3, 4}", new String[]{"1", "2", "5", "3", "4"}, pq.asArray());
    }

    @Test
    public void test8add6()
    {
        System.out.println("Test 8: add(6): {1, 6, 2, 5, 3, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        pq.add("2", 2);
        pq.add("3", 3);
        pq.add("6", 1);
        Assert.assertArrayEquals("Test 8: add(6): {1, 6, 2, 5, 3, 4}", new String[]{"1", "6", "2", "5", "3", "4"}, pq.asArray());
    }

    @Test
    public void test9pop()
    {
        System.out.println("Test 9: pop: {6, 2, 5, 3, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        pq.add("2", 2);
        pq.add("3", 3);
        pq.add("6", 1);
        pq.pop();
        assertArrayEquals("Test 9: pop: {6, 2, 5, 3, 4}", new String[]{"6", "2", "5", "3", "4"}, pq.asArray());
    }

    @Test
    public void test10add3()
    {
        System.out.println("Test 10: add (3, 3) : {6, 2, 5, 3, 3, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        pq.add("2", 2);
        pq.add("3", 3);
        pq.add("6", 1);
        pq.pop();
        pq.add("3", 3);

        assertArrayEquals("Test 10: add (3, 3) : {6, 2, 5, 3, 3, 4}", new String[]{"6", "2", "5", "3", "3", "4"}, pq.asArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test11remove1()
    {
        System.out.println("Test 11: remove(1) : {6, 2, 5, 3, 3, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        pq.add("2", 2);
        pq.add("3", 3);
        pq.add("6", 1);
        pq.pop();
        pq.add("3", 3);
        pq.remove("1");
    }

    @Test
    public void test12remove3()
    {
        System.out.println("Test 12: remove(3) : {6, 2, 5, 3, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        pq.add("2", 2);
        pq.add("3", 3);
        pq.add("6", 1);
        pq.pop();
        pq.add("3", 3);
        pq.remove("3");
        assertArrayEquals("Test 12: remove(3) : {6, 2, 5, 3, 4}", new String[]{"6","2","5","3","4"}, pq.asArray());
    }

    @Test
    public void test13isEmpty()
    {
        System.out.println("Test 13: isEmpty: {6, 2, 5, 3, 4}");
        PriorityQueue pq = new PriorityQueue();
        pq.isEmpty();
        pq.add("1", 1);
        pq.add("4", 4);
        pq.add("5", 3);
        pq.add("2", 2);
        pq.add("3", 3);
        pq.add("6", 1);
        pq.pop();
        pq.add("3", 3);
        pq.remove("3");
        assertFalse("Test 13: isEmpty: {6, 2, 5, 3, 4}", pq.isEmpty());
    }
}
