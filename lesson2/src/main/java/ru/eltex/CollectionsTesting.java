package ru.eltex;

import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

public class CollectionsTesting {
    public static void main(String[] args) {
        test(new ArrayList<Integer>());
        test(new LinkedList<Integer>());
        test(new TreeSet<Integer>());
    }
    
    static void test(Collection<Integer> collection) {
        for (int i = 0; i < 1000000; i++) {
            collection.add(i);
        }
        
        Integer value = Integer.MAX_VALUE;
        long addingTime = measureAddingTime(collection, value);
        long removingTime = measureRemovingTime(collection, value);
        
        String name = collection.getClass().getSimpleName();
        System.out.printf("%-15s Adding: %7d ns\t Removing: %10d ns\n", name, addingTime, removingTime);
    }
    
    static long measureAddingTime(Collection<Integer> collection, Integer value) {
        long timeBefore = System.nanoTime();
        collection.add(value);
        long timeAfter = System.nanoTime();
        return timeAfter - timeBefore;
    }
    
    static long measureRemovingTime(Collection<Integer> collection, Integer value) {
        long timeBefore = System.nanoTime();
        collection.remove(value);
        long timeAfter = System.nanoTime();
        return timeAfter - timeBefore;
    }
}
