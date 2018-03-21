package com.jpmorgan.onlinetest.others;

/*

User interface contains two types of user input controls: TextInput, which accepts all characters and NumericInput, which accepts only digits.

Implement the class TextInput that contains:

Public method void add(char c) - adds the given character to the current value
Public method String getValue() - returns the current value
Implement the class NumericInput that:

Inherits from TextInput
Overrides the add method so that each non-numeric character is ignored
For example, the following code should output "10":

TextInput input = new NumericInput();
input.add('1');
input.add('a');
input.add('0');
System.out.println(input.getValue());


 */
public class UserInput {

    public static class TextInput {

        private StringBuffer Value= new StringBuffer("");

        public void add(char a)
        {

            Value.append(a);


        }

        public String getValue(){
            return Value.toString();
        }
    }

    public static class NumericInput extends TextInput{


        @Override
        public void add(char a) {
            if(Character.isDigit(a))
                super.add(a);
        }
    }

    public static void main(String[] args) {
        //TextInput input = new NumericInput();
        //input.add('1');
        //input.add('a');
        //input.add('0');
        //System.out.println(input.getValue());
    }
}


