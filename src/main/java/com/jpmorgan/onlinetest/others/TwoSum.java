package com.jpmorgan.onlinetest.others;


/*
Write a function that, given a list and a target sum, returns zero-based indices of any two distinct elements whose sum is equal to the target sum. If there are no such elements, the function should return null.

For example, findTwoSum(new int[] { 1, 3, 5, 7, 9 }, 12) should return a single dimensional array with two elements and contain any of the following pairs of indices:

1 and 4 (3 + 9 = 12)
2 and 3 (5 + 7 = 12)
3 and 2 (7 + 5 = 12)
4 and 1 (9 + 3 = 12)

 */

import java.util.ArrayList;
import java.util.List;

public class TwoSum {
    public static int[] findTwoSum(int[] list, int sum) {
        //throw new UnsupportedOperationException("Waiting to be implemented.");

        List<Integer> list1 = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            int i1 = list[i];

            for (int j = 0; j < list.length; j++) {
                int i2 = list[j];

                if(i1+i2==sum)
                {
                    list1.add(i);
                    list1.add(j);
                    System.out.println(""+(i)+"+"+(j)+"("+i1+"+"+i2+"="+sum+")");

                }

            }

        }


        if(list1.size()==0)return null;

        int [] reta= new int [list1.size()];
        for (int i = 0; i < reta.length; i++) {
             reta[i]=list1.get(i);

        }

        return reta;

    }

    public static void main(String[] args) {
        int[] indices = findTwoSum(new int[] { 1, 3, 5, 7, 9 }, 12);
        //System.out.print(indices);
        if(indices != null) {
            for (int i = 0; i < indices.length; i+=2) {
                System.out.println(indices[i] + " " + indices[i+1]);

            }
        }
    }
}
