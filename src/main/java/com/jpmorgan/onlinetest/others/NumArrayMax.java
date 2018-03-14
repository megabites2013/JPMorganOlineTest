package com.jpmorgan.onlinetest.others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//try finding max/min number, and quicksort in a List
public class NumArrayMax {

    //randomly
    private static List<Integer> numlist2  = Arrays.asList(1,323,112445,64,6,4,577,6566,773,28,27,23,21323,123,9019,-2323,-134,-1);


    public static void main(String args[]) {

        System.out.println("max  = " + getMax(numlist2));
        System.out.println("min  = " + getMin(numlist2));
        System.out.println(quicksort(numlist2));

    }

    private static int getMax(List<Integer> numlist) {
        int tmp=Integer.MIN_VALUE;
        for (int num : numlist) {
            if (num>tmp) tmp=num;
        }
        return tmp;
    }

    private static int getMin(List<Integer> numlist) {
        int tmp=Integer.MAX_VALUE;
        for (int num : numlist) {
            if (num<tmp) tmp=num;
        }
        return tmp;
    }


    private static List<Integer> quicksort(List<Integer> nums) {

        if(nums.size()==0) return nums;

        int keyidx=Math.floorDiv(nums.size(),2);
        Integer keyval = nums.get(keyidx);

        List<Integer> minlst= new ArrayList<>();
        List<Integer> keylst= new ArrayList<>(){{add(keyval);}};
        List<Integer> maxlst= new ArrayList<>();

        for (int num : nums) {
            if(num< keyval)
                minlst.add(num);
            else if(num> keyval)
                maxlst.add(num);
        }

        return Stream.concat(
                Stream.concat(quicksort(minlst).stream(), keylst.stream())
                        .collect(Collectors.toList()).stream(),
                quicksort(maxlst).stream()
        )
                .collect(Collectors.toList());

    }

}
