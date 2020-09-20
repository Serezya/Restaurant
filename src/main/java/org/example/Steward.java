package org.example;

import java.util.concurrent.locks.*;

public class Steward {
    String name;
    Lock lock = new ReentrantLock();
    Condition conditionLock = lock.newCondition();

    public String getName() {
        return name;
    }

    public Steward(String name) {
        this.name = name;
        System.out.println(this.getName() + " на работе");
    }
}
