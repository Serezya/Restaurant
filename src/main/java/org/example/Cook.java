package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class Cook {
    String name;
    Lock cookLock = new ReentrantLock();
    Condition conditionCookLock = cookLock.newCondition();


    public String getName() {
        return name;
    }

    public Cook(String name) {
        this.name = name;
        System.out.println(this.getName() + " на работе");
    }

    public boolean makeDish(Condition condition) throws InterruptedException {
//        if (cookLock.tryLock()) {
            try {
                System.out.println("Повар готовит блюдо для: " + Thread.currentThread().getName());
                int COOK_TIME = 5000;
                TimeUnit.SECONDS.sleep(5);
//                Thread.sleep(COOK_TIME);
            } finally {
                System.out.println("Повар закончил готовить блюдо для: " + Thread.currentThread().getName());
//                conditionCookLock.signalAll();
//                cookLock.unlock();
                condition.signalAll();
                return true;
            }
//        }
    }
}
