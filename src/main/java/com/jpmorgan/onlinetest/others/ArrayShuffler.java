package com.jpmorgan.onlinetest.others;
/*
Why this question?
The first part is meant as a warm-up, asking for the standard Fisher-Yates shuffle algorithm.
If the candidate doesn’t know it, and is unable to come up with it on the spot,
they should be gently nudged towards it
until they write it down as it’s an important foundation for the second part of the question.

The second part is not much about algorithms at all, but rather about concurrency. As mentioned before,
concurrency is tricky to get right, and this is also one of the advanced java interview questions.
But selecting the right concurrency primitives from the Java standard library goes a long way towards a correct implementation.
Experienced candidates should be able to come up with a correct solution here. */

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrayShuffler<T> {



    private T[] arr;
    private AtomicInteger index;
    private Semaphore semaphore;

    public ArrayShuffler(T[] arr) {
        this.arr = arr;
        index = new AtomicInteger(0);
        semaphore = new Semaphore(0);
    }

    public void startShuffling() {
        Random random = new Random();
        for (int source = 0; source < arr.length; ++source) {
            int target = random.nextInt(arr.length - source) + source;
            T temp = arr[source];
            arr[source] = arr[target];
            arr[target] = temp;
            semaphore.release();
        }
    }

    public T getNext() throws InterruptedException  {
        semaphore.acquire();
        int ind = index.getAndIncrement();
        return arr[ind];
    }


}
