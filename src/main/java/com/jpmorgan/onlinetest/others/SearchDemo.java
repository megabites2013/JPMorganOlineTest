package com.jpmorgan.onlinetest.others;

//有一数组 a[1000]存放了1000 个数,这 1000个数取自1-999, 且只有两个相同的数,剩下的 998个数不同,
// 写一个搜索算法找出相同的那个数的值（请用 C# or JAVA编程实现，注意空间效率和时间效率尽可能优化）。



import java.util.Arrays;

public class SearchDemo {

    /** 被搜索数据的大小 */

    private static final int size = 1000;


    public static void main(String[] args) {

        int[] data = new int[size];


        // 添加测试数据

        for (int k = 0; k < data.length; k++) {

            data[k] = k + 1;

        }

        data[999] = 567;

        System.out.println(Arrays.toString(data));

        result2(data);

    }


    public static void result2(int data[]){

        Arrays.sort(data);
        System.out.println(Arrays.toString(data));


        for (int i = 0; i < data.length; i++) {



            if (data[i] == data[i+1]) {

                System.out.println("相同元素为："+data[i]);

                break;

            }

        }

    }

    /**

     * 调用2分搜索算法的方法实现查找相同元素

     * @param data

     */

    public static void result(int data[]){

        Arrays.sort(data);

        for (int i = 0; i < data.length; i++) {

            int target = data[i];

            data[i] = 0;

            int result = binaryFind(data, target);

            if (result != -1) {

                System.out.println("相同元素为："+data[result]);

                break;

            }

        }

    }

    /**

     * 二分搜索算法实现

     *

     * @param data

     * 数据集合

     * @param target

     * 搜索的数据

     * @return 返回找到的数据的位置，返回-1表示没有找到。

     */

    public static int binaryFind(int[] data, int target) {

        int start = 0;

        int end = data.length - 1;

        while (start <= end) {

            int middleIndex = (start + end) / 2;

            if (target == data[middleIndex]) {

                return middleIndex;

            }

            if (target >= data[middleIndex]) {

                start = middleIndex + 1;

            } else {

                end = middleIndex - 1;

            }

        }

        return -1;

    }

}
