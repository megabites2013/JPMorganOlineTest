package com.jpmorgan.onlinetest.others;

/*
A palindrome is a word that reads the same backward or forward.

Write a function that checks if a given word is a palindrome. Character case should be ignored.

For example, isPalindrome("Deleveled") should return true as character case should be ignored, resulting in "deleveled", which is a palindrome since it reads the same backward and forward.
 */

public class Palindrome {
    public static boolean isPalindrome(String word) {
        //if(word.length()==0) return true;

        //throw new UnsupportedOperationException("Waiting to be implemented.");
        return word.equalsIgnoreCase(new StringBuilder(word).reverse().toString());
        //or for 1.5
        //return word.equalsIgnoreCase(new StringBuffer().append(word).reverse().toString());


    }

    public static void main(String[] args) {
        System.out.println(Palindrome.isPalindrome("Deleveled"));
    }
}
