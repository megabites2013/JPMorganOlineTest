package com.jpmorgan.onlinetest.others;

public class CarViecle {
}


class Vehicle {
    public void drive() {
        System.out.println("Vehicle: drive");
    }
}

class Car extends Vehicle {
    public void drive() {
        System.out.println("Car: drive");
    }
}

 class Test {
    public static void main (String args []) {
        Vehicle v;
        Car c;
        v = new Vehicle();
        c = new Car();
        v.drive();
        c.drive();
        v = c;
        v.drive();
    }
}

   // What will be the effect of compiling and running this class Test?
