package com.jpmorgan.onlinetest.others;
/*
A TrainComposition is built by attaching and detaching wagons from the left and the right sides.

For example, if we start by attaching wagon 7 from the left followed by attaching wagon 13, again from the left, we get a composition of two wagons (13 and 7 from left to right). Now the first wagon that can be detached from the right is 7 and the first that can be detached from the left is 13.

Implement a TrainComposition that models this problem.


 */
import java.util.LinkedList;

public class TrainComposition {
    private LinkedList lst = new LinkedList();

    public void attachWagonFromLeft(int wagonId) {
        //throw new UnsupportedOperationException("Waiting to be implemented.");
        lst.addFirst(wagonId);
    }

    public void attachWagonFromRight(int wagonId) {
        //throw new UnsupportedOperationException("Waiting to be implemented.");
        lst.addLast(wagonId);
    }

    public int detachWagonFromLeft() {
        //throw new UnsupportedOperationException("Waiting to be implemented.");
        return (int)lst.removeFirst();
    }

    public int detachWagonFromRight() {
        //throw new UnsupportedOperationException("Waiting to be implemented.");
        return (int)lst.removeLast();

    }

    public static void main(String[] args) {
        TrainComposition tree = new TrainComposition();
        tree.attachWagonFromLeft(7);
        tree.attachWagonFromLeft(13);
        System.out.println(tree.detachWagonFromRight()); // 7
        System.out.println(tree.detachWagonFromLeft()); // 13
    }
}
