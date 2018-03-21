package com.jpmorgan.onlinetest.others;

/*
Implement function countNumbers that accepts a sorted array of integers and counts the number of array elements that are less than the parameter lessThan.

For example, SortedSearch.countNumbers(new int[] { 1, 3, 5, 7 }, 4) should return 2 because there are two array elements less than 4.

known issue:

  Performance test when sortedArray contains lessThan: Time limit exceeded
  Performance test when sortedArray doesn't contain lessThan: Time limit exceeded

 */

public class SortedSearch {
    public static int countNumbers(int[] sortedArray, int lessThan) {
        //throw new UnsupportedOperationException("Waiting to be implemented.");


        int k=0;
        for (int i : sortedArray) {
            if(i<lessThan)k++;
            else break;
        }

        return k;
    }

    public static void main(String[] args) {
        System.out.println(SortedSearch.countNumbers(new int[] { 1, 3, 5, 7 }, 4));
    }
}
