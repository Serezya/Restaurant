package org.example;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private final int COOK_TIME = 5000;
    private final int EAT_TIME = 4000;
    private final int WAIT_ORDER = 1000;


    // Создаем официантов и говорим, что они готовы к работе
    final List<Steward> stewards = new ArrayList<>() {{
        add(new Steward("Официант 1"));
        add(new Steward("Официант 2"));
        add(new Steward("Официант 3"));
    }};

    Cook cook = new Cook("Повар");

    public void newOrder() {
        int i = 0;
        while (i < 5) {
            for (Steward steward : stewards) {
                if (steward.lock.tryLock()) {
                    try {
                        callSteward(steward);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        }
    }

    private void callSteward(Steward steward) {
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " пришел в ресторан");
            try {
                Thread.sleep(WAIT_ORDER);
                System.out.println(Thread.currentThread().getName() + " готов сделать заказ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(steward.getName() + " взял заказ");
            steward.lock.unlock();

            while (cook.cookLock.tryLock()) {
                try{
                    cook.makeDish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cook.cookLock.unlock();
                    break;
                }
            }
            steward.lock.lock();
            System.out.println(steward.getName() + " несет заказ для: " + Thread.currentThread().getName());
            steward.lock.unlock();
            try {
                System.out.println(Thread.currentThread().getName() + " кушает");
                Thread.sleep(EAT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " уходит");
            Thread.currentThread().interrupt();
        }
    }
}