package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Steward {
    String name;
    Lock lock = new ReentrantLock();

    public String getName() {
        return name;
    }

    public Steward(String name) {
        this.name = name;
        System.out.println(this.getName() + " на работе");
    }
}
