package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cook {
    String name;
    Lock cookLock = new ReentrantLock();

    public String getName() {
        return name;
    }
    public Cook(String name) {
        this.name = name;
        System.out.println(this.getName() + " на работе");
    }
}
