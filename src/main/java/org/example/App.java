package org.example;

public class App {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        // Идем кушать в ресторан
        new Thread(null, restaurant::newOrder, "Поcетитель 1").start();
        new Thread(null, restaurant::newOrder, "Поcетитель 2").start();
        new Thread(null, restaurant::newOrder, "Поcетитель 3").start();
        new Thread(null, restaurant::newOrder, "Поcетитель 4").start();
        new Thread(null, restaurant::newOrder, "Поcетитель 5").start();
    }
}
