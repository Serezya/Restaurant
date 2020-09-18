package org.example;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private final int COOK_TIME = 5000;
    private final int TIME_OUT = 4000;

    // Создаем официантов и говорим, что они готовы к работе
    final List<Steward> stewards = new ArrayList<>() {{
        add(new Steward("Официант 1"));
        add(new Steward("Официант 2"));
        add(new Steward("Официант 3"));
    }};


    public void newOrder() {
        int i = 0;
        while (i < 5) {
            for (Steward steward : stewards) {
                if (steward.lock.tryLock()) {
                    try {
                        callSteward(steward);
                    } finally {
                        steward.lock.unlock();
                        i++;
                        break;
                    }
                }
            }
        }
    }

    private void callSteward(Steward steward) {
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " пришел в ресторан");
            System.out.println(steward.getName() + " взял заказ");
            try {
                System.out.println("Повар готовит блюдо для: " + Thread.currentThread().getName());
                Thread.sleep(TIME_OUT);
                System.out.println("Повар закончил готовить блюдо для: " + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(steward.getName() + " несет заказ для: " + Thread.currentThread().getName());
            try {
                System.out.println(Thread.currentThread().getName() + " кушает");
                Thread.currentThread().sleep(COOK_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " уходит");
            Thread.currentThread().interrupt();
        }
    }
}

