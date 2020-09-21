package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private final int EAT_TIME = 4000;
    private final int WAIT_ORDER = 1000;
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    volatile boolean isCooked = true;

    // Создаем официантов и говорим, что они готовы к работе
    final List<Steward> stewards = new ArrayList<>() {{
        add(new Steward("Официант 1"));
        add(new Steward("Официант 2"));
        add(new Steward("Официант 3"));
    }};

    Cook cook = new Cook("Повар");

    public void newOrder() {
        boolean counter = true;
        while (counter) {
            for (Steward steward : stewards) {
                if (steward.lock.tryLock()) {
                    try {
                        callSteward(steward);
                        counter = false;
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void callSteward(Steward steward) throws InterruptedException {
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " пришел в ресторан");
            try {
                Thread.sleep(WAIT_ORDER);
                System.out.println(Thread.currentThread().getName() + " готов сделать заказ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(steward.getName() + " взял заказ от " + Thread.currentThread().getName());
            steward.lock.unlock();

            lock.lock();
            while (!isCooked) {
                condition.await();
            }

            try {
                isCooked = cook.makeDish(condition);
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName());
                e.printStackTrace();
            } finally {
                lock.unlock();
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
            isCooked = false;
            Thread.currentThread().interrupt();
        }
    }
}