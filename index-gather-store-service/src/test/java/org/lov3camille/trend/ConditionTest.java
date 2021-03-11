package org.lov3camille.trend;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    public static void main(String[] args) {
        Data2 data2 = new Data2();

        new Thread(()->{
            for (int i = 0; i < 10; i ++) {
                data2.printA();
            }
        }, "A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i ++) {
                data2.printB();
            }
        }, "B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i ++) {
                data2.printC();
            }
        }, "C").start();

    }

}

class Data2 {

    private final Lock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private final Condition condition3 = lock.newCondition();
    private int num = 1;

    public void printA() {
        lock.lock();
        try {
            while (num != 1) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "->A");
            num = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (num != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "->B");
            num = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (num != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "->C");
            num = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
